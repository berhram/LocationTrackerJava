package com.velvet.trackerforsleepwalkers.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.App;
import com.velvet.trackerforsleepwalkers.R;

import javax.inject.Inject;

public class AuthRepository implements AuthNetwork {
    @Inject
    FirebaseAuth firebaseAuth;


    private Integer infoText;

    private void setInfoText(Integer infoText) {
        this.infoText = infoText;
    }

    @Override
    public int getInfoText() {
        return infoText;
    }

    public AuthRepository() {
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public boolean checkIfUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;

    }

    @Override
    public int register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            int message = task.isSuccessful() ? R.string.success_registration : R.string.user_already_registered;
            setInfoText(message);
        });
        return infoText;
    }

    @Override
    public int login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            int message = task.isSuccessful() ? R.string.success_login : R.string.invalid_email_or_password;
            setInfoText(message);
        });
        return infoText;
    }
}
