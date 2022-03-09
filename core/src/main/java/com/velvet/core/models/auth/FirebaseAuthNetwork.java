package com.velvet.core.models.auth;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
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
                final Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(message.getFirstParam(), message.getSecondParam());
                Tasks.await(task);
                if (task.isSuccessful()) {
                    Log.d("AUTH", "taskValidator: Success");
                    return Result.success(Values.LOGIN);
                } else {
                    Log.d("AUTH", "taskValidator: Failure");
                    return new Result<>(Values.LOGIN, task.getException());
                }
            } else if (Values.REGISTER.equals(message.getId())) {
                final Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(message.getFirstParam(), message.getSecondParam());
                Tasks.await(task);
                if (task.isSuccessful()) {
                    Log.d("AUTH", "taskValidator: Success");
                    return Result.success(Values.REGISTER);
                } else {
                    Log.d("AUTH", "taskValidator: Failure");
                    return new Result<>(Values.REGISTER, task.getException());
                }
            } else if (Values.REQUEST.equals(message.getId())) {
                final Task<Void> task = firebaseAuth.sendPasswordResetEmail(message.getFirstParam());
                Tasks.await(task);
                if (task.isSuccessful()) {
                    Log.d("AUTH", "taskValidator: Success");
                    return Result.success(Values.REQUEST);
                } else {
                    Log.d("AUTH", "taskValidator: Failure");
                    return new Result<>(Values.REQUEST, task.getException());
                }
            } else if (Values.CHECK.equals(message.getId())) {
                final Task<Void> task = firebaseAuth.confirmPasswordReset(message.getFirstParam(), message.getSecondParam());
                Tasks.await(task);
                if (task.isSuccessful()) {
                    Log.d("AUTH", "taskValidator: Success");
                    return Result.success(Values.CHECK);
                } else {
                    Log.d("AUTH", "taskValidator: Failure");
                    return new Result<>(Values.CHECK, task.getException());
                }
            } else {
                return Result.error(new Exception("Wrong message id!"));
            }
        });
    }

    @Override
    public Completable signOut() {
        return Completable.fromRunnable(firebaseAuth::signOut);
    }
}
