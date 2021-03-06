package com.velvet.auth.login.state;


import com.velvet.auth.login.LoginContract;
import com.velvet.libs.mvi.AbstractEffect;

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
