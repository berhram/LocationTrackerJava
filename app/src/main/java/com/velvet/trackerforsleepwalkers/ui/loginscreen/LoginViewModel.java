package com.velvet.trackerforsleepwalkers.ui.loginscreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginInput = new MutableLiveData<>();
    private final MutableLiveData<String> passwordInput = new MutableLiveData<>();

    public MutableLiveData<String> getLoginInput() {
        return loginInput;
    }

    public MutableLiveData<String> getPasswordInput() {
        return passwordInput;
    }
}
