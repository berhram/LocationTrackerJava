package com.velvet.core.models.auth;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface AuthNetwork {
    @NonNull Single<Result<Boolean>> checkIfUserLoggedIn();
    @NonNull Single<Result<Boolean>> sendMessage(AuthMessage message);
}
