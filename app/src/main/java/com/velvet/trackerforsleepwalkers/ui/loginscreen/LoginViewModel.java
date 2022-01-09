package com.velvet.trackerforsleepwalkers.ui.loginscreen;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginInput = new MutableLiveData<>();
    private final MutableLiveData<String> passwordInput = new MutableLiveData<>();
    private FirebaseAuth auth;

    public MutableLiveData<String> getLoginInput() {
        return loginInput;
    }

    public MutableLiveData<String> getPasswordInput() {
        return passwordInput;
    }

    public void register(String login, String password) {
    }
}
