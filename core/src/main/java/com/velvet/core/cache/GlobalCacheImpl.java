package com.velvet.core.cache;

import java.util.ArrayList;
import java.util.List;

public class GlobalCacheImpl<T> implements GlobalCache<T> {
    private final List<T> cachedItems = new ArrayList<>();

    @Override
    public void addItems(List<T> inputItems) {
        cachedItems.addAll(inputItems);
    }

    @Override
    public void addItem(T inputItem) {
        cachedItems.add(inputItem);
    }

    @Override
    public List<T> getItems() {
        List<T> tempList = cachedItems;
        cachedItems.clear();
        return tempList;
    }
}
