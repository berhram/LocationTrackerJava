package com.velvet.core.filter;

import java.util.Date;

public class DateFilter {
    private final Date startDate;
    private final Date endDate;

    public DateFilter(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean check(long time) {
        return startDate.getTime() <= time && endDate.getTime() >= time;
    }
}
