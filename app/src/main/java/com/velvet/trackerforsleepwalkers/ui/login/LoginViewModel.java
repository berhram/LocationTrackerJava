package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends ViewModel implements MviViewModel<LoginIntent, LoginViewState> {
    @NonNull
    private PublishSubject<LoginIntent> mIntentsSubject;
    @NonNull
    private Observable<LoginViewState> mStatesObservable;
    @NonNull
    private CompositeDisposable mDisposables = new CompositeDisposable();
    @NonNull
    private LoginActionProcessorHolder mLoginProcessorHolder;

    private final MutableLiveData<String> loginInput = new MutableLiveData<>();
    private final MutableLiveData<String> passwordInput = new MutableLiveData<>();
    private final MutableLiveData<Integer> infoText = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authenticationState = new MutableLiveData<>();
    private FirebaseAuth mAuth;

    public LoginViewModel(@NonNull LoginActionProcessorHolder processorHolder) {
        mLoginProcessorHolder = processorHolder;
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

    private Observable<LoginViewState> compose() {
        return mIntentsSubject
                .compose()
                .map(this::a)
                .compose(mLoginProcessorHolder.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(TaskDetailViewState.idle(), reducer)
                // When a reducer just emits previousState, there's no reason to call render. In fact,
                // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
                // by showing the same snackbar twice in rapid succession).
                .distinctUntilChanged()
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0);

    public MutableLiveData<String> getLoginInput() {
        return loginInput;
    }

    public MutableLiveData<String> getPasswordInput() {
        return passwordInput;
    }

    public MutableLiveData<Boolean> getAuthenticationState() {
        return authenticationState;
    }

    public MutableLiveData<Integer> getInfoText() {
        return infoText;
    }

    public void setup() {
        if (mAuth.getCurrentUser() != null) {
            getAuthenticationState().setValue(true);
        } else {
            getAuthenticationState().setValue(false);
        }
    }

    public void register(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                infoText.setValue(R.string.success_registration);
            } else {
                infoText.setValue(R.string.invalid_email_or_password);
            }
        });

    }

    public void login(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                authenticationState.setValue(true);
            } else {
                infoText.setValue(R.string.invalid_email_or_password);
            }
        });
    }
}
