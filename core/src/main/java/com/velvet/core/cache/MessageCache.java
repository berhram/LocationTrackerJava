package com.velvet.core.cache;

import com.velvet.core.Values;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MessageCache extends GlobalCacheDecorator<String> {
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    public MessageCache(GlobalCache<String> globalCache) {
        super(globalCache);
    }

    @Override
    public void addItems(List<String> inputItems) {
        super.addItems(inputItems);
    }

    @Override
    public void addItem(String item) {
        super.addItem(item);
    }

    @Override
    public List<String> getItems() {
        return super.getItems();
    }

    public void addRawDate(Date date) {
        List<String> tempList = new ArrayList<>();
        tempList.add(sDF.format(date));
        addItems(tempList);
    }
}
