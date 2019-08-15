package io.reactivestax.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private SimpleDateFormat sdf;

    private static DateUtils du = new DateUtils();

    private DateUtils() {
        this.sdf = new SimpleDateFormat("dd-MMM-yyyy");
    }

    public Date formatDate(String dateStr) throws ParseException  {
        return dateStr == null ? null : sdf.parse(dateStr);
    }

    public static DateUtils getInstance() {
        return du;
    }

}
