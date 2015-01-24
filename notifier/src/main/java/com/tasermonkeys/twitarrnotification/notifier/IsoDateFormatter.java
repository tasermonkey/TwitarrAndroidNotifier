package com.tasermonkeys.twitarrnotification.notifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsoDateFormatter {
    private static ThreadLocal<SimpleDateFormat> DF = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }
    };

    public static Date parseDate(String dateStr) {
        try {
            return DF.get().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
