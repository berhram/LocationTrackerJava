package com.velvet.tracker.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.velvet.core.Values;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.models.location.emitter.LocationEmitterDatabase;
import com.velvet.core.models.location.emitter.LocationEmitterImpl;
import com.velvet.tracker.R;
import com.velvet.tracker.di.DaggerTrackerComponent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TrackerService extends Service {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private LocationEmitter emitter;

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
        emitter.start();
        DaggerTrackerComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(getApplicationContext())).build().inject(this);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, Values.CHANNEL_ID)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(R.drawable.ic_location)
                    .setOngoing(true)
                    .build();
            startForeground(1, notification);
        }
        disposables.add(
                Observable.interval(Values.LOCATION_CHECK_FREQUENTLY_SEC, TimeUnit.SECONDS)
                        .flatMap(t -> {
                            Log.d("LOC", "Service made response");
                            return Observable.just(emitter.getLocation());
                        })
                        //TODO why it is always null
                        .filter(locationResult -> !locationResult.isEmpty())
                        .subscribe(location -> {
                            Log.d("LOC", "Service receive update");
                            Log.d("LOC", "Mem. cell of cache (Service): " + messageCache);
                            if (location.isError()) {
                                messageCache.addItem(location.error.getMessage());
                            } else {
                                messageCache.addRawDate(new Date(location.data.getTime()));
                            }
                        }, Throwable::printStackTrace)
        );
    }


    @Override
    public void onDestroy() {
        disposables.clear();
        emitter.stop();
    }


}