package com.velvet.auth.login.state;

import com.velvet.auth.login.LoginContract;
import com.velvet.libs.mvi.MviViewState;

public class LoginViewState implements MviViewState<LoginContract.View> {
    private static final int ACTION_SET_TEXT = 1;
    private static final int ACTION_SET_PASSWORD_ERROR = 2;
    private static final int ACTION_SET_EMAIL_ERROR = 3;

    private final int action;
    private final int text;

    public LoginViewState(int action, int text) {
        this.action = action;
        this.text = text;
    }

    static public LoginViewState createSetTextState(int text) {
        return new LoginViewState(ACTION_SET_TEXT, text);
    }

    static public LoginViewState createSetEmailErrorState(int text) {
        return new LoginViewState(ACTION_SET_EMAIL_ERROR, text);
    }

    static public LoginViewState createSetPasswordErrorState(int text) {
        return new LoginViewState(ACTION_SET_PASSWORD_ERROR, text);
    }

    @Override
    public void visit(LoginContract.View screen) {
        if (action == ACTION_SET_TEXT) {
            screen.setInfoText(text);
        } else if (action == ACTION_SET_PASSWORD_ERROR) {
            screen.setPasswordError(text);
        } else if (action == ACTION_SET_EMAIL_ERROR) {
            screen.setEmailError(text);
        }
    }
}
