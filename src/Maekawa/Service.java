package Maekawa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import Maekawa.Config;
import Maekawa.Host;
import Maekawa.Neighbor;
import Maekawa.Node;

public class Service extends Thread{
	static int RETRY_COUNT=20;
	
	public Config config;
	public ScalarClock localClock;
	
	private ReentrantLock serviceLock;
	private ReentrantLock applicationLock;	// Is application in critical section or not or not.
	
	
	private ArrayList<NeighborMonitor> neighborMonitors;
	public ArrayList<Message> requestMessageQueue;
	
	
	public Service(Config config) {
		super();
		
		this.serviceLock = new ReentrantLock(true);
		this.applicationLock = new ReentrantLock(true);
		this.applicationLock.lock(); //Service unlocks application lock only if service layer is done
		
		this.config = config;
		this.localClock = new ScalarClock();
		
		this.neighborMonitors = new ArrayList<NeighborMonitor>();
		this.setup();
		run();
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
			e.printStackTrace();
			System.exit(-1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		for (NeighborConnectionSetupHandler nch : NCHList) {
			try {
				nch.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		for (Neighbor neighbor : config.getNode().getNeighbors()) {
			NeighborMonitor nm = new NeighborMonitor(neighbor);
			nm.run();
			this.neighborMonitors.add(nm);
		}
	}

	public void csEnter() {
		if(!this.applicationLock.isLocked()) {	// If the application is already running a critical section 
												//- wait until it releases it
												//- request the quorum
												//- release lock after quorum agrees
			
			this.applicationLock.lock(); 		// Wait for current CS to finish and acquire lock for current session 
			lockQuorum();
			return;

		}else { // Else acquire a lock for application - contact quorum
			this.applicationLock.lock();
			lockQuorum();
			return;
		}
	}
	
	private void lockQuorum() {
		// TODO Auto-generated method stub
		
	}

	public void csLeave() {
		unLockQuorum();
		this.applicationLock.unlock();
		return;
	}
	
	private void unLockQuorum() {
		// TODO Auto-generated method stub
		
	}

	private void handleRequestMessage(RequestMessage message) {
		
	}
	
	private void handleLockedMessage(LockedMessage message) {
		
	}
	
	private void handleFailedMessage(FailedMessage message) {
		
	}
	
	private void handleInquireMessage(InquireMessage message) {
		
	}
	
	private void handleRelinquishMessage(RelinquishMessage message) {
		
	}
	
	private void handleApplicationMessage(ApplicationMessage message) {
		
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
					neighbor.getOutputObjectStream().writeObject(new Integer(config.getNode().getId()));
	
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
				Class<? extends Message> klass = message.getClass();
				if(klass == RequestMessage.class) {
					
				}else if(klass == RequestMessage.class) {
					RequestMessage requestMessage = (RequestMessage)message;
					serviceLock.lock();
					System.out.println(
							String.format(
									"NeighborMonitor for Neighbor %d received a Request message",
									neighbor.getId()
									)
							);
					handleRequestMessage(requestMessage);
					serviceLock.unlock();
				}else if(klass == LockedMessage.class) {
					LockedMessage lockedMessage = (LockedMessage)message;
					serviceLock.lock();
					System.out.println(
							String.format(
									"NeighborMonitor for Neighbor %d received a Request message",
									neighbor.getId()
									)
							);
					handleLockedMessage(lockedMessage);
					serviceLock.unlock();
				}else if(klass == FailedMessage.class) {
					FailedMessage failedMessage = (FailedMessage)message;
					serviceLock.lock();
					System.out.println(
							String.format(
									"NeighborMonitor for Neighbor %d received a Request message",
									neighbor.getId()
									)
							);
					handleFailedMessage(failedMessage);
					serviceLock.unlock();
				}else if(klass == RelinquishMessage.class) {
					RelinquishMessage relinquishMessage = (RelinquishMessage)message;
					serviceLock.lock();
					System.out.println(
							String.format(
									"NeighborMonitor for Neighbor %d received a Request message",
									neighbor.getId()
									)
							);
					handleRelinquishMessage(relinquishMessage);
					serviceLock.unlock();
				}else if(klass == InquireMessage.class) {
					InquireMessage inquireMessage = (InquireMessage)message;
					serviceLock.lock();
					System.out.println(
							String.format(
									"NeighborMonitor for Neighbor %d received a Request message",
									neighbor.getId()
									)
							);
					handleInquireMessage(inquireMessage);
					serviceLock.unlock();
				}else if(klass == ApplicationMessage.class) {
					ApplicationMessage applicationMessage = (ApplicationMessage)message;
					serviceLock.lock();
					System.out.println(
							String.format(
									"NeighborMonitor for Neighbor %d received a Request message",
									neighbor.getId()
									)
							);
					handleApplicationMessage(applicationMessage);
					serviceLock.unlock();
				}else {
					throw new ClassNotFoundException(
							String.format(
									"NeighborMonitor for Neighbor %d received an undefined message",
									neighbor.getId()
									)
							);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			} finally {
				serviceLock.unlock();
				System.exit(-1);
			}
		}
		
	}
	/**************************************************/
}
