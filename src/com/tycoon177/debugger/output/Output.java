package com.tycoon177.debugger.output;

public class Output {
	private static StringBuilder normalOutput = new StringBuilder("");
	private static final String line = "\r\n";

	/**
	 * Returns the output generated by the program.
	 * @return
	 */
	public static String getOutput() {
		return normalOutput.toString();
	}

	/**
	 * Returns the output of the program in an HTML text area
	 * @return
	 */
	public static String getHTMLOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append("<textarea readonly rows=\"20\" cols=\"50\">");
		builder.append(getOutput());
		builder.append("</textarea>");
		return builder.toString();
	}

	/**
	 * Prints text with an end line 
	 * @param out
	 */
	public static void println(String out) {
		print(out);
		print(line);
	}

	/**
	 * Prints text
	 * @param out
	 */
	public static void print(String out) {
		normalOutput.append(out);
		System.out.print(out);
	}

}
