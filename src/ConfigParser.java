import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigParser {
	//	public static ProjectMain readConfigFile(String name) throws IOException{
	public static int count = 0,flag = 0;
	public static	int MeanReqDelay,N,MeanExecDelay,NumReq;

	public static void main(String[] args) throws IOException{
		Integer nodeid = Integer.parseInt(args[0]);
		String filename=args[1];
		String line= null;

		Config config=new Config(nodeid);

		try {
			//FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filename);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int line_number = 0;
			while((line = bufferedReader.readLine()) != null) {

				if(line.length() == 0)
					continue;

				if(line.startsWith("#")) {
					continue;
				}

				String segment;
				if(line.contains("#")){
					String[] segments = line.split("#");
					segment = segments[0];
				}else {
					segment = line;
				}
				
				if(segment.length() == 0) {
					continue;
				}else {
					line_number += 1;
					if(line_number == 1) { //Reading the first line
						String[] tokens = segment.split(" +");
						config.setN(Integer.parseInt(tokens[0]));
						config.setMeanReqDelay(Integer.parseInt(tokens[1]));
						config.setMeanExecDelay(Integer.parseInt(tokens[2]));
						config.setNumReq(Integer.parseInt(tokens[3]));
					}else if(line_number < config.getN()+1) {
//						Read into hosts
						String[] tokens = segment.split(" +");
						Host host = new Host(tokens[1], Integer.parseInt(tokens[2]));
						config.hosts.add(host);
					}else if(line_number == 1 + config.getN() + 2) {
//						Read my neighbors
						String[] tokens = segment.split(" +");
						for (String token : tokens) {
							Neighbor neighbor = new Neighbor(Integer.parseInt(token));
							config.node.neighbors.add(neighbor);
						}
					}
				}
			}
			config.display();
			bufferedReader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}  
	}
}




