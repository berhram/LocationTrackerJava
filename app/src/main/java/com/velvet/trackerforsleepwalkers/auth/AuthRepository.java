package com.velvet.trackerforsleepwalkers.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.R;

import io.reactivex.rxjava3.core.Single;

public class AuthRepository {
    private AuthRepository INSTANCE = null;

    private FirebaseAuth mAuth;

    private boolean isLoginSuccesful;

    private Integer infoText;

    private void setIsLoginSuccessful(boolean isLoginSuccesful) {
        this.isLoginSuccesful = isLoginSuccesful;
    }

    private void setInfoText(Integer infoText) {
        this.infoText = infoText;
    }

    public AuthRepository getINSTANCE(FirebaseAuth mAuth) {
        if (INSTANCE == null) {
            INSTANCE = new AuthRepository(mAuth);
        }
        return INSTANCE;
    }

    public AuthRepository(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public Single<Boolean> checkIfUserLoggedIn() {
        if (mAuth.getCurrentUser() == null) {
            return Single.just(false);
        } else {
            return Single.just(true);
        }
    }

    public Single<Integer> register(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                setInfoText(R.string.success_registration);
            } else {
                setInfoText(R.string.invalid_email_or_password);
            }
        });
        return Single.just(infoText);
    }

    public Single<Boolean> login(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                setIsLoginSuccessful(true);
            } else {
                setIsLoginSuccessful(false);
            }
        });
        return Single.just(isLoginSuccesful);
    }
}
