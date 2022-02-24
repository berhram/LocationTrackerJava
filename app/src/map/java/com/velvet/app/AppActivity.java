package com.velvet.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.velvet.auth.login.LoginContract;
import com.velvet.auth.login.LoginFragmentDirections;
import com.velvet.auth.passwordrecovery.PasswordRecoveryContract;
import com.velvet.auth.passwordrecovery.PasswordRecoveryFragmentDirections;
import com.velvet.map.ui.MapContract;

import app.databinding.ActivityMainBinding;

public class AppActivity extends FragmentActivity implements LoginContract.Host, MapContract.Host, PasswordRecoveryContract.Host {
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
            navController.navigate(LoginFragmentDirections.loginToMap());
        }
    }

    @Override
    public void proceedToLoginScreen(String id) {
        if (id.equals("Password recovery")) {
            navController.navigate(PasswordRecoveryFragmentDirections.recoveryToLogin());
        } else if (id.equals("Map")) {
            navController.navigate(MapFragmentDirections.mapToLogin());
        }
    }

    @Override
    public void proceedToPasswordRecovery() {
        navController.navigate(LoginFragmentDirections.loginToRecovery());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navController = null;
        binding = null;
    }
}
