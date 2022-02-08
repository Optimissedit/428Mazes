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


public class Main {

	public static void main(String[] args) {
		
		// Takes command-line arguments as value for N (Max size of given dimensions)
		int mazeSize = Integer.parseInt(args[0]);
		
		// Determine how many cells the array holds
	    int MAXSET = (int) Math.pow(mazeSize, 4);
	    //System.out.println(MAXSET);
	    
		// Create 4d Array and populate the values of each cell with a unique integer
		Cell maze[][][][] = populateStart(mazeSize);
		
		// Creates a disjoint set data structure to allow easy merging of cells by ID
		DisjointSet mazeSet = new DisjointSet(MAXSET);
		
		
		maze[1][2][3][4].smashWall(2);
		
		System.out.println(maze[1][2][3][4].toString());
	
		// 4D array of Cell objects
		// Select random coordinates and get its ID
		// Find valid neighbors of that cell and randomly select one
		// Attempt to merge the cells
		// If valid, adjust bits for both cells 
		// if invalid, retry other neighbors
		// If none valid, mark(?) cell, select new random coords
		// Repeat until all cell IDs in same set
		
		// Create disjoint set
 		
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
