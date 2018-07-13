import java.net.Socket;
import java.util.ArrayList;

/**
 * Class Node:	Represents the current node/site identified by nodeId
 * 				-
 * 
 * */
public class Node {
	private int id;
	public ArrayList<Neighbor> neighbors;
	
	public Node(int id) {
		super();
		
		this.setId(id);
		this.neighbors = new ArrayList<Neighbor>();
	}
	
	public Neighbor getNeighborById(int id) {
		for (Neighbor neighbor : neighbors) {
			if(neighbor.getId() == id) {
				return(neighbor);
			}
		}
		
		Neighbor blank = new Neighbor(-1);
		return(blank);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void display() {
		System.out.print("Neighbors: ");
		for (Neighbor neighbor : neighbors) {
			System.out.print(String.format("%d ", neighbor.getId()));
		}
		System.out.println();
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