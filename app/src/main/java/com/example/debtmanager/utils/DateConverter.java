package com.example.debtmanager.utils;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    @TypeConverter
    public static Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String millisToString(long value) {
        String pattern = "MMM d, yyyy";
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(value));
    }

    public static String dateToString(int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return DateConverter.millisToString(calendar.getTimeInMillis());
    }

    public static String currentDateToString() {

        Calendar calendar = Calendar.getInstance();

        String pattern = "MMM d, yyyy";
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(calendar.getTimeInMillis()));
    }

    public static Long CalendarTolong(Calendar c) {
        if (c != null)
            return c.getTimeInMillis();
        return null;

    }
}
