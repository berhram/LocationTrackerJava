package com.velvet.trackerforsleepwalkers.models.auth;

import com.velvet.trackerforsleepwalkers.models.result.Result;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface AuthNetwork {
    boolean checkIfUserLoggedIn();

    @NonNull Single<Result<Boolean>> login(String email, String password);

    @NonNull Single<Result<Boolean>> register(String email, String password);

    @NonNull Single<Result<FirebaseAuthMessages.RecoveryResult>> requestCode(String email);

    @NonNull Single<Result<FirebaseAuthMessages.RecoveryResult>> checkCode(String code, String newPassword);
}
