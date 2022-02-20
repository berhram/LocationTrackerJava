package com.velvet.core.cache;

import android.util.Log;

import com.velvet.core.Values;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class MessageCache extends GlobalCacheDecorator<String> {
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    public MessageCache(GlobalCache<String> globalCache) {
        super(globalCache);
    }


    @Override
    public void addItem(String item) {
        super.addItem(item);
        Log.d("LOC", "MessageCache addItem invoked");
    }

    @Override
    public PublishSubject<String> getItem() {
        Log.d("LOC", "MessageCache getItems invoked");
        return super.getItem();
    }

    public void addRawDate(Date date) {
        super.addItem(sDF.format(date));
    }
}
