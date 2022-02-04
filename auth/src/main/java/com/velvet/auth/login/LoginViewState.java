package com.velvet.auth.login;

import com.velvet.mvi.mvi.MviViewState;

public class LoginViewState implements MviViewState<LoginContract.View> {
    private static final int ACTION_SET_TEXT = 1;

    private final int action;

    private int infoText;

    public LoginViewState(int action, int infoText) {
        this.action = action;
        this.infoText = infoText;
    }

    static public LoginViewState createSetTextState(int infoText) {
        return new LoginViewState(ACTION_SET_TEXT, infoText);
    }

    @Override
    public void visit(LoginContract.View screen) {
        if (action == ACTION_SET_TEXT) {
            screen.setInfoText(infoText);
        }
    }
}
