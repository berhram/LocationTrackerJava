package com.velvet.core.models.auth;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthNetwork implements AuthNetwork {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthNetwork() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.useAppLanguage();
    }

    @Override
    public Single<Result<Boolean>> checkIfUserLoggedIn() {
        return Single.just(Result.success(firebaseAuth.getCurrentUser() != null));
    }

    @Override
    public Single<Result<String>> authRequest(AuthMessage message) {
        return Single.fromCallable(() -> {
            if (Values.LOGIN.equals(message.getId())) {
                return taskValidator(Values.LOGIN ,firebaseAuth.signInWithEmailAndPassword(message.getFirstParam(), message.getSecondParam()));
            } else if (Values.REGISTER.equals(message.getId())) {
                return taskValidator(Values.REGISTER ,firebaseAuth.createUserWithEmailAndPassword(message.getFirstParam(), message.getSecondParam()));
            } else if (Values.REQUEST.equals(message.getId())) {
                return taskValidator(Values.REQUEST ,firebaseAuth.sendPasswordResetEmail(message.getFirstParam()));
            } else if (Values.CHECK.equals(message.getId())) {
                return taskValidator(Values.CHECK ,firebaseAuth.confirmPasswordReset(message.getFirstParam(), message.getSecondParam()));
            } else {
                return Result.error(new Exception("Wrong message id!"));
            }
        });
    }

    private Result<String> taskValidator(String type, Task task) {
        if (task.isSuccessful()) {
            Log.d("AUTH", "taskValidator: Success");
            return Result.success(type);
        } else {
            Log.d("AUTH", "taskValidator: Failure");
            return new Result<>(type, task.getException());
        }
    }

    @Override
    public Completable signOut() {
        return Completable.fromRunnable(firebaseAuth::signOut);
    }
}
