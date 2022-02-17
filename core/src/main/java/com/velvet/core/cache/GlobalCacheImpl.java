package com.velvet.core.cache;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class GlobalCacheImpl<T> implements GlobalCache<T> {
    private final BehaviorSubject<List<T>> cachedItems = BehaviorSubject.createDefault(new ArrayList<>());

    @Override
    public void addItems(List<T> inputItems) {
        final List<T> oldItems = cachedItems.getValue();
        final List<T> newItems = new ArrayList<>(inputItems.size() + oldItems.size());
        newItems.addAll(oldItems);
        newItems.addAll(inputItems);
        cachedItems.onNext(newItems);
    }
    @Override
    public Observable<List<T>> getItemsObservable() {
        return cachedItems;
    }

    @Override
    public List<T> getItems() {
        return cachedItems.getValue();
    }
}
