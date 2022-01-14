package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.velvet.trackerforsleepwalkers.mvi.MviAction;

interface LoginAction extends MviAction {
    @AutoValue
    abstract class SignInAction implements LoginAction {
        abstract String email();
        abstract String password();
        public static SignInAction create(@NonNull String email, @NonNull String password) {
            return new AutoValue_LoginAction_SignInAction(email, password);
        }
    }

    @AutoValue
    abstract class SignUpAction implements LoginAction {
        abstract String email();
        abstract String password();
        public static SignUpAction create(@NonNull String email, @NonNull String password) {
            return new AutoValue_LoginAction_SignUpAction(email, password);
        }
    }

    @AutoValue
    abstract class ForgotPasswordAction implements LoginAction {
        public static ForgotPasswordAction create() {
            return new AutoValue_LoginAction_ForgotPasswordAction();
        }
    }

    @AutoValue
    abstract class InitialAction implements LoginAction {
        public static InitialAction create() {
            return new AutoValue_LoginAction_InitialAction();
        }
    }
}
