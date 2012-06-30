package server;

import org.apache.log4j.Logger;

import server.request.IRequest;

public class RequestExecutor implements Runnable {

	private IRequest request;
	private static Logger perfLogger = Logger.getLogger("PerformanceLogger");

	public RequestExecutor(IRequest request) {
		this.request = request;
	}
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		perfLogger.debug(request.getId() + " START    -> " + start );
		
		request.process();
		
		long end = System.currentTimeMillis();
		perfLogger.debug(request.getId() + " END      -> " + end );
		long responseTime = end - start;
		perfLogger.info(request.getId() +  " RESPONSE -> " + responseTime );
	}
	
}
