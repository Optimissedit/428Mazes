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

import java.lang.Math;


public class Main {

	public static void main(String[] args) {
		
		// Takes command-line arguments as value for N (Max size of given dimensions)
		int mazeSize = Integer.parseInt(args[0]);
		
		
		// Use disjoint Set data structure!!
	    int MAXSET = (int) Math.pow(mazeSize, 4);
	    System.out.println(MAXSET);
	    
		// Create 4d Array and populate the values of each cell with a unique integer
		int maze[][][][] = populateStart(mazeSize);

		
	}
	
	
	// Function to return a 4d array where each cell contains a unique integer (increasing from 0)
	public static int[][][][] populateStart(int size) {
		
		// Value to be assigned to each new cell for a Unique ID
		int counter = 0;
		
		// Create the 4d array
		int maze[][][][] = new int[size][size][size][size];
		
		// [t][z][y][x] Coords, starting with t
		for(int t = 0; t < size; t++) {
			// Time loop
			for(int z = 0; z < size;  z++) {
				// Z coord loop
				for(int y = 0; y < size; y++) {
					// Y coord loop
					for(int x = 0; x < size; x++) {
						// Assign current cell a counter value
						maze[t][z][y][x] = counter;
						// Increment counter for next cell
						counter++;
					}
				}
			}
		}
		
		return maze;
		
	}

}
