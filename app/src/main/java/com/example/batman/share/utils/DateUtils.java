package com.example.batman.share.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private final Date today, tomorrow, weekStart, weekEnd, monthStart, monthEnd, yearStart, yearEnd;

    public DateUtils() {
        Calendar calendar = Calendar.getInstance();
        today = new Calendar.Builder().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).build().getTime();
        Calendar calendarCalc = new Calendar.Builder().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).build();
        calendarCalc.set(Calendar.DATE, calendarCalc.get(Calendar.DATE) + 1);
        tomorrow = calendarCalc.getTime();

        calendarCalc.add(Calendar.DATE, -1);
        calendarCalc.set(Calendar.DATE, calendarCalc.get(Calendar.DATE) + 1 - LocalDateTime.now().getDayOfWeek().getValue());
        weekStart = calendarCalc.getTime();
        calendarCalc.set(Calendar.DATE, calendarCalc.get(Calendar.DATE) + 6);
        weekEnd = calendarCalc.getTime();

        calendarCalc.set(Calendar.DATE, 1);
        monthStart = calendarCalc.getTime();
        calendarCalc.set(Calendar.DATE, YearMonth.now().lengthOfMonth());
        monthEnd = calendarCalc.getTime();

        calendarCalc.set(calendarCalc.get(Calendar.YEAR), 0, 1);
        yearStart = calendarCalc.getTime();
        calendarCalc.set(calendarCalc.get(Calendar.YEAR) + 1, 0, 0);
        yearEnd = calendarCalc.getTime();
    }

    public Date getToday() {
        return today;
    }

    public Date getTomorrow() {
        return tomorrow;
    }

    public Date getWeekStart() {
        return weekStart;
    }

    public Date getWeekEnd() {
        return weekEnd;
    }

    public Date getMonthStart() {
        return monthStart;
    }

    public Date getMonthEnd() {
        return monthEnd;
    }

    public Date getYearStart() {
        return yearStart;
    }

    public Date getYearEnd() {
        return yearEnd;
    }

}
