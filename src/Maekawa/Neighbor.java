package Maekawa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Neighbor extends Node {
	private Socket OutgoingSocket;
	private Socket IncomingSocket;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Neighbor(int nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
	}

	public ObjectInputStream getInputObjectStream() {
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
			this.in = new ObjectInputStream(IncomingSocket.getInputStream());;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(ScalarClock clock) throws IOException {
//		TODO: Working here
//		this.out.writeObject(request);
	}
}