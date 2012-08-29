package server.request;

public interface IRequest {

	public void process();
	public String getId();
	public void finish();
	public void start();
}
