package com.velvet.trackerforsleepwalkers.ui.login;

import com.velvet.trackerforsleepwalkers.mvi.MviViewState;

public class LoginViewState extends MviViewState<LoginContract.View> {
    private static final int ACTION_INITIAL = 1;
    private static final int ACTION_SET_TEXT = 2;
    private static final int ACTION_SUCCESS  = 3;

    private final int action;

    private int infoText;

    public LoginViewState(int action) {
        this.action = action;
    }

    public LoginViewState(int action, int infoText) {
        this.action = action;
        this.infoText = infoText;
    }

    static public LoginViewState createInitialState() {
        return new LoginViewState(ACTION_INITIAL);
    }

    static public LoginViewState createSetTextState(int infoText) {
        return new LoginViewState(ACTION_SET_TEXT, infoText);
    }

    static public LoginViewState createSuccess() {
        return new LoginViewState(ACTION_SUCCESS);
    }


    @Override
    public void visit(LoginContract.View screen) {
        if (action == ACTION_INITIAL) {
        } else if (action == ACTION_SET_TEXT) {
            screen.setInfoText(infoText);
        } else if (action == ACTION_SUCCESS) {
            screen.proceedToNextScreen();
        }
    }
}
