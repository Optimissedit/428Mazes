import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		
		// Takes command-line arguments as value for N (Max size of given dimensions)
		int mazeSize = Integer.parseInt(args[0]);
		
		// Determine how many cells the array holds
	    int MAXSET = (int) Math.pow(mazeSize, 4);
	    //System.out.println(MAXSET);

		// Creates a disjoint set data structure to allow easy merging of cells by ID
		DisjointSet mazeSet = new DisjointSet(MAXSET);

		

		Stack<Integer> indices = new Stack<>();
		for(int i = 0; i < MAXSET; i++) {
			indices.push(i);
		}
		
		Collections.shuffle(indices);
		
		int runs = 0;
		boolean flag = false;
		while(!flag) {
			//System.out.println(runs);
			if(runs > 75000 && runs % 5000 == 0) {
				//System.out.println("Checking for end");
				// Checking for end condition
				int set = mazeSet.findParent(0);
				boolean check = true;
				
				for(int i = 0; i < MAXSET; i++) {
					if(mazeSet.findParent(i) != set) {
						// Not all one set, continue breaking walls
						check = false;
						break;
					}
				}
				if(check) {
					flag = true;
				}
				// end checking for end condition
			}

			
			// Find a random cell
			int index = indices.pop();
			
			int[] currCoords = getCoords(index, mazeSize);
			
			// Grab all its neighbors
			int[] neighbors = findNeighbors(index, mazeSize);

			
			// Select a valid neighbor to merge current index with	
			int neighborIndex = -1;
			Random rand = new Random();
			boolean valid = false;
			
			//TODO: Add method to get coordinates before neighbor checking?
			// Allows us to check if +- on an axis will put us outside
			while(!valid) {
				
				int cell = rand.nextInt(8);
				boolean ok = false;
				
				switch(cell) {
				case 0:
					if(currCoords[0] > 0) {
						ok = true;
					}
					break;
				case 1:
					if(currCoords[0] < mazeSize - 1) {
						ok = true;
					}
					break;
				case 2:
					if(currCoords[1] > 0) {
						ok = true;
					}
					break;
				case 3:
					if(currCoords[1] < mazeSize - 1) {
						ok = true;
					}
					break;
				case 4:
					if(currCoords[2] > 0) {
						ok = true;
					}
					break;
				case 5:
					if(currCoords[2] < mazeSize - 1) {
						ok = true;
					}
					break;
				case 6:
					if(currCoords[3] > 0) {
						ok = true;
					}
					break;
				case 7:
					if(currCoords[3] < mazeSize - 1) {
						ok = true;
					}
					break;
				default:
					break;
				}
				
				if(ok) {
					neighborIndex = neighbors[cell];
					valid = true;
				}
			}
			
			// Try to merge selected cell and its neighbor
			if(mazeSet.unionSets(index, neighborIndex)) {
				
				// Successful merge, update cell info
				mazeSet.cellList[index].addConnection(neighborIndex);
				mazeSet.cellList[neighborIndex].addConnection(index);
				// TODO: Modify walls here?
			}
			
			// Refill list of indices if maze isn't complete
			if(indices.empty()) {
				refill(indices, MAXSET);
			}
		
			runs++;
		}
		//System.out.println("Completed maze creation!\n" + mazeSet.toString());
		//TODO: Algo works well enough, translate into output!
		

		// Decode
		try {
			File output = new File("maze.txt");
			if(output.createNewFile()) {
				System.out.println("File successfully created.");
			}
			else {
				// File already exists, do nothing
			}
			// [(-x, x), (-y, y), (-z, z), (-t, t)]
			OutputStream writer = new FileOutputStream(output);
			for(int x = 0; x < mazeSize; x++) {
				
				for(int y = 0; y < mazeSize; y++) {
					
					for(int z = 0; z < mazeSize; z++) {
						
						for(int t = 0; t < mazeSize; t++) {
							// Get coordinates from loops as an array
							//int[] pos = {x,y,z,t};
							int[] pos = {x,y,z,t};
							int result = mazeSet.findCell(pos, mazeSize);
							
							//    0  1    2   3    4   5    6   7
							// [(-x, x), (-y, y), (-z, z), (-t, t)]
							int[] neighbors = findNeighbors(result, mazeSize);
							
							char[] bin = {'1','1','1','1','1','1','1','1'};
							int count = 0;
							
							//System.out.print("Cell " + result + " has neighbors:\n");
							for(int i = 0; i < neighbors.length; i++) {
								
								if(mazeSet.cellList[result].connectedCells.contains(neighbors[i])) {
									bin[i] = '0';
									//System.out.print(neighbors[i] + ", \n");
									count++;
								}
								
							}
							
							String nei = "";
							for(int i = 0; i < 8; i++) {
								nei += neighbors[i];
								nei += " ";
							}
							
							String ans = new String(bin);
							
							
							//System.out.println("Cell " + result + " has bits " + ans);
							//System.out.println("       & neighbors " + mazeSet.cellList[result].connectedCells);
				
							//String ans = new String(bin);
			
							
							writer.write(Integer.parseInt(ans, 2));
						}
					}
				}
			}
			writer.close();
			
		}
		catch(IOException err) {
			//Error handling
			err.printStackTrace();
		}
 		
	}
	
	// method to refill indices in a random order
	public static void refill(Stack<Integer> x, int max) {
		x.clear();
		
		for(int i = 0; i < max; i++) {
			x.push(i);
		}
		
		Collections.shuffle(x);
	}
	
	// Function to select a random set of coordinates to any cell
	public static int[] selectCell(int max) {
		// Create an array to hold 4 random coords
		int[] arr = new int[4];
		
		// Generate 4 random values between 0 and N-1 
		for(int i = 0; i < 4; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0, max + 1);
		}
		// Return filled array to be used as coordinates
		return arr;
	}
	//   0   1     2  3     4  5    6   7
	// [(-x, x), (-y, y), (-z, z), (-t, t)]
	// Function to randomly select a valid neighbor given a current position
	public static int[] findNeighbors(int index, int N) {
		
		int[] result = new int[8];
		// t neighbors
		result[6] = index - 1;
		result[7] = index + 1;
		// z Neighbors
		result[4] = index - N;
		result[5] = index + N;
		// y neighbors
		result[2] = index - (N*N);
		result[3] = index + (N*N);
		// x Neighbors
		result[0] = index - (N*N*N);
		result[1] = index + (N*N*N);
		
		return result;
		
	}
	
	// Function that gets a coordinate from index
	public static int[] getCoords(int index, int N) {
		int xParam = N * N * N;
		
		int yParam = N * N;
		
		int zParam = N;
		
		int x = index / xParam;
		int remainder = index % xParam;
		int y = remainder / yParam;
		remainder = remainder % yParam;
		int z = remainder / zParam;
		remainder = remainder % zParam;
		int t = remainder / 1;
		remainder = remainder % 1;
		
		int[] pos = {x,y,z,t};
		return pos;
	}
	
	// Public to determine whether 
	public static int coinFlip() {
		int rand = ThreadLocalRandom.current().nextInt(0, 1+1);
		if(rand > 0) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	

}
