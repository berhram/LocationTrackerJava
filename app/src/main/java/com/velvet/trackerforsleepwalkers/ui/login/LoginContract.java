package com.velvet.trackerforsleepwalkers.ui.login;

import com.velvet.trackerforsleepwalkers.mvi.FragmentContract;

public class LoginContract {
    public interface ViewModel extends FragmentContract.ViewModel<LoginViewState> {
        void signIn(String email, String password);

        void signUp(String email, String password);

        void setInfoText(int infoText);

        void checkIfUserSignIn();

        void success();
    }

    public interface View extends FragmentContract.View {
        void setInfoText(int infoText);

        void proceedToPasswordRecovery();

        void proceedToNextScreen();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPasswordRecovery();

        void proceedToNextScreen();
    }
}
