// Creating a 4d Maze
// Create a 4D array, give each cell a unique number (i.e 123456...)
// Create set operations (create set, merge sets, search sets)
// Randomly select a cell by integer
// Check its neighbors for valid walls by coordinate
// Ensure merging new cell won't create a loop (would be comparing two sets of same elements)
// Merge new cell with other cell or set of cells

// Decoding the maze
// Starting with 'completed' set of cells, determine each axis of motion
// I.E check both positions of X axis and get their integer. Check final set for those integers. If found, there is no wall between the cells.
// Print cell bits to file as generated, once done with whole set file will be complete


// Alternative method: 
// Disjoint Set of 'cell' objects
// when doing unions, adjust 'bits' variable to ensure correct 'walls'
// Ignore unions after all 'cells' in one set, simply print all cells 'bits' in order


import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;


public class Main {

	public static void main(String[] args) {
		
		// Takes command-line arguments as value for N (Max size of given dimensions)
		int mazeSize = Integer.parseInt(args[0]);
		
		// Determine how many cells the array holds
	    int MAXSET = (int) Math.pow(mazeSize, 4);
	    //System.out.println(MAXSET);
	    
		// Create 4d Array of cells to hold cell objects
		Cell maze[][][][] = populateStart(mazeSize);
		
		// Creates a disjoint set data structure to allow easy merging of cells by ID
		DisjointSet mazeSet = new DisjointSet(MAXSET);
		
		
		boolean complete = false;		
		while(!complete) {
			// Grab the first set's parent to compare to others
			int holder = mazeSet.parent[0];
			
			// Loop through all parents, ensuring sameness (all cells are reachable)
			for(int i = 1; i < MAXSET; i++) {
				// Cells not in the same set found, loop needs to continue
				if(holder != mazeSet.parent[0]) {
					break;
				}
				else {
					// All cells in the same set!
					complete = true;
				}
			}
			
			// Select a cell by coordinates
			int selectedCoords[] = selectCell(mazeSize);
			// Find a neighbor cell from selection
			int neighborCoords[] = findValidNeighbor(selectedCoords, mazeSize);
			//Grab ID values of selected cell
			int selectedID = maze[selectedCoords[0]][selectedCoords[1]][selectedCoords[2]][selectedCoords[3]].getID();
			int neighborID = maze[neighborCoords[0]][neighborCoords[1]][neighborCoords[2]][neighborCoords[3]].getID();
			
			// Checks to see if these cells are a valid union
			if(!(mazeSet.unionSets(selectedID, neighborID))) {
				// Invalid, do not complete this merge and smash
			}
			else {
				// Valid, smash walls
				
			}
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
	public static int[] findValidNeighbor(int coords[], int max) {
		int index = ThreadLocalRandom.current().nextInt(0, 3+1);
		
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
