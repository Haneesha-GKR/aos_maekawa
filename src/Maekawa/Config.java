package Maekawa;
import java.util.ArrayList;
import java.util.HashSet;


public class Config {
	private long N;
	private long MeanReqDelay;
	private long MeanExecDelay;
	private long NumReq;
	
	public ArrayList<Host> hosts;
	public ArrayList<HashSet<Integer>> quorums;
	
	public Node node;

	public ArrayList<Host> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}

	public long getN() {
		return N;
	}

	public void setN(long n) {
		N = n;
	}

	public long getMeanReqDelay() {
		return MeanReqDelay;
	}

	public void setMeanReqDelay(long meanReqDelay) {
		MeanReqDelay = meanReqDelay;
	}

	public long getMeanExecDelay() {
		return MeanExecDelay;
	}

	public void setMeanExecDelay(long meanExecDelay) {
		MeanExecDelay = meanExecDelay;
	}

	public long getNumReq() {
		return NumReq;
	}

	public void setNumReq(long numReq) {
		NumReq = numReq;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
	public Config(Integer nodeid) {
		super();
		this.hosts = new ArrayList<Host>();
		this.node = new Node(nodeid);
		this.quorums = new ArrayList<HashSet<Integer>>();
	}
	
	public void display() {
		System.out.println(String.format("==================== NODE %02d ====================", node.getId()));
		System.out.println(String.format("|N\t\t\t\t: %5d\t\t|", getN()));
		System.out.println(String.format("|MeanReqDelay\t\t\t: %5d\t\t|", getMeanReqDelay()));
		System.out.println(String.format("|MeanExecDelay\t\t\t: %5d\t\t|", getMeanExecDelay()));
		System.out.println(String.format("|NumReq\t\t\t\t: %5d\t\t|", getNumReq()));
		System.out.println("-------------------------------------------------");
		
		int i = 0;
		for (Host host : hosts) {
			System.out.println(String.format("%02d> %8s | %6d |", i++, host.getHostname(), host.getPort()) );
		}
		
		System.out.println("---------------------QUORUMS---------------------");
		for (HashSet<Integer> hi : quorums) {
			for (Integer integer : hi) {
				System.out.print(String.format("%d ", integer));
			}
			System.out.println();
		}
		
		System.out.println("-------------------------------------------------");
		node.display();
		System.out.println("-------------------------------------------------");
	}
	
	public void genMemberships() {
		HashSet<Integer> neighbors_set = new HashSet<Integer>();
		int iter = 0;
		for (HashSet<Integer> quorum : quorums) {
			if(iter!=getNode().getId() && quorum.contains(node.getId())){
				neighbors_set.add(iter);
			}
			iter++;
		}
		
		for (Integer integer : neighbors_set) {
			Neighbor neighbor = new Neighbor(integer);
			node.neighbors.add(neighbor);
		}
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
	
	public String toString() {
		return String.format("%s %d", hostname, port);
	}
}