package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.velvet.trackerforsleepwalkers.mvi.MviViewState;

public class LoginViewState extends MviViewState<LoginContract.View> {
    private static final int ACTION_CHECK = 1;
    private static final int ACTION_SIGN_IN = 2;
    private static final int ACTION_SIGN_UP  = 3;
    private static final int ACTION_FORGOT_PASSWORD  = 4;

    private final int action;

    public LoginViewState(int action) {
        this.action = action;
    }

    @Override
    public void visit(LoginContract.View screen) {
        if (action == ACTION_CHECK ) {

        } else if (action == ACTION_SIGN_IN) {


        } else if (action == ACTION_SIGN_UP) {


        } else {

        }
    }
}
