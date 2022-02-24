package com.velvet.core.models.cache;

import com.velvet.core.Values;
import com.velvet.core.result.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationCache extends GlobalCacheImpl<Result<String>>{
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    public void addRawDate(Date date) {
        this.addItem(Result.success(sDF.format(date)));
    }
}
