package com.velvet.trackerforsleepwalkers.ui.login;


import com.google.auto.value.AutoValue;
import com.velvet.trackerforsleepwalkers.mvi.MviIntent;

public interface LoginIntent extends MviIntent {
    @AutoValue
    abstract class InitialIntent implements LoginIntent {
        public static InitialIntent create() {
            return new AutoValue_LoginIntent_InitialIntent();
        }
    }

    @AutoValue
    abstract class SignInIntent implements LoginIntent {
        abstract String email();
        abstract String password();
        public static SignInIntent create(String email, String password) {
            return new AutoValue_LoginIntent_SignInIntent(email, password);
        }
    }

    @AutoValue
    abstract class SignUpIntent implements LoginIntent {
        abstract String email();
        abstract String password();
        public static SignUpIntent create(String email, String password) {
            return new AutoValue_LoginIntent_SignUpIntent(email, password);
        }
    }

    @AutoValue
    abstract class ForgotPasswordIntent implements LoginIntent {
        public static ForgotPasswordIntent create() {
            return new AutoValue_LoginIntent_ForgotPasswordIntent();
        }
    }
}
