package com.velvet.core.models.auth;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthNetwork implements AuthNetwork {

    @Inject
    FirebaseAuth firebaseAuth;

    public FirebaseAuthNetwork() {
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
                return taskValidator(firebaseAuth.signInWithEmailAndPassword(message.getFirstParam(), message.getSecondParam()));
            } else if (Values.REGISTER.equals(message.getId())) {
                return taskValidator(firebaseAuth.createUserWithEmailAndPassword(message.getFirstParam(), message.getSecondParam()));
            } else if (Values.REQUEST.equals(message.getId())) {
                return taskValidator(firebaseAuth.sendPasswordResetEmail(message.getFirstParam()));
            } else if (Values.CHECK.equals(message.getId())) {
                return taskValidator(firebaseAuth.confirmPasswordReset(message.getFirstParam(), message.getSecondParam()));
            } else {
                return Result.error(new Exception("Wrong message id!"));
            }
        });
    }

    private Result<Boolean> taskValidator(Task task) {
        if (task.isSuccessful()) {
            Log.d("AUTH", "taskValidator: Success");
            return Result.success(true);
        } else {
            Log.d("AUTH", "taskValidator: Failure");
            return Result.error(task.getException());
        }
    }
}
