package server;

import org.apache.log4j.Logger;

import server.request.IRequest;

public class RequestExecutor implements Runnable {

	private IRequest request;

	public RequestExecutor(IRequest request) {
		this.request = request;
	}
	
	@Override
	public void run() {
		request.process();
		request.finish();
	}
	
}
