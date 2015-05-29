package com.tycoon177.debugger.pages;

import com.tycoon177.debugger.output.Output;

public class Information implements Page {
	private static long startTime;

	static {
		startTime = System.currentTimeMillis();
	}

	/**
	 * Calculates the uptime of the program and returns the formatted uptime.
	 * @return
	 */
	public static String getUptime() {
		double seconds = (System.currentTimeMillis() - startTime) / 1000.0;
		double minutes = seconds / 60.0;
		double hours = minutes / 60.0;
		String timestamp = ((int) hours) + "h " + (int)(minutes) + "m " + (int)(seconds % 60) + "s";
		return timestamp;
	}

	@Override
	public String getHTMLOutput(String argument) {
		StringBuilder output = new StringBuilder("");
		output.append("<html><body><center>Uptime: ");
		output.append(getUptime());
		output.append("<br><br><b>Program Output:</b><br>");
		output.append(Output.getHTMLOutput());
		output.append("</center></body></html>");
		return output.toString();
	}
}
