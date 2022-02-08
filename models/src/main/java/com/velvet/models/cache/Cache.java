package com.velvet.models.cache;

import io.reactivex.rxjava3.subjects.Subject;

public interface Cache<T> {
    void put(T data);
    Subject<T> get();
}
