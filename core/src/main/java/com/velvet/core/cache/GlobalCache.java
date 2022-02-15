package com.velvet.core.cache;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GlobalCache<T> {
    void addItems(List<T> inputItems);

    Observable<List<T>> getItemsObservable();

    List<T> getItems();
}
