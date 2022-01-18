package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModel extends MviViewModel<LoginViewState> implements LoginContract.ViewModel {
    private final AuthNetwork authRepository;
    //I don't understand what 'subjects' do. It is like LiveData and UI should observe them?
    private final PublishSubject<Boolean> isSuccess = PublishSubject.create();
    private final BehaviorSubject<String> email = BehaviorSubject.create();
    private final BehaviorSubject<String> password = BehaviorSubject.create();
    private final BehaviorSubject<String> infoText = BehaviorSubject.create();


    public LoginViewModel(AuthNetwork authRepository) {
        this.authRepository = authRepository;
    }

    //what's supposed to happen here? setup of ViewModel?
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                isSuccess.
            );
        }
    }

    @Override
    protected LoginViewState getDefaultState() {
        return LoginViewState.createCheckState();
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setInfoText(int infoText) {

    }
}
