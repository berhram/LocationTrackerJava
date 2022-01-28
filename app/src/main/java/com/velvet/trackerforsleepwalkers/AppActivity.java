package com.velvet.trackerforsleepwalkers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

import com.velvet.trackerforsleepwalkers.databinding.ActivityMainBinding;
import com.velvet.trackerforsleepwalkers.ui.login.LoginContract;
import com.velvet.trackerforsleepwalkers.ui.login.LoginFragmentDirections;
import com.velvet.trackerforsleepwalkers.ui.map.MapContract;
import com.velvet.trackerforsleepwalkers.ui.map.MapFragmentDirections;
import com.velvet.trackerforsleepwalkers.ui.passwordrecovery.PasswordRecoveryContract;
import com.velvet.trackerforsleepwalkers.ui.passwordrecovery.PasswordRecoveryFragmentDirections;
import com.velvet.trackerforsleepwalkers.ui.settings.SettingsContract;
import com.velvet.trackerforsleepwalkers.ui.settings.SettingsFragmentDirections;

public class AppActivity extends AppCompatActivity implements LoginContract.Host, PasswordRecoveryContract.Host, SettingsContract.Host, MapContract.Host {

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
    public void proceedToMapScreen(String id) {
        if (id.equals("Login")) {
            navController.navigate(LoginFragmentDirections.loginScreenToMapScreen());
        } else if (id.equals("Settings")) {
            navController.navigate(SettingsFragmentDirections.settingsScreenToMapScreen());
        }
    }

    @Override
    public void proceedToLoginScreen(String id) {
        if (id.equals("Password recovery")) {
            navController.navigate(PasswordRecoveryFragmentDirections.passwordRecoveryScreenToLoginScreen());
        } else if (id.equals("Settings")) {
            navController.navigate(SettingsFragmentDirections.settingsScreenToLoginScreen());
        }
    }

    @Override
    public void proceedToPasswordRecovery() {
        navController.navigate(LoginFragmentDirections.loginScreenToPasswordRecoveryScreen());
    }


    @Override
    public void proceedToSettingsScreen(String id) {
        if (id.equals("Map")) {
            navController.navigate(MapFragmentDirections.mapScreenToSettingsScreen());
        }
    }
}