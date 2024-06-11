package com.example.batman.share.utils;

import android.util.Log;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

@Getter
public class DateUtils {

    private final Date today, tomorrow, weekStart, weekEnd, monthStart, monthEnd, yearStart, yearEnd;

    public DateUtils() {
        Calendar calendar = Calendar.getInstance();
        today = new Calendar.Builder().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).build().getTime();
        Log.w("date today", "" + today);
        Log.w("date today", "" + new Date(System.currentTimeMillis()));
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

    public static boolean todayEquals(Calendar c1) {
        Calendar today = Calendar.getInstance();
        if (c1.get(Calendar.YEAR) != today.get(Calendar.YEAR)) return false;
        if (c1.get(Calendar.MONTH) != today.get(Calendar.MONTH)) return false;
        if (c1.get(Calendar.DATE) != today.get(Calendar.DATE)) return false;
        return true;
    }

}
