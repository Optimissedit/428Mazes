import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		// Takes command-line arguments as value for N (Max size of given dimensions)
		int mazeSize = Integer.parseInt(args[0]);
		
		// Determine how many cells the array holds
	    int MAXSET = (int) Math.pow(mazeSize, 4);
	    //System.out.println(MAXSET);

		// Creates a disjoint set data structure to allow easy merging of cells by ID
		DisjointSet mazeSet = new DisjointSet(MAXSET);
		
	
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
	public static int[] findValidNeighbor(int selectedCoords[], int max) {
		int index = ThreadLocalRandom.current().nextInt(0, 3+1);
		// Create a new array to avoid changing selected coord value
		int[] coords = new int[selectedCoords.length];
		// Populate new array with old elements
		for(int i = 0; i < coords.length; i++) {
			coords[i] = selectedCoords[i];
		}
		// Ensure no out of bounds
		if(coords[index] - 1 < 0 || coords[index] + 1 > max - 1) {
			// An operation will be out of bounds. Attempt the opposite
			if(!(coords[index] - 1 < 0)) {
				// Able to subtract
				coords[index] = coords[index] - 1;
				return coords;
			}
			else {
				// Since subtracting is invalid, addition must be possible
				coords[index] = coords[index] + 1;
				return coords;
			}
		}
		else {
			// Add or subtract 1 to the coordinates to grab a neighbors coords
			coords[index] = coords[index] + coinFlip();
			return coords;
		}
		
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
	
	// Function to return a 4d array where each cell contains a unique integer (increasing from 0)
	public static Cell[][][][] populateStart(int size) {

		// Create the 4d array
		Cell maze[][][][] = new Cell[size][size][size][size];
		
		// [t][z][y][x] Coords, starting with t
		for(int x = 0; x < size; x++) {
			// Time loop
			for(int y = 0; y < size;  y++) {
				// Z coord loop
				for(int z = 0; z < size; z++) {
					// Y coord loop
					for(int t = 0; t < size; t++) {
						// Assign current cell a counter value
						maze[x][y][z][t] = new Cell();
					}
				}
			}
		}
		
		return maze;
		
	}

}
