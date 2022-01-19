package com.velvet.trackerforsleepwalkers.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.App;
import com.velvet.trackerforsleepwalkers.R;

import javax.inject.Inject;

public class AuthRepository implements AuthNetwork {
    @Inject
    FirebaseAuth firebaseAuth;

    private boolean isLoginSuccessful;

    private Integer infoText;

    private void setIsLoginSuccessful(boolean isLoginSuccesful) {
        this.isLoginSuccessful = isLoginSuccesful;
    }

    private void setInfoText(Integer infoText) {
        this.infoText = infoText;
    }

    public AuthRepository() {
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public boolean checkIfUserLoggedIn() {
        if (firebaseAuth.getCurrentUser() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                setInfoText(R.string.success_registration);
            } else {
                setInfoText(R.string.invalid_email_or_password);
            }
        });
        return infoText;
    }

    @Override
    public boolean login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                setIsLoginSuccessful(true);
            } else {
                setIsLoginSuccessful(false);
            }
        });
        return isLoginSuccessful;
    }
}
