package com.velvet.tracker.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.velvet.core.Values;
import com.velvet.tracker.R;
import com.velvet.tracker.TrackerBroadcastReceiver;
import com.velvet.tracker.services.controller.TrackerController;
import com.velvet.tracker.services.controller.TrackerControllerFactory;

public class TrackerService extends Service {
    private TrackerController controller;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        controller = new TrackerControllerFactory(getApplicationContext()).create();
        controller.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent stopIntent = new Intent(this, TrackerBroadcastReceiver.class);
            PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 1, stopIntent, PendingIntent.FLAG_IMMUTABLE);
            final Notification notification = new Notification.Builder(this, Values.CHANNEL_ID)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(R.drawable.ic_location)
                    .setOngoing(true)
                    .addAction(R.drawable.ic_location, getString(R.string.stop_tracking), stopPendingIntent)
                    .build();
            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        controller.stop();
        Log.d("Service", " service stopped");
    }
}
