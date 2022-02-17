package com.velvet.core.cache;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GlobalCacheDecorator<T> implements GlobalCache<T> {
    private final GlobalCache<T> globalCache;

    public GlobalCacheDecorator(GlobalCache<T> globalCache) {
        this.globalCache = globalCache;
    }

    @Override
    public void addItems(List<T> inputItems) {
        globalCache.addItems(inputItems);
    }

    @Override
    public Observable<List<T>> getItemsObservable() {
        return globalCache.getItemsObservable();
    }

    @Override
    public List<T> getItems() {
        return globalCache.getItems();
    }
}
