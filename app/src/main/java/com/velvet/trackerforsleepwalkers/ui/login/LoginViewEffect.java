package com.velvet.trackerforsleepwalkers.ui.login;

import android.util.Log;

import com.velvet.trackerforsleepwalkers.mvi.AbstractEffect;

public abstract class LoginViewEffect extends AbstractEffect<LoginContract.View> {
    static class ProceedToNextScreen extends LoginViewEffect {
        @Override
        public void handle(LoginContract.View screen) {
            Log.d("Nav", "handle");
            screen.proceedToNextScreen();
        }
    }
}
