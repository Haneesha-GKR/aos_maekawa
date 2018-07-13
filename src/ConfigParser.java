import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigParser {

//	public static ProjectMain readConfigFile(String name) throws IOException{
//		//ProjectMain mySystem = new ProjectMain();
		public static int count = 0,flag = 0;
	public static	int MeanReqDelay,N,MeanExecDelay,NumReq;
	

	public static void main(String[] args) throws IOException{
		String filename=args[0];
		String line= null;
	
		
		 Config m=new Config();
		
		try {
			
			
			 //FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filename);
			System.out.println(filename);
			
			 
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				if(line.length() == 0)
					continue;
				// Ignore comments and consider only those lines which are not comments
				if(!line.startsWith("#")){
					if(line.contains("#")){
						String[] input = line.split("#.*$");
						System.out.println(input);
						String[] input1 = input[0].split("\\s+");
						if(flag == 0 && input1.length == 4){
							N = Integer.parseInt(input1[0]);
							MeanReqDelay = Integer.parseInt(input1[1]);
							MeanExecDelay = Integer.parseInt(input1[2]);
							NumReq = Integer.parseInt(input1[3]);
							flag++;
							
						}
						
						else if(flag == 1 && count < N)
						{					
							System.out.println(input1);
//							m.hosts.add(Integer.parseInt(input1[1]);
							count++;
							if(count ==N){
								flag=2;
								}
						}
			}
					}
					else {
						String[] input = line.split("\\s+");
						if(flag == 0 && input.length == 4){
						           N= Integer.parseInt(input[0]);
						           MeanReqDelay = Integer.parseInt(input[1]);
						           MeanExecDelay = Integer.parseInt(input[2]);
						           NumReq= Integer.parseInt(input[3]);
							
							flag++;
							//mySystem.adjMatrix = new int[mySystem.numOfNodes][mySystem.numOfNodes];
						}
//						else if(flag == 1 && count < N)
//						{
//							add(new Node(Integer.parseInt(input[0]),input[1],Integer.parseInt(input[2])));
//							count++;
//							if(count == N){
//								flag = 2;
//							}
//						}
//						else if(flag == 2){
//							insertIntoMatrix(input,mySystem,curNode);
//							curNode++;
						}
					}
				
			
			// Always close files.
			bufferedReader.close();  
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" +filename + "'");                
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + filename + "'");                  
		}
//		for(int i=0;i<mySystem.numOfNodes;i++){
//			for(int j=0;j<mySystem.numOfNodes;j++){
//				if(mySystem.adjMatrix[i][j] == 1){
//					mySystem.adjMatrix[j][i] = 1;
//				}
//			}
//		}
//		return mySystem;
//	}
	System.out.println(N);
		

		}

	}



