package com.velvet.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.velvet.core.services.TrackerService;
import com.velvet.auth.login.LoginContract;
import com.velvet.auth.passwordrecovery.PasswordRecoveryContract;
import com.velvet.core.preferences.SharedPreferenceProvider;
import com.velvet.tracker.TrackerContract;
import com.velvet.tracker.services.TrackerService;

import app.R;
import app.databinding.ActivityMainBinding;

public class AppActivity extends AppCompatActivity implements LoginContract.Host, PasswordRecoveryContract.Host, TrackerContract.Host, AppActivityContract {
    private NavController navController;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<String[]> locationPermissionRequest;
    private SharedPreferenceProvider preferences;

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
        preferences = new SharedPreferenceProvider(this);
        if (preferences.get("Source", "").equals("")) {
            preferences.put("Source", LocationManager.GPS_PROVIDER);
        }
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
        } else if (id.equals("Tracker")) {
            //navController.navigate();
        }
    }

    @Override
    public void proceedToPasswordRecovery() {
        //navController.navigate();
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

    public void startService() {
        getApplicationContext().startService(new Intent(this, TrackerService.class));
    }
}