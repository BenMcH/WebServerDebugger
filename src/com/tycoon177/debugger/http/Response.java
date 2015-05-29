package com.tycoon177.debugger.http;

import com.tycoon177.debugger.WebServer;
import com.tycoon177.debugger.pages.Page;

public class Response {

	private String response;

	public Response(Request req) {
		String asset = req.getRequestedAsset().substring(1);
		String arg = req.getArgument();
		Page page = WebServer.pages.get(asset);
		if (page != null)
			response = page.getHTMLOutput(arg);
		int stat = 200;
		if (response == null) {
			response = "\r\n\r\n";
			stat = 404;
		}
		StringBuilder http = new StringBuilder();
		http.append("HTTP/1.1 " + stat);
		if (stat == 200) {
			http.append(" OK\r\nContent-type: text/html\r\n\r\n");
			http.append(response);
			http.append("\r\n\r\n");
		}
		if (stat == 404) {
			http.append("  Not Found\r\nContent-type: text/html\r\nContent-length:135\r\n\r\n<html><head><title>Not Found</title></head><body>Sorry, the object you requested was not found.</body><html>");
		}
		
		response = http.toString();
	}
	
	public String getResponse(){
		return response;
	}

}
