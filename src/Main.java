import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		int nodeid = Integer.parseInt(args[0]);
		String filename = args[1];
		try {
			Config config = ConfigParser.genConfig(nodeid, filename);
			config.display();
			new SetupService(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
