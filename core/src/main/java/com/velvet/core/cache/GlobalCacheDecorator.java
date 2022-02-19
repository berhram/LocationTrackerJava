package com.velvet.core.cache;

import java.util.List;

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
    public void addItem(T inputItem) {
        globalCache.addItem(inputItem);
    }

    @Override
    public List<T> getItems() {
        return globalCache.getItems();
    }
}
