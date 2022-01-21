package com.velvet.trackerforsleepwalkers.ui.passwordrecovery;

import com.velvet.trackerforsleepwalkers.mvi.AbstractEffect;

public abstract class PasswordRecoveryViewEffect extends AbstractEffect<PasswordRecoveryContract.View> {
    static final class ProceedToPreviousScreen extends PasswordRecoveryViewEffect {
        @Override
        public void handle(PasswordRecoveryContract.View screen) {
            screen.proceedToPreviousScreen();
        }

    }
}
