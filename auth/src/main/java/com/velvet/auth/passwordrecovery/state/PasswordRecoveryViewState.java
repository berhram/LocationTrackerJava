package com.velvet.auth.passwordrecovery.state;

import com.velvet.auth.passwordrecovery.PasswordRecoveryContract;
import com.velvet.libs.mvi.MviViewState;

public class PasswordRecoveryViewState implements MviViewState<PasswordRecoveryContract.View> {
    private static final int ACTION_SET_TEXT = 1;
    private static final int ACTION_SET_PASSWORD_ERROR = 2;
    private static final int ACTION_SET_EMAIL_ERROR = 3;

    private final int action;
    private final int text;

    public PasswordRecoveryViewState(int action, int infoText) {
        this.action = action;
        this.text = infoText;
    }

    static public PasswordRecoveryViewState createSetTextState(int text) {
        return new PasswordRecoveryViewState(ACTION_SET_TEXT, text);
    }

    static public PasswordRecoveryViewState createSetEmailErrorState(int text) {
        return new PasswordRecoveryViewState(ACTION_SET_EMAIL_ERROR, text);
    }

    static public PasswordRecoveryViewState createSetPasswordErrorState(int text) {
        return new PasswordRecoveryViewState(ACTION_SET_PASSWORD_ERROR, text);
    }

    @Override
    public void visit(PasswordRecoveryContract.View screen) {
        if (action == ACTION_SET_TEXT) {
            screen.setInfoText(text);
        } else if (action == ACTION_SET_PASSWORD_ERROR) {
            screen.setPasswordError(text);
        } else if (action == ACTION_SET_EMAIL_ERROR) {
            screen.setEmailError(text);
        }
    }
}
