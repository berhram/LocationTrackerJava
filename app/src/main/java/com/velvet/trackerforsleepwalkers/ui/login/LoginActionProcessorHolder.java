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
    private AuthRepository mAuthRepository;
    @NonNull
    @Inject
    private BaseSchedulerProvider mSchedulerProvider;

    public LoginActionProcessorHolder() {
        App.getInstance().getLoginComponent().inject(this);
    }

    private ObservableTransformer<LoginAction.InitialAction, LoginAction.InitialAction> initialActionProcessor =
            action -> action.flatMap(initialAction -> )
            .to
}
