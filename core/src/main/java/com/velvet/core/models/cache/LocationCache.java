package com.velvet.core.models.cache;

import com.velvet.core.Converters;
import com.velvet.core.result.Result;

import java.util.Date;

public class LocationCache extends AbstractGlobalCache<Result<String>> {

    public void addRawDate(Date date) {
        this.addItem(Result.success(Converters.dateToString(date)));
    }
}
