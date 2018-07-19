package Maekawa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import Maekawa.Config;
import Maekawa.Host;
import Maekawa.Neighbor;
import Maekawa.Node;

public class Service extends Thread{
	static int RETRY_COUNT=20;
	
	public Config config;
	
	protected ReentrantLock serviceLock;
	public ScalarClock localClock;
	public ArrayList<Message> requestMessageQueue;
	
	public Service(Config config) {
		super();
		this.serviceLock = new ReentrantLock();
		this.config = config;
		this.localClock = new ScalarClock();
		this.setup();
	}
	
	@Override
	public void run() {
		this.serviceLock.lock();
		this.config.getNode().getNeighbors().size();
	}
	
	private void setup() {
		ArrayList<NeighborConnectionSetupHandler> NCHList = new ArrayList<NeighborConnectionSetupHandler>(); 
		for (Neighbor neighbor : config.node.neighbors) {
			NeighborConnectionSetupHandler ncHandler = new NeighborConnectionSetupHandler(neighbor);
			NCHList.add(ncHandler);
			ncHandler.run();
		}
		
		Node node = config.getNode();
		Host node_host = config.hosts.get(node.getId());
		try {
			ServerSocket listener = new ServerSocket(node_host.getPort(),
					(int)config.getN(),
					InetAddress.getByName(node_host.getHostname()));
			
//			Accepting incoming connections
			for(int i = 0; i < config.node.neighbors.size(); i++) {
				Socket connector = listener.accept();
				ObjectInputStream is = new ObjectInputStream(connector.getInputStream());
				Integer neighbor_id = (Integer)is.readObject();
				
				Neighbor neighbor = config.getNode().getNeighborById(neighbor_id);
				neighbor.setIncomingSocket(connector);
				System.out.println(String.format("Received connection from neighbor%d", neighbor.getId()));
			}
			
			listener.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (NeighborConnectionSetupHandler nch : NCHList) {
			try {
				nch.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public void csEnter() {
		this.serviceLock.lock();
		try {
//			Send requests to my quorum
			for (Neighbor neighbor : this.config.getNode().getNeighbors()) {
				neighbor.sendRequest(this.localClock);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.serviceLock.unlock();
		}
//		This blocks until the service is done serving  
	}
	
	public void csLeave() {
//		Send release to my members
	}
	
	/**************************************************/

	private class NeighborConnectionSetupHandler extends Thread{
		private Neighbor neighbor;
		
		public NeighborConnectionSetupHandler(Neighbor neighbor) {
			this.neighbor = neighbor;
		}
		
		@Override
		public void run(){
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Host host = config.hosts.get(neighbor.getId());
			boolean successFlag = false;
			for(int i = 0; i < RETRY_COUNT; i++) {
				try {
					Socket connector = new Socket(InetAddress.getByName(host.getHostname()), host.getPort());
					neighbor.setOutgoingSocket(connector);
					successFlag = true;

//					Sending nodeID to identify self after making connection
					neighbor.getOut().writeObject(new Integer(config.getNode().getId()));
	
				} catch (IOException e) {
					System.out.println(String.format("Retrying for %dth time", i+1));
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!successFlag) {
				System.err.println(String.format("Connection to neighbor: %d failed", neighbor.getId()));
				System.exit(-1);
			}
			
		}
	}
	/**************************************************/
	private class NeighborMonitor extends Thread implements Runnable{
		Neighbor neighbor;
		
		public NeighborMonitor(Neighbor neighbor) {
			this.neighbor = neighbor;
		}
		
		public void run() {
			ObjectInputStream ois = neighbor.getInputObjectStream();
			try {
				Message message = (Message) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**************************************************/
}
