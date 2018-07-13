import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private Socket OutgoingSocket;
	private Socket IncomingSocket;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Neighbor(int nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream objectInputStream) {
		this.in = objectInputStream;
//		ObjectInputStream is = new ObjectInputStream(connector.getInputStream());
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public Socket getOutgoingSocket() {
		return OutgoingSocket;
	}

	public void setOutgoingSocket(Socket outgoingSocket) throws IOException {
		OutgoingSocket = outgoingSocket;
		this.out = new ObjectOutputStream(outgoingSocket.getOutputStream());
	}

	public Socket getIncomingSocket() {
		return IncomingSocket;
	}

	public void setIncomingSocket(Socket incomingSocket) {
		IncomingSocket = incomingSocket;
		try {
			this.in = new ObjectInputStream(IncomingSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}