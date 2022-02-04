package com.velvet.auth.passwordrecovery;

import com.velvet.mvi.mvi.MviViewState;

public class PasswordRecoveryViewState implements MviViewState<PasswordRecoveryContract.View> {
    private static final int ACTION_SET_TEXT = 1;

    private final int action;

    private int infoText;

    public PasswordRecoveryViewState(int action, int infoText) {
        this.action = action;
        this.infoText = infoText;
    }

    static public PasswordRecoveryViewState createSetTextState(int infoText) {
        return new PasswordRecoveryViewState(ACTION_SET_TEXT, infoText);
    }

    @Override
    public void visit(PasswordRecoveryContract.View screen) {
        if (action == ACTION_SET_TEXT) {
            screen.setInfoText(infoText);
        }
    }
}
