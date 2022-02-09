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
		
		mazeSet.unionSets(0, 1);
		mazeSet.unionSets(1, 2);
		mazeSet.unionSets(2, 0);
		System.out.println(mazeSet.findParent(0));
		
		
		boolean complete = true;		
		while(!complete) {
			// Grab the first set's parent to compare to others
			int holder = mazeSet.parent[0];
			
			// Boolean flag to mark whether all elements are part of the same set
			boolean flag = true;
			// Loop through all parents, ensuring sameness (all cells are reachable)
			for(int i = 1; i < MAXSET; i++) {
				// Cells not in the same set found, loop needs to continue
				if(holder != mazeSet.parent[i]) {
					flag = false;
					break;
				}
			}
			// If all cells are in the set, loop will end
			if(flag) {
				System.out.println("Flag flipped, loop ending");
				complete = true;
			}
			
			// Select a cell by coordinates
			int selectedCoords[] = selectCell(mazeSize - 1);

			// Find a neighbor cell from selection
			int neighborCoords[] = findValidNeighbor(selectedCoords, mazeSize - 1);
	
			//Grab ID values of selected cell
			int selectedID = maze[selectedCoords[0]][selectedCoords[1]][selectedCoords[2]][selectedCoords[3]].getID();
			int neighborID = maze[neighborCoords[0]][neighborCoords[1]][neighborCoords[2]][neighborCoords[3]].getID();
						
			// Checks to see if these cells are a valid union
			if(!(mazeSet.unionSets(selectedID, neighborID))) {
				// Invalid, do not complete this merge and smash
			}
			else {
				// Valid, smash walls
				
				for(int i = 0; i < mazeSize - 1; i++) {
					// Find axis of walls
					if(selectedCoords[i] != neighborCoords[i]) {
						// Get bit position of this axis
						int axisroot = i * 2;
						
						
						if(selectedCoords[i] > neighborCoords[i]) {
							// Neighbor is down one in same axis, bust negative for selected
							maze[selectedCoords[0]][selectedCoords[1]][selectedCoords[2]][selectedCoords[3]].smashWall(axisroot);
							// Neighbors wall is positive
							maze[neighborCoords[0]][neighborCoords[1]][neighborCoords[2]][neighborCoords[3]].smashWall(axisroot + 1);
						}
						else {
							// Neighbor is up one on same axis, bust positive
							maze[selectedCoords[0]][selectedCoords[1]][selectedCoords[2]][selectedCoords[3]].smashWall(axisroot + 1);
							// Neighbors wall is negative
							maze[neighborCoords[0]][neighborCoords[1]][neighborCoords[2]][neighborCoords[3]].smashWall(axisroot);
						}
					}
				}
			}
			//System.out.println("Neighbor ID = " + neighborID);
			//System.out.println("Parent of selected = " + mazeSet.findParent(selectedID));
			//System.out.println(maze[selectedCoords[0]][selectedCoords[1]][selectedCoords[2]][selectedCoords[3]].toString());
			//System.out.println(maze[neighborCoords[0]][neighborCoords[1]][neighborCoords[2]][neighborCoords[3]].toString());
			
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
