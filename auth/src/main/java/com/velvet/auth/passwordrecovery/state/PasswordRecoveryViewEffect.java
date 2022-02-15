package com.velvet.auth.passwordrecovery.state;

import com.velvet.auth.passwordrecovery.PasswordRecoveryContract;
import com.velvet.libs.mvi.AbstractEffect;

public class PasswordRecoveryViewEffect extends AbstractEffect<PasswordRecoveryContract.View> {
    private static final int ACTION_PREVIOUS = 1;

    private final int action;

    public PasswordRecoveryViewEffect(int action) {
        this.action = action;
    }

    static public PasswordRecoveryViewEffect proceedToPreviousScreen() {
        return new PasswordRecoveryViewEffect(ACTION_PREVIOUS);
    }

    @Override
    public void handle(PasswordRecoveryContract.View screen) {
        if (action == ACTION_PREVIOUS) {
            screen.proceedToLoginScreen();
        }
    }
}
