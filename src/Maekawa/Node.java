package Maekawa;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class Node:	Represents the current node/site identified by nodeId
 * 				-
 * 
 * */
public class Node {
	private int id;
	protected ArrayList<Neighbor> neighbors;
	protected ArrayList<Neighbor> quorumNeighbors;
	
	public Node(int id) {
		super();
		
		this.setId(id);
		this.neighbors = new ArrayList<Neighbor>();
		this.quorumNeighbors = new ArrayList<Neighbor>();
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
		System.out.print("Quorum: ");
		for (Neighbor neighbor : quorumNeighbors) {
			System.out.print(String.format("%d ", neighbor.getId()));
		}
		System.out.println();
	}

	public ArrayList<Neighbor> getNeighbors() {
		return neighbors;
	}
	
	public void sendRequests(ScalarClock clock) throws IOException {
		for (Neighbor neighbor : neighbors) {
			neighbor.sendRequest(clock);
		}
	}
}
