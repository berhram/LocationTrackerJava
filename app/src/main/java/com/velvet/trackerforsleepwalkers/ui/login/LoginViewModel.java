package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.models.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends MviViewModel<LoginViewState, LoginViewEffect> implements LoginContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<Boolean> loginSubject = PublishSubject.create();
    private final BehaviorSubject<AuthParams> infoTextSubject = BehaviorSubject.create();

    public LoginViewModel(AuthNetwork authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    loginSubject
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
                                if ("register".equals(p.type)) {
                                    return authRepository.register(p.email, p.password).toObservable();
                                } else {
                                    return authRepository.login(p.email, p.password).toObservable();
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (result.isError()) {
                                    //TODO add variety in messages like in recovery screen
                                    result.error.printStackTrace();
                                    setInfoText(R.string.invalid_email_or_password);
                                } else {
                                    setInfoText(R.string.success_login);
                                    //login after successful sign in
                                    loginSubject.onNext(authRepository.checkIfUserLoggedIn());
                                }
                            }, e -> {
                                e.printStackTrace();
                                setInfoText(R.string.invalid_email_or_password);
                            })
            );
            loginSubject.onNext(authRepository.checkIfUserLoggedIn());
        }
    }

    @Override
    protected LoginViewState getDefaultState() {
        return LoginViewState.createSetTextState(R.string.initial);
    }

    @Override
    public void signIn(String email, String password) {
        infoTextSubject.onNext(new AuthParams(email, password, "login"));
    }

    @Override
    public void signUp(String email, String password) {
        infoTextSubject.onNext(new AuthParams(email, password, "register"));
    }

    @Override
    public void success() {
        setAction(new LoginViewEffect.ProceedToNextScreen());
    }

    @Override
    public void setInfoText(int infoText) {
        setState(LoginViewState.createSetTextState(infoText));
    }

    //TODO remove from there
    private static class AuthParams {
        private final String email;
        private final String password;
        private final String type;

        AuthParams(String email, String password, String type) {
            this.email = email;
            this.password = password;
            this.type = type;
        }
    }
}
