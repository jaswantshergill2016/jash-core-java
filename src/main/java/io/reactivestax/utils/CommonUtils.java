package io.reactivestax.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class CommonUtils {

    private static final Gson gson = new GsonBuilder().create();

    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


    public static Object unmarshallJson(String jsonPayload, Class  clz) {
        return gson.fromJson(jsonPayload,clz);

    }


    /**
     * returns a printStackTrace as String
     * @param exception
     * @return String
     */
    public static String printStackTrace(final Throwable exception) {
        final Writer writer = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(writer);
        exception.printStackTrace(printWriter);
        return writer.toString();
    }
}
