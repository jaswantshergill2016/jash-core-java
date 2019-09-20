package io.reactivestax.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;

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
}
