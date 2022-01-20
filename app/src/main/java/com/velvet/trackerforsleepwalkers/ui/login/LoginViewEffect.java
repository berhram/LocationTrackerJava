package com.velvet.trackerforsleepwalkers.ui.login;

import com.velvet.trackerforsleepwalkers.mvi.AbstractEffect;

public class LoginViewEffect extends AbstractEffect<LoginContract.View> {
    @Override
    public void handle(LoginContract.View screen) {
        screen.proceedToNextScreen();
    }
}
