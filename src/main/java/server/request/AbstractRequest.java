package server.request;

public class AbstractRequest implements IRequest {

	
	private String id;

	public AbstractRequest(String id){
		this.id = id;
	}
	
	@Override
	public void process() {
		throw new RuntimeException("process method not implemented");
	}

	@Override
	public String getId() {
		return id;
	}

}
