package com.velvet.models.cache;

import com.velvet.models.result.Result;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public interface Cache<T> {
    void put(T data);
    ReplaySubject<T> get();
}
