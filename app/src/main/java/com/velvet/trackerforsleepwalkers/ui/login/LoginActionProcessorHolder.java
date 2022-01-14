package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;

import com.velvet.trackerforsleepwalkers.auth.AuthRepository;
import com.velvet.trackerforsleepwalkers.utils.BaseSchedulerProvider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;

public class LoginActionProcessorHolder {
    @NonNull
    private AuthRepository mAuthRepository;
    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    public LoginActionProcessorHolder(AuthRepository mAuthRepository, BaseSchedulerProvider mSchedulerProvider) {
        this.mAuthRepository = mAuthRepository;
        this.mSchedulerProvider = mSchedulerProvider;
    }

    private ObservableTransformer<LoginAction.InitialAction, LoginAction.InitialAction> initialActionProcessor =
            action -> action.flatMap(initialAction -> )
            .to
}
