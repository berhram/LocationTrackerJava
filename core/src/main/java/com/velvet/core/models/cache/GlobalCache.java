package com.velvet.core.models.cache;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public interface GlobalCache<T> {
    void addItem(T inputItem);

    BehaviorSubject<T> getItem();
}
