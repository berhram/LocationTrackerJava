package com.velvet.trackerforsleepwalkers.ui.loginscreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.R;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginInput = new MutableLiveData<>();
    private final MutableLiveData<String> passwordInput = new MutableLiveData<>();
    private final MutableLiveData<Integer> infoText = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authenticationState = new MutableLiveData<>();
    private FirebaseAuth mAuth;

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
