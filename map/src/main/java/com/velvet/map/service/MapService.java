package com.velvet.map.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.velvet.models.filter.DateFilter;
import com.velvet.models.result.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MapService extends Service {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final BehaviorSubject<Result<Void>> errors = BehaviorSubject.create();
    private final PublishSubject<MarkerOptions> markers = PublishSubject.create();
    private FirebaseFirestore database;
    private final long MAX_CACHE_BYTES = 1024 * 1024 * 10;
    private DateFilter filter;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
        disposables.add(markers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
        setup();
    }

    private void setup() {
        database = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(MAX_CACHE_BYTES)
                .build();
        database.setFirestoreSettings(settings);
        if (filter == null) {
            filter = new DateFilter(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
        }
    }

    public void setFilter(DateFilter filter) {
        this.filter = filter;
    }

    private MarkerOptions locationToMarker(Location location) {
        return new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title(dateFormat.format(new Date(location.getTime())));
    }

    private List<Location> applyFilter(List<Location> locations) {
        for (Location location:
                locations) {
            Date locationDate = new Date(location.getTime());
            if (!dateFormat.format(locationDate).equals(dateFormat.format(filter.getStartDate())) && !dateFormat.format(locationDate).equals(dateFormat.format(filter.getEndDate())) && (!filter.getStartDate().before(locationDate) || !filter.getEndDate().after(locationDate))) {
                locations.remove(location);
            }
        }
        return locations;
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    private void receiveAllLocationFromFirestore() {
        database.collection("Tracker")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            markers.onNext(document.toObject(MarkerOptions.class));
                        }
                    }
                });
    }
}
