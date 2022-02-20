package com.velvet.core.cache;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class GlobalCacheImpl<T> implements GlobalCache<T> {
    private PublishSubject<T> cachedItem = PublishSubject.create();

    @Override
    public void addItem(T inputItem) {
        cachedItem.onNext(inputItem);
    }

    @Override
    public PublishSubject<T> getItem() {
        return cachedItem;
    }
}
