package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import server.request.AbstractRequest;
import server.request.IRequest;

public class RequestBroker {
	
	private static final int KEEP_ALIVE_TIME = 10;
	private static final int MAXIMUM_POOL_SIZE = 40;
	private static final int CORE_POOL_SIZE = 20;
	private ThreadPoolExecutor pool;
	private static RequestBroker instance = new RequestBroker();
	
	private RequestBroker() {
		
		BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>();
		pool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_TIME,TimeUnit.SECONDS,queue );
	}
	
	public static RequestBroker getInstance() {
		return instance;
	}
	
	public void processRequest(AbstractRequest request) {
		RequestExecutor worker = new RequestExecutor(request);
		pool.execute(worker);
	}
	

}
