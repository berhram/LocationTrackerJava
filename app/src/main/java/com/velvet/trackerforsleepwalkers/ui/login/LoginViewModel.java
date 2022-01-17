package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;

public class LoginViewModel extends MviViewModel<LoginViewState> implements LoginContract.ViewModel {
    @Override
    public LiveData<LoginViewState> getStateObservable() {
        return super.getStateObservable();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
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

    @Override
    protected LoginViewState getDefaultState() {
        return null;
    }
}
