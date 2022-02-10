package com.velvet.auth.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.auth.R;
import com.velvet.auth.login.state.LoginViewEffect;
import com.velvet.auth.login.state.LoginViewState;
import com.velvet.models.Values;
import com.velvet.models.auth.AuthNetwork;
import com.velvet.models.auth.FirebaseAuthMessages;
import com.velvet.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends MviViewModel<LoginContract.View, LoginViewState, LoginViewEffect> implements LoginContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<Long> loginSubject = PublishSubject.create();
    private final BehaviorSubject<FirebaseAuthMessages.AuthParams> infoTextSubject = BehaviorSubject.create();

    public LoginViewModel(AuthNetwork authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    loginSubject
                            .map(t -> authRepository.checkIfUserLoggedIn())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    success();
                                }
                            }, e -> {
                                setInfoText(R.string.something_went_wrong);
                                e.printStackTrace();
                            }),
                    infoTextSubject
                            .switchMap(p -> {
                                if (p.isLogin()) {
                                    return authRepository.login(p.getEmail(), p.getPassword()).toObservable();
                                } else {
                                    return authRepository.register(p.getEmail(), p.getPassword()).toObservable();
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    result.error.printStackTrace();
                                    setInfoText(R.string.invalid_email_or_password);
                                } else {
                                    setInfoText(R.string.success_login);
                                    loginSubject.onNext(System.currentTimeMillis());
                                }
                            }, e -> {
                                e.printStackTrace();
                                setInfoText(R.string.invalid_email_or_password);
                            })
            );
            loginSubject.onNext(System.currentTimeMillis());
        }
    }

    @Override
    protected LoginViewState getDefaultState() {
        return LoginViewState.createSetTextState(R.string.initial);
    }

    @Override
    public void signIn(String email, String password) {
        infoTextSubject.onNext(new FirebaseAuthMessages.AuthParams(email, password, Values.LOGIN));
    }

    @Override
    public void signUp(String email, String password) {
        infoTextSubject.onNext(new FirebaseAuthMessages.AuthParams(email, password, Values.REGISTER));
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
