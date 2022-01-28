package com.velvet.trackerforsleepwalkers.location;


import static android.telecom.TelecomManager.EXTRA_LOCATION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.velvet.trackerforsleepwalkers.AppActivity;
import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.models.Values;

public class LocationService extends Service {
    ServiceBinder binder = new ServiceBinder();

    public final String TAG = "LocationTracker";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String CHANNEL_ID = "my_channel_id";

    public static final CharSequence CHANNEL_NAME = "MY Channel";

    private LocationRequest locationRequest;
    
    private Location location;

    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action = intent.getAction();
            if(action != null) {
                if (ACTION_START_FOREGROUND_SERVICE.equals(action)) {
                    startForegroundService();
                } else if (ACTION_STOP_FOREGROUND_SERVICE.equals(action)) {
                    stopForegroundService();
                }
            }
        }
        return START_STICKY;
    }
    
    private void startForegroundService(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };
        createLocationRequest();
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {

        }
        getLastLocation();
        createNotificationChannel();
        Intent showTaskIntent = new Intent(getApplicationContext(), AppActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder  builder = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Location Service is running")
                .setSmallIcon(R.drawable.ic_map)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        startForeground(1, builder.build());
        LocationUtils.setRequestingLocationUpdates(getApplicationContext(), true);
    }

    private void stopForegroundService(){
        LocationUtils.setRequestingLocationUpdates(getApplicationContext(), false);
        fusedLocationClient.removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
    }

    private void createNotificationChannel(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                notificationChannel.setShowBadge(false);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(Values.LOCATION_UPDATES);
        locationRequest.setFastestInterval(Values.FASTEST_LOCATION_UPDATES);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getLastLocation() {
        try {
            fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    location = task.getResult();
                }});
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void onNewLocation(Location location) {
        location = location;
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(EXTRA_LOCATION, location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    class ServiceBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }
}
