package com.velvet.core.models.auth;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import java.util.concurrent.ExecutionException;

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
            if (Values.LOGIN.equals(message.id)) {
                final Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(message.emailOrCode, message.pwd);
                return completeTask(task, message.id);
            } else if (Values.REGISTER.equals(message.id)) {
                final Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(message.emailOrCode, message.pwd);
                Tasks.await(task);
                return completeTask(task, message.id);
            } else if (Values.REQUEST.equals(message.id)) {
                final Task<Void> task = firebaseAuth.sendPasswordResetEmail(message.emailOrCode);
                Tasks.await(task);
                return completeTask(task, message.id);
            } else if (Values.CHECK.equals(message.id)) {
                final Task<Void> task = firebaseAuth.confirmPasswordReset(message.emailOrCode, message.pwd);
                return completeTask(task, message.id);
            } else {
                return Result.error(new Exception("Wrong message id!"));
            }
        });
    }

    @Override
    public Completable signOut() {
        return Completable.fromCallable(() -> {
            firebaseAuth.signOut();
            return Completable.complete();
        });
    }

    private Result<String> completeTask(Task task, String key) throws ExecutionException, InterruptedException {
        Tasks.await(task);
        if (task.isSuccessful()) {
            return Result.success(key);
        } else {
            return new Result<>(key, task.getException());
        }
    }
}
