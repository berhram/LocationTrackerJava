package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends MviViewModel<LoginViewState> implements LoginContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<Boolean> loginSubject = PublishSubject.create();

    private final BehaviorSubject<Integer> infoTextSubject = BehaviorSubject.create();

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
                infoTextSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(infoText -> {
                    setInfoText(infoText);
                })
            );
            loginSubject.onNext(authRepository.checkIfUserLoggedIn());
        }
    }

    @Override
    protected LoginViewState getDefaultState() {
        return LoginViewState.createInitialState();
    }

    @Override
    public void signIn(String email, String password) {
        infoTextSubject.onNext(authRepository.login(email, password));
        checkIfUserSignIn();
    }

    @Override
    public void signUp(String email, String password) {
        infoTextSubject.onNext(authRepository.register(email, password));
    }

    @Override
    public void success() {
        setState(LoginViewState.createSuccess());
    }

    @Override
    public void setInfoText(int infoText) {
        setState(LoginViewState.createSetTextState(infoText));
    }

    @Override
    public void checkIfUserSignIn() {
        loginSubject.onNext(authRepository.checkIfUserLoggedIn());
    }
}
