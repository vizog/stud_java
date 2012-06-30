package server;

import server.request.GPARequest;
import server.request.IRequest;


public class LoadRunner {
	
	public static void main(String[] args) {
		for(int i = 0; i < 40; i++) {
			IRequest request = new GPARequest("req-" + (i+1));
			RequestBroker.getInstance().processRequest(request);
		}
	}
}
