package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.velvet.trackerforsleepwalkers.mvi.MviResult;
import com.velvet.trackerforsleepwalkers.utils.LceStatus;

interface LoginResult extends MviResult {
    @AutoValue
    abstract class SignIn implements LoginResult {
        @NonNull
        abstract LceStatus status();

        @Nullable
        abstract String infoText();

        @Nullable
        abstract Boolean isPasswordForgotten();

        @Nullable
        abstract Boolean isSignInSuccess();
        @Nullable
        abstract Throwable error();

        @NonNull
        static SignIn success(boolean isPasswordForgotten, boolean isSignInSuccess, String infoText) {
            return new AutoValue_LoginResult_SignIn(LceStatus.SUCCESS, infoText, isPasswordForgotten, isSignInSuccess,  null);
        }

        @NonNull
        static SignIn failure(Throwable error) {
            return new AutoValue_LoginResult_SignIn(LceStatus.FAILURE, null, null, null, error);
        }

        @NonNull
        static SignIn inFlight() {
            return new AutoValue_LoginResult_SignIn(LceStatus.IN_FLIGHT, null, null, null, null);
        }
    }

    @AutoValue
    abstract class SignUp implements LoginResult {
        @NonNull
        abstract LceStatus status();

        @Nullable
        abstract String infoText();

        @Nullable
        abstract Boolean isPasswordForgotten();

        @Nullable
        abstract Boolean isSignInSuccess();
        @Nullable
        abstract Throwable error();

        @NonNull
        static SignUp success(boolean isPasswordForgotten, boolean isSignInSuccess, String infoText) {
            return new AutoValue_LoginResult_SignUp(LceStatus.SUCCESS, infoText, isPasswordForgotten, isSignInSuccess,  null);
        }

        @NonNull
        static SignUp failure(Throwable error) {
            return new AutoValue_LoginResult_SignUp(LceStatus.FAILURE, null, null, null, error);
        }

        @NonNull
        static SignUp inFlight() {
            return new AutoValue_LoginResult_SignUp(LceStatus.IN_FLIGHT, null, null, null, null);
        }
    }

    @AutoValue
    abstract class ForgotPassword implements LoginResult {
        @Nullable
        abstract Boolean isPasswordForgotten();

        @Nullable
        abstract Boolean isSignInSuccess();

        @NonNull
        static ForgotPassword success(boolean isPasswordForgotten, boolean isSignInSuccess, String infoText) {
            return new AutoValue_LoginResult_ForgotPassword(LceStatus.SUCCESS, infoText, isPasswordForgotten, isSignInSuccess,  null);
        }

    }
}
