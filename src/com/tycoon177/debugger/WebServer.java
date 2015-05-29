package com.tycoon177.debugger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.tycoon177.debugger.http.Client;
import com.tycoon177.debugger.output.Output;
import com.tycoon177.debugger.pages.Functions;
import com.tycoon177.debugger.pages.Information;
import com.tycoon177.debugger.pages.Page;

public class WebServer implements Runnable {
	private ServerSocket socket;
	private int portNum;
	private int backlogNum;
	private boolean started = false;
	public static HashMap<String, Page> pages;
	private Thread thread;

	/**
	 * Creates a web server on port 80 with 50 positions in the queue
	 */
	public WebServer() {
		this(80);
	}

	/**
	 * Creates a web server on a specified port with 50 positions in the queue
	 * 
	 * @param port
	 */
	public WebServer(int port) {
		this(port, 50);
	}

	/**
	 * Creates a web server on a specified port with a specified number of positions in the queue
	 * 
	 * @param port
	 * @param backlog
	 */
	public WebServer(int port, int backlog) {
		setupServer(port, backlog);
		pages = new HashMap<String, Page>();
		pages.put(" ", new Information());
		pages.put("info", new Information());
		pages.put("functions", new Functions());
		Functions.addFunction("restartserver", () -> restartServer());
		Functions.addFunction("stopserver", () -> stopServer());
		Functions.addFunction("shutdown", () -> System.exit(0));
	}

	/**
	 * Creates the server socket
	 * 
	 * @param port
	 * @param backlog
	 */
	private void setupServer(int port, int backlog) {
		Output.println("Starting Server...");
		try {
			socket = new ServerSocket(port, backlog);
			started = true;
		} catch (IOException e) {
			Output.println("Server failed to start on port " + port);
			started = false;
		}
		thread = new Thread(this);
		thread.start();
		this.portNum = port;
		this.backlogNum = backlog;
		if (started)
			Output.println("Server Started Successfully on port " + port + "!");
	}

	/**
	 * Restarts the server
	 */
	public void restartServer() {
		stopServer();
		setupServer(portNum, backlogNum);
		Output.println("Finished Restarting Server!");
	}

	/**
	 * Stops the web server from running
	 */
	public void stopServer() {
		Output.println("Shutting Down Server...");
		if (started) {
			started = false;
			try {
				socket.close();
			} catch (IOException e) {
				Output.println("Unable to properly close the socket. It may still be running.");
			}
		}
		Output.println("Server Shutdown complete!");
	}

	/**
	 * Adds additional pages for debugging purposes
	 * 
	 * @param path
	 * @param page
	 */
	public void addPage(String path, Page page) {
		pages.put(path, page);
	}

	public void run() {
		while (started) {
			Socket s = null;
			try {
				s = socket.accept();
			} catch (IOException e) {
			}
			if (s == null)
				continue;
			new Thread(new Client(s), "Client Thread").start();
		}
	}
}
