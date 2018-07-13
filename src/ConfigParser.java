import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ConfigParser {
	public static Config genConfig(int nodeid, String filename) throws IOException{
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
					System.out.println(line);
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
					}else if(line_number < config.getN() + 2) {
						String[] tokens = segment.split(" +");
						Host host = new Host(tokens[1], Integer.parseInt(tokens[2]));
						config.hosts.add(host);
					}else if(line_number < 2*config.getN() + 2) {
						HashSet<Integer> quorum = new HashSet<Integer>();
						String[] tokens = segment.split(" +");
						for (String token : tokens) {
							int tok_num = Integer.parseInt(token);
							quorum.add(tok_num);
						}
						
						config.quorums.add(quorum);
						
					}
				}
			}
			config.genMemberships();
//			config.display();
			bufferedReader.close();
			
			return config;
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
//		This is a failure case. This should never happen
		Config c = new Config(-1);
		return c;  
	}
}




