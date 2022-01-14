package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.velvet.trackerforsleepwalkers.mvi.MviViewState;

@AutoValue
abstract class LoginViewState implements MviViewState {
    abstract boolean isPasswordForgotten();

    abstract boolean isSignInSuccess();

    abstract String infoText();

    public abstract Builder buildWith();

    @Nullable
    abstract Throwable error();

    static LoginViewState idle() {
        return new AutoValue_LoginViewState.Builder()
                .isPasswordForgotten(false)
                .isSignInSuccess(false)
                .error(null)
                .infoText("")
                .build();
    }

    @AutoValue.Builder
    static abstract class Builder {
        abstract Builder isPasswordForgotten(boolean isPasswordForgotten);

        abstract Builder isSignInSuccess(boolean isSignInSuccess);

        abstract Builder infoText(String infoText);

        abstract Builder error(@NonNull Throwable error);

        abstract LoginViewState build();
    }
}
