package com.velvet.models.filter;

import java.util.Date;

public class DateFilter {
    private final Date startDate;
    private final Date endDate;

    public DateFilter(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }
}
