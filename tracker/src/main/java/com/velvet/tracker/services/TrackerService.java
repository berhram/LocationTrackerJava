package com.velvet.tracker.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.velvet.core.Values;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.tracker.R;
import com.velvet.tracker.di.DaggerTrackerComponent;
import com.velvet.tracker.di.TrackerModule;
import com.velvet.tracker.services.controller.TrackerController;

import javax.inject.Inject;

public class TrackerService extends Service {

    @Inject
    TrackerController controller;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        DaggerTrackerComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(getApplicationContext())).trackerModule(new TrackerModule(getApplicationContext())).build().inject(this);
        controller.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final Notification notification = new Notification.Builder(this, Values.CHANNEL_ID)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(R.drawable.ic_location)
                    .setOngoing(true)
                    .build();
            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        controller.stop();
    }
}
