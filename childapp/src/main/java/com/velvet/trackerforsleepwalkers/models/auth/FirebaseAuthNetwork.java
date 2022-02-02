package com.velvet.trackerforsleepwalkers.models.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.App;
import com.velvet.sharedcodelibrary.result.Result;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthNetwork implements AuthNetwork {
    @Inject
    FirebaseAuth firebaseAuth;

    public FirebaseAuthNetwork() {
        App.getInstance().getComponent().inject(this);
        firebaseAuth.useAppLanguage();
    }

    @Override
    public boolean checkIfUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public @NonNull Single<Result<Boolean>> register(String email, String password) {
        return Single.fromCallable(() -> {
            final Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(email, password);
            if (task.isSuccessful()) {
                return Result.success(true);
            } else {
                return Result.error(task.getException());
            }
        });
    }

    @Override
    public @NonNull Single<Result<Boolean>> login(String email, String password) {
        return Single.fromCallable(() -> {
            final Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(email, password);
            if (task.isSuccessful()) {
                return Result.success(true);
            } else {
                return Result.error(task.getException());
            }
        });
    }

    @Override
    public @NonNull Single<Result<FirebaseAuthMessages.RecoveryResult>> requestCode(String email) {
        return Single.fromCallable(() -> {
            final Task<Void> task = firebaseAuth.sendPasswordResetEmail(email);
            if (task.isSuccessful()) {
                return Result.success(new FirebaseAuthMessages.RecoveryResult(true, "request"));
            } else {
                return Result.error(task.getException());
            }
        });
    }

    @Override
    public @NonNull Single<Result<FirebaseAuthMessages.RecoveryResult>> checkCode(String code, String newPassword) {
        return Single.fromCallable(() -> {
            final Task<Void> task = firebaseAuth.confirmPasswordReset(code, newPassword);
            if (task.isSuccessful()) {
                return Result.success(new FirebaseAuthMessages.RecoveryResult(true, "check"));
            } else {
                return Result.error(task.getException());
            }
        });
    }
}
