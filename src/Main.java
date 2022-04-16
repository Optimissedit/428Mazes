import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		// Takes command-line arguments as value for N (Max size of given dimensions)
		int mazeSize = Integer.parseInt(args[0]);
		
		// Determine how many cells the array holds
	    int MAXSET = (int) Math.pow(mazeSize, 4);
	    //System.out.println(MAXSET);

		// Creates a disjoint set data structure to allow easy merging of cells by ID
		DisjointSet mazeSet = new DisjointSet(MAXSET);
		
		//System.out.println(mazeSet);
	
		boolean flag = false;
		while(!flag) {
			
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
			
			// Find a random cell
			Random rand = new Random();
			int index = rand.nextInt(MAXSET);
			
			// Grab all its neighbors
			int[] neighbors = findNeighbors(index, mazeSize);
			
			// Select a valid neighbor to merge current index with
			
			int neighborIndex;
			boolean valid = false;
			
			while(!valid) {
				
				int cell = rand.nextInt(8);
				
				if(neighbors[cell] >= 0) {
					neighborIndex = neighbors[cell];
					valid = true;
				}
			}
			
			// Try to merge selected cell and its neighbor
			
		
		
		}
		

		// Decode
		try {
			File output = new File("maze.txt");
			if(output.createNewFile()) {
				System.out.println("File successfully created.");
			}
			else {
				// File already exists, do nothing
			}
			
			FileWriter writer = new FileWriter(output);

			writer.close();
			
		}
		catch(IOException err) {
			//Error handling
			err.printStackTrace();
		}
 		
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
	
	// Function to randomly select a valid neighbor given a current position
	public static int[] findNeighbors(int index, int N) {
		
		int[] result = new int[8];
		// X neighbors
		result[0] = index - 1;
		result[1] = index + 1;
		// Y Neighbors
		result[2] = index - N;
		result[3] = index + N;
		// Z neighbors
		result[4] = index - (N*N);
		result[5] = index + (N*N);
		// T Neighbors
		result[6] = index - (N*N*N);
		result[7] = index + (N*N*N);
		
		return result;
		
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
