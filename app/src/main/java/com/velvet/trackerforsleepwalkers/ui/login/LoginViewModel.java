package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.models.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.models.auth.FirebaseAuthMessages;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends MviViewModel<LoginContract.View, LoginViewState, LoginViewEffect> implements LoginContract.ViewModel {
    private final AuthNetwork authRepository;
    private final PublishSubject<Boolean> loginSubject = PublishSubject.create();
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
                                if ("register".equals(p.getType())) {
                                    return authRepository.register(p.getEmail(), p.getPassword()).toObservable();
                                } else {
                                    return authRepository.login(p.getEmail(), p.getPassword()).toObservable();
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
        infoTextSubject.onNext(new FirebaseAuthMessages.AuthParams(email, password, "login"));
    }

    @Override
    public void signUp(String email, String password) {
        infoTextSubject.onNext(new FirebaseAuthMessages.AuthParams(email, password, "register"));
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
