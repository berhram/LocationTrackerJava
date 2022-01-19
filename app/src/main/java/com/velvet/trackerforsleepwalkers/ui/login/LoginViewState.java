package com.velvet.trackerforsleepwalkers.ui.login;

import com.velvet.trackerforsleepwalkers.mvi.MviViewState;

public class LoginViewState extends MviViewState<LoginContract.View> {
    private static final int ACTION_INITIAL = 1;
    private static final int ACTION_SIGN_IN = 2;
    private static final int ACTION_SIGN_UP  = 3;
    private static final int ACTION_FORGOT_PASSWORD  = 4;
    private static final int ACTION_SUCCESS  = 5;

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

    static public LoginViewState createSignInState(int infoText) {
        return new LoginViewState(ACTION_SIGN_IN, infoText);
    }

    static public LoginViewState createSignUpState(int infoText) {
        return new LoginViewState(ACTION_SIGN_UP, infoText);
    }

    static public LoginViewState createForgotPasswordState() {
        return new LoginViewState(ACTION_FORGOT_PASSWORD);
    }

    static public LoginViewState createSuccess() {
        return new LoginViewState(ACTION_SUCCESS);
    }

    @Override
    public void visit(LoginContract.View screen) {
        if (action == ACTION_INITIAL) {

        } else if (action == ACTION_SIGN_IN) {


        } else if (action == ACTION_SIGN_UP) {


        } else if (action == ACTION_FORGOT_PASSWORD) {
            screen.proceedToPasswordRecovery();
        } else if (action == ACTION_SUCCESS){
            screen.proceedToNextScreen();
        }
    }
}
