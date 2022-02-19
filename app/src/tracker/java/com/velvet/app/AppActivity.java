package com.velvet.app;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.velvet.auth.login.LoginContract;
import com.velvet.auth.login.LoginFragmentDirections;
import com.velvet.auth.passwordrecovery.PasswordRecoveryContract;
import com.velvet.auth.passwordrecovery.PasswordRecoveryFragmentDirections;
import com.velvet.core.Values;
import com.velvet.tracker.TrackerContract;
import com.velvet.tracker.TrackerFragment;
import com.velvet.tracker.TrackerFragmentDirections;
import com.velvet.tracker.services.TrackerService;

import app.R;
import app.databinding.ActivityMainBinding;

public class AppActivity extends FragmentActivity implements LoginContract.Host, PasswordRecoveryContract.Host, TrackerContract.Host {
    private NavController navController;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<String[]> locationPermissionRequest;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannel();
        }
        locationPermissionRequest = registerForActivityResult(new ActivityResultContracts
                .RequestMultiplePermissions(), result -> {
            if (!isLocationPermissionsGranted()) {
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
    public void proceedToNextScreen(String id) {
        if (id.equals("Login")) {
            navController.navigate(LoginFragmentDirections.loginToTracker());
        }
    }

    @Override
    public void proceedToLoginScreen(String id) {
        if (id.equals("Password recovery")) {
            navController.navigate(PasswordRecoveryFragmentDirections.recoveryToLogin());
        } else if (id.equals("Tracker")) {
            navController.navigate(TrackerFragmentDirections.trackerToLogin());
        }
    }

    @Override
    public void proceedToPasswordRecovery() {
        navController.navigate(LoginFragmentDirections.loginToRecovery());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(Values.CHANNEL_ID, Values.CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel for location tracker");
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(false);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void startService() {
        if (isLocationPermissionsGranted()) {
            getApplicationContext().startService(new Intent(this, TrackerService.class));
        } else {
            requestPermissions();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startNotification();
        }
    }

    @Override
    public void stopService() {
        getApplicationContext().stopService(new Intent(this, TrackerService.class));
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION});
        } else {
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startNotification() {
        Intent notificationIntent = new Intent(this, AppActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new Notification.Builder(this, Values.CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(R.drawable.ic_location)
            .setContentIntent(pendingIntent)
            .build();
        notificationManager.notify(1, notification);
    }


    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isLocationPermissionsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) || isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) && isPermissionGranted(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        } else {
            return (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) || isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION));
        }
    }
}