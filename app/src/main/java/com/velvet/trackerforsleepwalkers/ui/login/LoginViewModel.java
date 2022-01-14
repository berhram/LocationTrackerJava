package com.velvet.trackerforsleepwalkers.ui.login;

import static com.velvet.trackerforsleepwalkers.utils.LceStatus.FAILURE;
import static com.velvet.trackerforsleepwalkers.utils.LceStatus.IN_FLIGHT;
import static com.velvet.trackerforsleepwalkers.utils.LceStatus.SUCCESS;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.velvet.trackerforsleepwalkers.mvi.MviIntent;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends ViewModel implements MviViewModel<LoginIntent, LoginViewState> {
    @NonNull
    private PublishSubject<LoginIntent> mIntentsSubject;
    @NonNull
    private Observable<LoginViewState> mStatesObservable;
    @NonNull
    private CompositeDisposable mDisposables = new CompositeDisposable();
    @NonNull
    private LoginActionProcessorHolder mLoginActionProcessorHolder;

    public LoginViewModel(LoginActionProcessorHolder mLoginActionProcessorHolder) {
        this.mLoginActionProcessorHolder = mLoginActionProcessorHolder;
        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
    }

    @Override
    public void processIntents(Observable<LoginIntent> intents) {
        mDisposables.add(intents.subscribe(mIntentsSubject::onNext));
    }

    @Override
    public Observable<LoginViewState> states() {
        return mStatesObservable;
    }

    private ObservableTransformer<LoginIntent, LoginIntent> intentFilter =
            intents -> intents.publish(shared ->
                    Observable.merge(
                            shared.ofType(LoginIntent.InitialIntent.class).take(1),
                            shared.filter(intent -> !(intent instanceof LoginIntent.InitialIntent))
                    )
            );

    @Override
    protected void onCleared() {
        mDisposables.dispose();
    }

    private LoginAction actionFromIntent(MviIntent intent) {
        if (intent instanceof LoginIntent.InitialIntent) {
            return LoginAction.InitialAction.create();
        }
        if (intent instanceof LoginIntent.SignInIntent) {
            LoginIntent.SignInIntent signInIntent = (LoginIntent.SignInIntent) intent;
            return LoginAction.SignInAction.create(signInIntent.email(), signInIntent.password());
        }
        if (intent instanceof LoginIntent.SignUpIntent) {
            LoginIntent.SignUpIntent signUpIntent = (LoginIntent.SignUpIntent) intent;
            return LoginAction.SignUpAction.create(signUpIntent.email(), signUpIntent.password());
        }
        if (intent instanceof LoginIntent.ForgotPasswordIntent) {
            return LoginAction.ForgotPasswordAction.create();
        }
        // Fail for unhandled intents
        throw new IllegalArgumentException("do not know how to treat this intent " + intent);
    }

    private Observable<LoginViewState> compose() {
        return mIntentsSubject.compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(mLoginActionProcessorHolder.actionProcessor)
                .scan();
    }

    private static BiFunction<LoginViewState, LoginResult, LoginViewState> reducer =
            (previousState, result) -> {
                LoginViewState.Builder stateBuilder = previousState.buildWith();
                if (result instanceof LoginResult) {
                    LoginResult.SignIn signInResult = (LoginResult.SignIn) result;
                    switch (signInResult.status()) {
                        case SUCCESS:
                            stateBuilder.infoText(signInResult.infoText());
                            stateBuilder.isPasswordForgotten(signInResult.isPasswordForgotten());
                            stateBuilder.isSignInSuccess(signInResult.isSignInSuccess());
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(signInResult.error()).build();
                        case IN_FLIGHT:
                            return stateBuilder.build();
                    }
                }
                if (result instanceof LoginResult) {
                    LoginResult.SignUp signUpResult = (LoginResult.SignUp) result;
                    switch (signUpResult.status()) {
                        case SUCCESS:
                            stateBuilder.infoText(signUpResult.infoText());
                            stateBuilder.isPasswordForgotten(signUpResult.isPasswordForgotten());
                            stateBuilder.isSignInSuccess(signUpResult.isSignInSuccess());
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(signUpResult.error()).build();
                        case IN_FLIGHT:
                            return stateBuilder.build();
                    }
                }
                if (result instanceof LoginResult) {
                    LoginResult.ForgotPassword forgotPasswordResult = (LoginResult.ForgotPassword) result;
                    stateBuilder.isPasswordForgotten(forgotPasswordResult.isPasswordForgotten());
                    stateBuilder.isSignInSuccess(forgotPasswordResult.isSignInSuccess());
                    return stateBuilder.build();
                }
                // Fail for unhandled results
                throw new IllegalStateException("Mishandled result? Should not happen―as always: " + result);
            };

}
