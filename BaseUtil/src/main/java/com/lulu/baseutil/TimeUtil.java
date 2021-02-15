package com.lulu.baseutil;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    /**
     * 判断是否为同一天
     */
    public static boolean isSameDay(@NonNull Date date1, @NonNull Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        if ((year1 == year2) && (day1 == day2)) {
            return true;
        }
        return false;
    }

    /**
     * 两个时间是否处于同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);
        return isSameDay(date1, date2);
    }
}
