import java.net.Socket;
import java.util.ArrayList;

// Object that will have <Identifier> <Hostname> <Port> read from config file stored
public class Node {
	private int nodeId;
	private ArrayList<Neighbor> neighbors;
	
	public Node(int nodeId) {
		this.setNodeId(nodeId);
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	public Neighbor getNeighborById(int id) {
		for (Neighbor neighbor : neighbors) {
			if(neighbor.getNodeId() == id) {
				return(neighbor);
			}
		}
		
		Neighbor blank = new Neighbor(-1);
		return(blank);
	}
}

class Neighbor extends Node{
	private Socket in;
	private Socket out;
	
	public Neighbor(int nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
	}

	public Socket getIn() {
		return in;
	}

	public void setIn(Socket in) {
		this.in = in;
	}

	public Socket getOut() {
		return out;
	}

	public void setOut(Socket out) {
		this.out = out;
	}
}