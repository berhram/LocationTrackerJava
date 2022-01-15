package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;

import com.velvet.trackerforsleepwalkers.App;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;
import com.velvet.trackerforsleepwalkers.utils.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.ObservableTransformer;

public class LoginActionProcessorHolder {
    @NonNull
    @Inject
    AuthRepository mAuthRepository;

    @NonNull
    @Inject
    BaseSchedulerProvider mSchedulerProvider;

    public LoginActionProcessorHolder() {
        App.getInstance().getAppComponent().inject(this);
    }

    private ObservableTransformer<LoginAction.InitialAction, LoginResult.Initial> initialActionProcessor =
            action -> action.map(initialAction -> mAuthRepository.checkIfUserLoggedIn())
            .map(LoginResult.Initial::success)
            .onErrorReturn(LoginResult.Initial::failure)
            .subscribeOn(mSchedulerProvider.io())
            .observeOn(mSchedulerProvider.ui())
            .startWith(LoginResult.Initial.inFlight());
}
