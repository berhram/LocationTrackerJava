package com.velvet.core.filter;

import android.util.Log;

import com.velvet.core.Converters;

import java.util.Date;

public class DateFilter {
    private Date startDate;
    private Date endDate;

    public static DateFilter createFullDateFilter(Date startDate, Date endDate) {
        DateFilter output = new DateFilter();
        output.startDate = startDate;
        output.endDate = endDate;
        return output;
    }

    public static DateFilter createStartDateFilter(Date startDate) {
        DateFilter output = new DateFilter();
        output.startDate = startDate;
        return output;
    }

    public static DateFilter createEndDateFilter(Date endDate) {
        DateFilter output = new DateFilter();
        output.endDate = endDate;
        return output;
    }

    public boolean check(long time) {
        Log.d("LOC", Converters.dateToString(new Date(time)) + " more than " + Converters.dateToString(new Date(startDate.getTime())) + " start: " + (startDate.getTime() <= time));
        Log.d("LOC", Converters.dateToString(new Date(time)) + " less than " + Converters.dateToString(new Date(endDate.getTime())) + " end: " + (endDate.getTime() >= time));
        return (startDate.getTime() <= time) && (endDate.getTime() >= time);
    }

    public void updateFilter(DateFilter newFilter) {
        if (newFilter.startDate != null) {
            startDate = newFilter.startDate;
        }
        if (newFilter.endDate != null) {
            endDate = newFilter.endDate;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
