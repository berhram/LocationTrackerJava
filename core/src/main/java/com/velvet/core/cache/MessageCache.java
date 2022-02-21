package com.velvet.core.cache;

import com.velvet.core.Values;
import com.velvet.core.result.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MessageCache implements GlobalCache<Result<String>>{
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    private final GlobalCache<Result<String>> globalCache;


    public MessageCache(GlobalCache<Result<String>> globalCache) {
        this.globalCache = globalCache;
    }


    @Override
    public void addItem(Result<String> item) {
        globalCache.addItem(item);
    }

    @Override
    public BehaviorSubject<Result<String>> getItem() {
        return globalCache.getItem();
    }

    public void addRawDate(Date date) {
        globalCache.addItem(Result.success(sDF.format(date)));
    }
}
