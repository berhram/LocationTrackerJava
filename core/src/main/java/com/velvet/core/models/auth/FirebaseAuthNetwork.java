package com.velvet.core.models.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthNetwork implements AuthNetwork {
    @Inject
    FirebaseAuth firebaseAuth;

    public FirebaseAuthNetwork() {
        //TODO add DI
        firebaseAuth.useAppLanguage();
    }

    @Override
    public Single<Result<Boolean>> checkIfUserLoggedIn() {
        return Single.just(Result.success(firebaseAuth.getCurrentUser() != null));
    }

    @Override
    public Single<Result<Boolean>> sendMessage(AuthMessage message) {
        return Single.fromCallable(() -> {
            if (Values.LOGIN.equals(message.getId())) {
                final Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(message.getFirstParam(), message.getSecondParam());
                if (task.isSuccessful()) {
                    return Result.success(true);
                } else {
                    return Result.error(task.getException());
                }
            } else if (Values.REGISTER.equals(message.getId())) {
                final Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(message.getFirstParam(), message.getSecondParam());
                if (task.isSuccessful()) {
                    return Result.success(true);
                } else {
                    return Result.error(task.getException());
                }
            } else if (Values.REQUEST.equals(message.getId())) {
                final Task<Void> task = firebaseAuth.sendPasswordResetEmail(message.getFirstParam());
                if (task.isSuccessful()) {
                    return Result.success(true);
                } else {
                    return Result.error(task.getException());
                }
            } else if (Values.CHECK.equals(message.getId())) {
                final Task<Void> task = firebaseAuth.confirmPasswordReset(message.getFirstParam(), message.getSecondParam());
                if (task.isSuccessful()) {
                    return Result.success(true);
                } else {
                    return Result.error(task.getException());
                }
            } else {
                return Result.error(new Exception("Wrong message id!"));
            }
        });
    }
}
