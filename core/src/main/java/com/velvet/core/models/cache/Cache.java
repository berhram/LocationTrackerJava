package com.velvet.core.models.cache;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Observable;

public interface Cache {
    void addItem(Result<String> inputItem);

    Observable<Result<String>> getItem();
}
