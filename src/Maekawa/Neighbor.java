package Maekawa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Neighbor extends Node {
	private Socket OutgoingSocket;
	private Socket IncomingSocket;
	
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	public Neighbor(int nodeId) {
		super(nodeId);
	}

	public ObjectInputStream getInputObjectStream() {
		return objectInputStream;
	}

	public ObjectOutputStream getOutputObjectStream() {
		return objectOutputStream;
	}

	public void setOutgoingSocket(Socket outgoingSocket) throws IOException {
		OutgoingSocket = outgoingSocket;
		try {
			this.objectOutputStream = new ObjectOutputStream(OutgoingSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getIncomingSocket() {
		return IncomingSocket;
	}

	public void setIncomingSocket(Socket incomingSocket) {
		IncomingSocket = incomingSocket;
		try {
			this.objectInputStream = new ObjectInputStream(IncomingSocket.getInputStream());;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(ScalarClock clock) throws IOException {
		RequestMessage message = new RequestMessage(clock);
		objectOutputStream.writeObject(message);
		objectOutputStream.flush();
	}

	public void sendRelease(ScalarClock clock) throws IOException {
		ReleaseMessage message = new ReleaseMessage(clock);
		objectOutputStream.writeObject(message);
		objectOutputStream.flush();
	}
}