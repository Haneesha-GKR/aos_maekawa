import java.util.ArrayList;


public class Config {
	private long N;
	private long MeanReqDelay;
	private long MeanExecDelay;
	private long NumReq;
	
	public static  ArrayList<Host> hosts;
//	public static boolean addHost( Host newhost ) {
//	      hosts.add( newhost );
//	      return true;
//	   }
	
	private Node node;

	public ArrayList<Host> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}
}

class Host{
	private String hostname;
	private int port;
	
	public Host(String host, int port) {
		super();
		this.setHostname(host);
		this.setPort(port);
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}