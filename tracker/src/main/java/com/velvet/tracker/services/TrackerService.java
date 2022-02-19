package com.velvet.tracker.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.velvet.core.Values;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.models.location.emitter.LocationEmitterDatabase;
import com.velvet.core.models.location.emitter.LocationEmitterImpl;
import com.velvet.tracker.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerService extends Service {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private LocationEmitter emitter;
    private NotificationManager notificationManager;

    @Inject
    MessageCache messageCache;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        this.emitter = new LocationEmitterDatabase(new LocationEmitterImpl(this));
        Log.d("Service", "Service started");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(this, Values.CHANNEL_ID)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(R.drawable.ic_location)
                    .setOngoing(true)
                    .build();
            startForeground(1, notification);
        }
        disposables.add(
                Observable.interval(Values.LOCATION_READ_FREQUENTLY_SEC, TimeUnit.SECONDS)
                        .flatMap(t -> emitter.getLocations().toObservable())
                        .flatMap(Observable::fromIterable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(locations -> {
                            Log.d("Service", "Service receive update");
                            if (locations.isError()){
                                messageCache.addItem(locations.error.getMessage());
                            } else {
                                messageCache.addRawDate(new Date(locations.data.getTime()));
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }


}