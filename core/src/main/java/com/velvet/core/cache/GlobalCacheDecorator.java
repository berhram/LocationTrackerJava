package com.velvet.core.cache;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class GlobalCacheDecorator<T> implements GlobalCache<T> {
    private final GlobalCache<T> globalCache;

    public GlobalCacheDecorator(GlobalCache<T> globalCache) {
        this.globalCache = globalCache;
    }

    @Override
    public void addItem(T inputItem) {
        globalCache.addItem(inputItem);
    }

    @Override
    public PublishSubject<T> getItem() {
        return globalCache.getItem();
    }
}
