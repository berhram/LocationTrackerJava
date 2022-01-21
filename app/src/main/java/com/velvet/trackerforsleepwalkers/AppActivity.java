package com.velvet.trackerforsleepwalkers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.velvet.trackerforsleepwalkers.databinding.ActivityMainBinding;
import com.velvet.trackerforsleepwalkers.ui.login.LoginContract;
import com.velvet.trackerforsleepwalkers.ui.login.LoginFragmentDirections;
import com.velvet.trackerforsleepwalkers.ui.passwordrecovery.PasswordRecoveryContract;
import com.velvet.trackerforsleepwalkers.ui.passwordrecovery.PasswordRecoveryFragmentDirections;

public class AppActivity extends AppCompatActivity implements LoginContract.Host, PasswordRecoveryContract.Host {

    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navController = Navigation.findNavController(this, binding.fragmentContainerView.getId());
    }

    @Override
    public void proceedToNextScreen(String id) {
        if (id.equals("Login")) {
            navController.navigate(LoginFragmentDirections.loginScreenToMapScreen());
        } else if (id.equals("Password recovery")) {
            navController.navigate(PasswordRecoveryFragmentDirections.passwordRecoveryScreenToLoginScreen());
        }
    }

    @Override
    public void proceedToPreviousScreen(String id) {
        navController.navigate(LoginFragmentDirections.loginScreenToMapScreen());
    }

    @Override
    public void proceedToPasswordRecovery(String id) {
        navController.navigate(LoginFragmentDirections.loginScreenToPassordRecoveryScreen());
    }
}