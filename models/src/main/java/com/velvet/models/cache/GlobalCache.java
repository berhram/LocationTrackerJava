package com.velvet.models.cache;

import android.location.Location;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GlobalCache<T> {
    void addItems(List<T> inputItems);

    Observable<List<T>> getItemsObservable();

    List<T> getItems();
}
