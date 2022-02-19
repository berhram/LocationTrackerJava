package com.velvet.auth.login;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.auth.R;
import com.velvet.auth.login.state.LoginViewEffect;
import com.velvet.auth.login.state.LoginViewState;
import com.velvet.core.models.auth.AuthMessage;
import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.libs.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends MviViewModel<LoginContract.View, LoginViewState, LoginViewEffect> implements LoginContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<Long> checkSubject = PublishSubject.create();
    private final BehaviorSubject<AuthMessage> authSubject = BehaviorSubject.create();

    public LoginViewModel(AuthNetwork authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    checkSubject
                            .flatMap(t -> authRepository.checkIfUserLoggedIn().toObservable())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.data) {
                                    success();
                                }
                            }, e -> {
                                setInfoText(R.string.something_went_wrong);
                                e.printStackTrace();
                            }),
                    authSubject
                            .flatMap(p -> authRepository.sendMessage(p).toObservable())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    result.error.printStackTrace();
                                    setInfoText(R.string.invalid_email_or_password);
                                } else {
                                    setInfoText(R.string.success_login);
                                    checkSubject.onNext(System.currentTimeMillis());
                                }
                            }, e -> {
                                e.printStackTrace();
                                setInfoText(R.string.invalid_email_or_password);
                            })
            );
            checkSubject.onNext(System.currentTimeMillis());
        }
        if (event == Lifecycle.Event.ON_RESUME) {
            checkSubject.onNext(System.currentTimeMillis());
        }
    }

    @Override
    protected LoginViewState getDefaultState() {
        return LoginViewState.createSetTextState(R.string.initial);
    }

    @Override
    public void signIn(String email, String password) {
        authSubject.onNext(AuthMessage.createLoginMessage(email, password));
    }

    @Override
    public void signUp(String email, String password) {
        authSubject.onNext(AuthMessage.createRegisterMessage(email, password));
    }

    @Override
    public void success() {
        setAction(LoginViewEffect.proceedToNextScreen());
    }

    @Override
    public void setInfoText(int infoText) {
        setState(LoginViewState.createSetTextState(infoText));
    }
}
