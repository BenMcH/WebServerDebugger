package com.tycoon177.debugger.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.tycoon177.debugger.output.Output;

public class Client implements Runnable {
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private Request request;
	private Response response;

	public Client(Socket s) {
		socket = s;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			Output.println("Client not initialized correctly");
			output.close();
		}
	}

	@Override
	public void run() {
		if (input != null && output != null) {
			StringBuilder builder = new StringBuilder();
			String in;
			try {
builder.append(input.readLine());
			} catch (IOException e) {
			}
			request = new Request(builder.toString());
			response = new Response(request);
			output.println(response.getResponse());
			output.flush();
		}
		
		// Close Connections
		try {
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {

		}
	}

}
