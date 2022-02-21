package com.velvet.core.cache;

import com.velvet.core.Values;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MessageCache implements GlobalCache<String>{
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    private final GlobalCache<String> globalCache;


    public MessageCache(GlobalCache<String> globalCache) {
        this.globalCache = globalCache;
    }


    @Override
    public void addItem(String item) {
        globalCache.addItem(item);
    }

    @Override
    public BehaviorSubject<String> getItem() {
        return globalCache.getItem();
    }

    public void addRawDate(Date date) {
        globalCache.addItem(sDF.format(date));
    }
}
