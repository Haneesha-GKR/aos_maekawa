import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SetupService {
	static int RETRY_COUNT=20;
	
	private Config config;
	
	class NeighborConnectionHandler extends Thread{
		private Neighbor neighbor;
		public NeighborConnectionHandler(Neighbor neighbor) {
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
	
	public SetupService(Config config) {
		this.config = config;
		ArrayList<NeighborConnectionHandler> NCHList = new ArrayList<NeighborConnectionHandler>(); 
		for (Neighbor neighbor : config.node.neighbors) {
			NeighborConnectionHandler ncHandler = new NeighborConnectionHandler(neighbor);
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
		
		for (NeighborConnectionHandler nch : NCHList) {
			try {
				nch.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

}
