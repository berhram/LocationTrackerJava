package com.velvet.auth.login;


import com.velvet.mvi.AbstractEffect;

public class LoginViewEffect extends AbstractEffect<LoginContract.View> {
    private static final int ACTION_NEXT = 1;

    private final int action;

    public LoginViewEffect(int action) {
        this.action = action;
    }

    static public LoginViewEffect proceedToNextScreen() {
        return new LoginViewEffect(ACTION_NEXT);
    }

    @Override
    public void handle(LoginContract.View screen) {
        if (action == ACTION_NEXT) {
            screen.proceedToNextScreen();
        }
    }
}
