package com.velvet.auth.passwordrecovery;

import com.velvet.mvi.mvi.FragmentContract;

public class PasswordRecoveryContract {
    public interface ViewModel extends FragmentContract.ViewModel<PasswordRecoveryViewState, PasswordRecoveryViewEffect> {
        void requestCode(String email);

        void checkCode(String code, String newPassword);

        void setInfoText(int infoText);

        void success();
    }

    public interface View extends FragmentContract.View {
        void setInfoText(int infoText);

        void proceedToLoginScreen();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
