package com.velvet.core.cache;

import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public interface GlobalCache<T> {
    void addItem(T inputItem);

    PublishSubject<T> getItem();
}
