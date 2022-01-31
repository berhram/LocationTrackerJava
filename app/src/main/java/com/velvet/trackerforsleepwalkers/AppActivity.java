package com.velvet.trackerforsleepwalkers;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.velvet.trackerforsleepwalkers.databinding.ActivityMainBinding;
import com.velvet.trackerforsleepwalkers.models.location.LocationService;
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
    private ActivityResultLauncher<String[]> locationPermissionRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    if (!result.get(Manifest.permission.ACCESS_FINE_LOCATION) || !result.get(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        Toast.makeText(this, R.string.please_give_access, Toast.LENGTH_SHORT).show();
                    }
                });
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

    public void checkOrRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION});
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            }
        }
    }

    public void startLocationService() {
        getApplicationContext().startService(new Intent(this, LocationService.class));
    }
}