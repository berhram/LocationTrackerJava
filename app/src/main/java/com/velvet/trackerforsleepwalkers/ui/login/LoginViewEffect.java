package com.velvet.trackerforsleepwalkers.ui.login;

import com.velvet.trackerforsleepwalkers.mvi.AbstractEffect;

public abstract class LoginViewEffect extends AbstractEffect<LoginContract.View> {
    static final class ProceedToNextScreen extends LoginViewEffect {
        @Override
        public void handle(LoginContract.View screen) {
            screen.proceedToNextScreen();
        }

    }
}
