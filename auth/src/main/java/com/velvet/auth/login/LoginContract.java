package com.velvet.auth.login;


import com.velvet.auth.login.state.LoginViewEffect;
import com.velvet.auth.login.state.LoginViewState;
import com.velvet.libs.mvi.FragmentContract;

public class LoginContract {
    public interface ViewModel extends FragmentContract.ViewModel<LoginViewState, LoginViewEffect> {
        void signIn(String email, String password);

        void signUp(String email, String password);
    }

    public interface View extends FragmentContract.View {
        void setInfoText(int infoText);

        void setEmailError(int text);

        void setPasswordError(int text);

        void proceedToNextScreen();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPasswordRecovery(String id);

        void proceedToNextScreen(String id);
    }
}
