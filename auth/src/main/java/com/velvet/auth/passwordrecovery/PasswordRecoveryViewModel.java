package com.velvet.auth.passwordrecovery;

import static com.velvet.auth.utils.Validator.validateEmail;
import static com.velvet.auth.utils.Validator.validatePasswordLength;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.auth.R;
import com.velvet.auth.passwordrecovery.state.PasswordRecoveryViewEffect;
import com.velvet.auth.passwordrecovery.state.PasswordRecoveryViewState;
import com.velvet.core.Values;
import com.velvet.core.models.auth.AuthMessage;
import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.libs.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PasswordRecoveryViewModel extends MviViewModel<PasswordRecoveryContract.View, PasswordRecoveryViewState, PasswordRecoveryViewEffect> implements PasswordRecoveryContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<AuthMessage> requestSubject = PublishSubject.create();
    private final BehaviorSubject<AuthMessage> recoverySubject = BehaviorSubject.create();

    public PasswordRecoveryViewModel(AuthNetwork authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner owner, @NonNull Lifecycle.Event event) {
        super.onStateChanged(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    recoverySubject
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .flatMap(p -> authRepository.authRequest(p).toObservable())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (Values.REQUEST.equals(result.data)) {
                                    if (result.isError()) {
                                        setInfoText(R.string.code_successfully_sent);
                                    } else {
                                        setInfoText(R.string.invalid_recovery_email);
                                    }
                                } else if (Values.CHECK.equals(result.data)) {
                                    if (result.isError()) {
                                        setInfoText(R.string.invalid_recovery_code);
                                    } else {
                                        setInfoText(R.string.password_successfully_changed);
                                    }
                                } else {
                                    setInfoText(R.string.something_went_wrong);
                                }
                            }, e -> {
                                setInfoText(R.string.something_went_wrong);
                                e.printStackTrace();
                            })
            );
        }
    }

    @Override
    protected PasswordRecoveryViewState getDefaultState() {
        return PasswordRecoveryViewState.createSetTextState(R.string.initial);
    }

    @Override
    public void requestCode(String email) {
        if (validateEmail(email)) {
            requestSubject.onNext(AuthMessage.createRequestCodeMessage(email));
        } else {
            setEmailError();
        }
    }

    @Override
    public void checkCode(String code, String newPassword) {
        if (validatePasswordLength(newPassword)) {
            recoverySubject.onNext(AuthMessage.createCheckCodeAndSetNewPasswordMessage(code, newPassword));
        } else {
            setPasswordError();
        }
    }

    private void setInfoText(int infoText) {
        setState(PasswordRecoveryViewState.createSetTextState(infoText));
    }

    private void setEmailError() {
        setState(PasswordRecoveryViewState.createSetEmailErrorState(R.string.email_error));
    }

    private void setPasswordError() {
        setState(PasswordRecoveryViewState.createSetPasswordErrorState(R.string.password_error));
    }
}
