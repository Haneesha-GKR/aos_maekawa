package Maekawa;
import java.io.IOException;

import Maekawa.Service;

public class Main {
	public static void main(String[] args) {
		int nodeid = Integer.parseInt(args[0]);
		String filename = args[1];
		try {
//			Read configuration
			Config config = ConfigParser.genConfig(nodeid, filename);
			config.display();
			
//			Setup node - socket connections
			Service service = new Service(config);
			
//			Run application
//			Terminate - cleanup
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
