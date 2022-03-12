package com.velvet.core.models.cache;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CacheImpl implements Cache {
    private final BehaviorSubject<Result<String>> cachedItem = BehaviorSubject.create();

    @Override
    public void addItem(Result<String> inputItem) {
        cachedItem.onNext(inputItem);
    }

    @Override
    public Observable<Result<String>> getItem() {
        return cachedItem;
    }
}
