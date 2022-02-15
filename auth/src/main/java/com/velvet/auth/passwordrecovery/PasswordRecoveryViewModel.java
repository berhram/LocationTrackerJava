package com.velvet.auth.passwordrecovery;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.auth.R;
import com.velvet.auth.passwordrecovery.state.PasswordRecoveryViewEffect;
import com.velvet.auth.passwordrecovery.state.PasswordRecoveryViewState;
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
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    recoverySubject
                            .flatMap(p -> authRepository.sendMessage(p).toObservable())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    result.error.printStackTrace();
                                    setInfoText(R.string.invalid_recovery_code);
                                } else {
                                    setInfoText(R.string.password_successfully_changed);
                                }
                            }, Throwable::printStackTrace),
                    requestSubject
                            .flatMap(p -> authRepository.sendMessage(p).toObservable())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    result.error.printStackTrace();
                                        setInfoText(R.string.invalid_recovery_email);
                                } else {
                                    setInfoText(R.string.code_successfully_sent);
                                }
                            }, Throwable::printStackTrace)
            );
        }
    }

    @Override
    protected PasswordRecoveryViewState getDefaultState() {
        return PasswordRecoveryViewState.createSetTextState(R.string.initial);
    }

    @Override
    public void success() {
        setAction(PasswordRecoveryViewEffect.proceedToPreviousScreen());
    }

    @Override
    public void setInfoText(int infoText) {
        setState(PasswordRecoveryViewState.createSetTextState(infoText));
    }

    @Override
    public void requestCode(String email) {
        requestSubject.onNext(AuthMessage.createRequestCodeMessage(email));
    }

    @Override
    public void checkCode(String code, String newPassword) {
        recoverySubject.onNext(AuthMessage.createCheckCodeAndSetNewPassowordMessage(code, newPassword));
    }
}
