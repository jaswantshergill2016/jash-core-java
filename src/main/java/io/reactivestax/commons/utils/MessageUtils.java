package io.reactivestax.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.google.gson.Gson;

/**
 * this class has various general purpose utility methods that can be used by
 * app developers
 */
public class MessageUtils {


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
	
	/**
	 * General purpose utility method to print the contents of map in JSON format
	 * @param map
	 * @return String
	 */
	public static String printMapInJSONFormat(Map<String,String> map) {
		return new Gson().toJson(map);
	}
	
}
