package com.velvet.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.velvet.auth.login.LoginFragment;
import com.velvet.map.service.MapService;
import com.velvet.auth.login.LoginContract;
import com.velvet.auth.passwordrecovery.PasswordRecoveryContract;
import com.velvet.map.ui.MapContract;

import app.databinding.ActivityMainBinding;

public class AppActivity extends Activity implements LoginContract.Host, MapContract.Host, PasswordRecoveryContract.Host {
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
            //navController.navigate();
        }
    }

    @Override
    public void proceedToLoginScreen(String id) {
        if (id.equals("Password recovery")) {
            //navController.navigate();
        } else if (id.equals("Map")) {
            //navController.navigate();
        }
    }

    @Override
    public void proceedToPasswordRecovery() {
        //navController.navigate();
    }

    @Override
    public void startService() {
        getApplicationContext().startService(new Intent(this, MapService.class));
    }
}
