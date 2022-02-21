package com.velvet.auth.login;

import static com.velvet.auth.utils.Validator.validateEmail;
import static com.velvet.auth.utils.Validator.validatePasswordLength;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.auth.R;
import com.velvet.auth.login.state.LoginViewEffect;
import com.velvet.auth.login.state.LoginViewState;
import com.velvet.core.Values;
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
                            .flatMap(p -> authRepository.authRequest(p).toObservable())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    result.error.printStackTrace();
                                    setInfoText(R.string.invalid_email_or_password);
                                } else {
                                    if (Values.LOGIN.equals(result.data)) {
                                        setInfoText(R.string.success_login);
                                        checkSubject.onNext(System.currentTimeMillis());
                                    } else if (Values.REGISTER.equals(result.data)) {
                                        setInfoText(R.string.success_registration);
                                    } else {
                                        setInfoText(R.string.something_went_wrong);
                                    }
                                }
                            }, e -> {
                                e.printStackTrace();
                                setInfoText(R.string.something_went_wrong);
                            })
            );
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
        if (validateEmail(email)) {
            if (validatePasswordLength(password)) {
                authSubject.onNext(AuthMessage.createRegisterMessage(email, password));
            } else {
                setPasswordError();
            }
        } else {
            setEmailError();
        }
    }

    @Override
    public void success() {
        setAction(LoginViewEffect.proceedToNextScreen());
    }

    @Override
    public void setInfoText(int infoText) {
        setState(LoginViewState.createSetTextState(infoText));
    }

    @Override
    public void setEmailError() {
        setState(LoginViewState.createSetEmailErrorState(R.string.email_error));
    }

    @Override
    public void setPasswordError() {
        setState(LoginViewState.createSetPasswordErrorState(R.string.password_error));
    }
}
