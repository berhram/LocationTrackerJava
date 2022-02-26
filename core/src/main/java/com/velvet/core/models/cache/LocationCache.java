package com.velvet.core.models.cache;

import com.velvet.core.Values;
import com.velvet.core.models.database.local.Converters;
import com.velvet.core.result.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationCache extends AbstractGlobalCache<Result<String>> {

    public void addRawDate(Date date) {
        this.addItem(Result.success(Converters.dateToString(date)));
    }
}
