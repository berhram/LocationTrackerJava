package com.velvet.trackerforsleepwalkers.ui.passwordrecovery;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.models.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PasswordRecoveryViewModel extends MviViewModel<PasswordRecoveryViewState, PasswordRecoveryViewEffect> implements PasswordRecoveryContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<Boolean> passwordRecoverySubject = PublishSubject.create();
    private final BehaviorSubject<RecoveryParams> infoTextSubject = BehaviorSubject.create();

    public PasswordRecoveryViewModel(AuthNetwork authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    passwordRecoverySubject
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    success();
                                }
                            }, e -> {
                                e.printStackTrace();
                            }),
                    infoTextSubject
                            .switchMap(p -> {
                                if ("request".equals(p.type)) {
                                    return authRepository.requestCode(p.email).toObservable();
                                } else {
                                    return authRepository.checkCode(p.code, p.newPassword).toObservable();
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    result.error.printStackTrace();
                                    setInfoText(R.string.invalid_email_or_password);
                                } else {
                                    setInfoText(R.string.password_successfully_changed);
                                }
                            }, e -> {
                                e.printStackTrace();
                                setInfoText(R.string.invalid_recovery_email);
                            })
            );
        }
    }

    @Override
    protected PasswordRecoveryViewState getDefaultState() {
        return PasswordRecoveryViewState.createSetTextState(R.string.initial);
    }

    @Override
    public void success() {
        setAction(new PasswordRecoveryViewEffect.ProceedToPreviousScreen());
    }

    @Override
    public void setInfoText(int infoText) {
        setState(PasswordRecoveryViewState.createSetTextState(infoText));
    }

    @Override
    public void requestCode(String email) {
        infoTextSubject.onNext(new RecoveryParams(email, "request"));
    }

    @Override
    public void checkCode(String code, String newPassword) {
        infoTextSubject.onNext(new RecoveryParams(code, newPassword, "check"));
    }

    private static class RecoveryParams {
        private final String type;
        private String email;
        private String newPassword;
        private String code;

        RecoveryParams(String code, String newPassword, String type) {
            this.newPassword = newPassword;
            this.code = code;
            this.type = type;
        }

        RecoveryParams(String email, String type) {
            this.email = email;
            this.type = type;
        }
    }
}
