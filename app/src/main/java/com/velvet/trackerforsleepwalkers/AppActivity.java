package com.velvet.trackerforsleepwalkers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.velvet.trackerforsleepwalkers.ui.login.LoginContract;
import com.velvet.trackerforsleepwalkers.ui.login.LoginFragmentDirections;

public class AppActivity extends AppCompatActivity implements LoginContract.Host {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
    }

    @Override
    public void proceedToNextScreen() {
        navController.navigate(LoginFragmentDirections.loginScreenToMapScreen());
    }

    @Override
    public void proceedToPasswordRecovery() {
        navController.navigate(LoginFragmentDirections.loginScreenToPassordRecoveryScreen());
    }
}