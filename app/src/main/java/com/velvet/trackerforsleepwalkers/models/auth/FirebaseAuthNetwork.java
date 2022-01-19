package com.velvet.trackerforsleepwalkers.models.auth;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.App;
import com.velvet.trackerforsleepwalkers.models.result.Result;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthNetwork implements AuthNetwork {
    @Inject
    FirebaseAuth firebaseAuth;

    public FirebaseAuthNetwork() {
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public boolean checkIfUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public @NonNull Single<Result<Boolean>> register(String email, String password) {
        return Single.fromCallable(() -> {
            final Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(email, password);
            Tasks.await(task);
            if (task.isSuccessful()) {
                return Result.success(true); // or what data you need
            } else {
                return Result.error(task.getException());
            }
        });
    }

    @Override
    public @NonNull Single<Result<Boolean>> login(String email, String password) {
        return Single.fromCallable(() -> {
            final Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(email, password);
            Tasks.await(task);
            if (task.isSuccessful()) {
                return Result.success(true); // or what data you need
            } else {
                return Result.error(task.getException());
            }
        });
    }
}
