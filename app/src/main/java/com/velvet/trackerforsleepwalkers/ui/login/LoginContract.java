package com.velvet.trackerforsleepwalkers.ui.login;

import com.velvet.trackerforsleepwalkers.mvi.FragmentContract;

public class LoginContract {
    public interface ViewModel extends FragmentContract.ViewModel<LoginViewState> {
        void setEmail(String email);

        void setPassword(String password);

        void setInfoText(int infoText);
    }

    public interface View extends FragmentContract.View {
        void setInfoText(int infoText);

        void setEmail(String email);

        void setPassword(String password);

        void proceedToPasswordRecovery();

        void proceedToNextScreen();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPasswordRecovery();

        void proceedToNextScreen();
    }
}
