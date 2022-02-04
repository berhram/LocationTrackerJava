package com.velvet.core.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.velvet.models.location.LocationEmitter;
import com.velvet.models.result.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class LocationService extends Service {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final BehaviorSubject<Result<Void>> errorSubject = BehaviorSubject.create();
    private FirebaseFirestore database;
    private final long MAX_CACHE_BYTES = 1024 * 1024 * 10;

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
        disposables.add(new LocationEmitter(getApplicationContext())
                .getLocationSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::writeLocationToFirestore));
        setup();
    }

    public void setup() {
        database = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(MAX_CACHE_BYTES)
                .build();
        database.setFirestoreSettings(settings);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    private void writeLocationToFirestore(Location location) {
        Map<Timestamp, Location> locationHashMap = new HashMap<>();
        locationHashMap.put(new Timestamp(new Date()), location);
        database.collection("Tracker")
                .document("Locations")
                .set(locationHashMap)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorSubject.onNext(new Result<>(null, new Exception("Firebase write error")));
                    }
                });
    }
}