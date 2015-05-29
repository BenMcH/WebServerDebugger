package com.tycoon177.debugger.http;


public class Request {
	private String asset;
	private String arg;

	public Request(String req) {
		String split = "\\?";
		req = req.split(" ")[1];
		if (req.split(split).length == 1) {
			asset = req;
		}
		else {
			asset = req.split(split)[0];
			arg = req.split(split)[1];
		}
	}

	public String getRequestedAsset() {
		return asset;
	}

	public String getArgument() {
		return arg;
	}

}
