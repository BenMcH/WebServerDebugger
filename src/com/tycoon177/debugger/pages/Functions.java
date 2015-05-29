package com.tycoon177.debugger.pages;

import java.util.HashMap;
import java.util.Set;

import com.tycoon177.debugger.WebServer;

public class Functions implements Page {
	public static final HashMap<String, Runnable> functions = new HashMap<String, Runnable>();

	/**
	 * Adds additional functions that can be done from the web interface.
	 * 
	 * @param id
	 * @param code
	 */
	public static void addFunction(String id, Runnable code) {
		functions.put(id, code);
	}

	/**
	 * Returns the runnable portion of code based on a function name
	 * 
	 * @param id
	 * @return
	 */
	public static Runnable getFunction(String id) {
		return functions.get(id);
	}

	/**
	 * Returns the function names
	 * 
	 * @return
	 */
	public static Set<String> getFunctions() {
		return functions.keySet();
	}

	public String getHTMLOutput(String argument) {
		if (argument != null) {
			Runnable func = getFunction(argument);
			if (func != null)
				func.run();
		}
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body><center>");
		//TODO Buttons
		builder.append("</center></body></html>");
		return builder.toString();
	}
}
