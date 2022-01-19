package com.velvet.trackerforsleepwalkers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.velvet.trackerforsleepwalkers.databinding.ActivityMainBinding;
import com.velvet.trackerforsleepwalkers.ui.login.LoginContract;
import com.velvet.trackerforsleepwalkers.ui.login.LoginFragmentDirections;

public class AppActivity extends AppCompatActivity implements LoginContract.Host {

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
    public void proceedToNextScreen() {
        navController.navigate(LoginFragmentDirections.loginScreenToMapScreen());
    }

    @Override
    public void proceedToPasswordRecovery() {
        navController.navigate(LoginFragmentDirections.loginScreenToPassordRecoveryScreen());
    }
}