package com.trams.joonggu_nubigo.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 19/11/2015.
 */
public class DateTimeUtils {

    private static final String DATE_FORMAT = "yyyy.MM.dd";

    public static String getDateFromLong(long dateLong) {
        Format formatter = new SimpleDateFormat(DATE_FORMAT);
        String result = formatter.format(new Date(dateLong));
        return result;
    }

}
