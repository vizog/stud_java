package server.request;

import org.apache.log4j.Logger;

import server.RequestBroker;

public abstract class AbstractRequest implements IRequest {

	
	private String id;
	private long start;
	private static Logger perfLogger = Logger.getLogger("PerformanceLogger");


	public AbstractRequest(String id){
		this.id = id;
	}
	
	public void start() {
		start = System.currentTimeMillis();
		RequestBroker.getInstance().processRequest(this);
	}
	
	public void finish() {
		perfLogger.debug(getId() + " START    -> " + start );
		long end = System.currentTimeMillis();
		perfLogger.debug(getId() + " END      -> " + end );
		long responseTime = end - start;
		System.out.println( responseTime);
//		System.out.println( responseTime+ "," + end);
		
//		perfLogger.info(";" + getId() +  ";" + responseTime );
	}

	
	@Override
	public abstract void process();

	@Override
	public String getId() {
		return id;
	}

}
