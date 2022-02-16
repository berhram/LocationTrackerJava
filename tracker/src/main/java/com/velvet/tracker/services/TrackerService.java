package com.velvet.tracker.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.velvet.core.Values;
import com.velvet.core.cache.GlobalCache;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.core.models.location.LocationEmitterImpl;
import com.velvet.core.result.Result;
import com.velvet.tracker.di.TrackerServiceComponent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class TrackerService extends Service {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final BehaviorSubject<Result<Void>> errorSubject = BehaviorSubject.create();
    private LocationEmitter emitter;

    @Inject
    FirebaseFirestore database;

    @Inject
    GlobalCache<Boolean> messageCache;

    public TrackerService() {
        TrackerServiceComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(getApplicationContext())).build().inject(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        disposables.add(
                Observable.interval(Values.LOCATION_READ_FREQUENTLY_SEC, TimeUnit.SECONDS)
                .flatMap(t -> emitter.getLocations().toObservable())
                .map(listResult -> listResult.data)
                .flatMap(Observable::fromIterable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::writeLocationToFirestore));
        emitter = new LocationEmitterImpl(getApplicationContext());
        setup();
    }

    public void setup() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(Values.MAX_CACHE_BYTES)
                .build();
        database.setFirestoreSettings(settings);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    private void writeLocationToFirestore(Location location) {
        database.collection("Tracker")
                .add(location)
                .addOnFailureListener(e -> errorSubject.onNext(Result.error(new Exception("Something went wrong in service"))));
    }
}