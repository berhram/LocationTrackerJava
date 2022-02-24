package com.velvet.core.models.cache;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class GlobalCacheImpl<T> implements GlobalCache<T> {
    private final BehaviorSubject<T> cachedItem = BehaviorSubject.create();

    @Override
    public void addItem(T inputItem) {
        cachedItem.onNext(inputItem);
    }

    @Override
    public BehaviorSubject<T> getItem() {
        return cachedItem;
    }
}
