package com.velvet.core.cache;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GlobalCache<T> {
    void addItems(List<T> inputItems);

    void addItem(T inputItem);

    List<T> getItems();

}
