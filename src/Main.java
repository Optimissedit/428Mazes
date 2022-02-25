// Algorithm:
/*
 *First, create a randomized stack of indexes. This will randomly select every index one time.
 *With the selected index, find a valid neighbor of the cell. Do not pass unless every neighbor is invalid
 *Break down walls with valid neighbor
 *Update cell information accordingly
 *Repeat with all indexes
 * -- If All cells are still not in the same set, repeat the process.
 * Once all cells are in the same set, print the resulting bits 
 */

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
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
	    
	    // Create a stack of randomized cell indexes
	    Stack<Integer> indexStack = fillIndexes(MAXSET);
	    
		// Creates a disjoint set data structure to allow easy merging of cells by ID
		DisjointSet mazeSet = new DisjointSet(mazeSize);
		
		// Create main algorithm loop
		boolean finished = false;
		while(!finished) {
			
			// Check to ensure we have more indexes to check 
			if(indexStack.size() < 1) {
							
				// Check to see if all cells exist in one set
				int holder = mazeSet.findParent(0);
				
				boolean exit = true;
				// Loop to check if all elements exist in same set (same parent)
				for(int i = 0; i < MAXSET; i++) {
					if(holder != mazeSet.findParent(i)) {
						// Not all in same set, continue breaking walls
						exit = false;
						break;
					}
				}
				
				if(exit) {
					// All cells are in the same set! Loop is done
					System.out.println("All cells in set, loop completed");
					finished = true;
				}
				else {
					//TODO: Ensure this doesn't cause any issues with shallow copies, etc.
					// More walls need to be broken. More indexes are needed.
					System.out.println("Refillin' the index list, boss!");
					indexStack = fillIndexes(MAXSET);
				}	
			}
			else {
				// End condition checking over
				
				// Grab an index for a Cell
				int currIndex = indexStack.pop();
				// Grab the coordinates for that index
				int[] x = mazeSet.cellList[currIndex].getCoords();
				
				// Create a true copy of coords to avoid issues
				int[] currCoords = new int[4];
				for(int i = 0; i < x.length; i++) {
					currCoords[i] = x[i];
				}

				int [] neighborCoords = findValidNeighbor(currCoords, mazeSize);
				
				// Index of neighboring cell
				int neighborIndex = mazeSet.findCell(neighborCoords);
				// Ensure coordinates are found
				if(neighborIndex != -1) {
					// TODO: Change unionsets to a check
					// If The merge fails, we can just skip and not check wall break
					mazeSet.unionSets(currIndex, neighborIndex);
					
					// Loop through coordinates to find where they differ
					for(int i = 0; i < 4; i++) {
						if(currCoords[i] != neighborCoords[i]) {
							// Found dimension of broken wall
							int wall = i * 2;
							if(currCoords[i] > neighborCoords[i]) {
								// Wall broken is in the negative direction of this dimension for selected cell
								mazeSet.cellList[currIndex].smashWall(wall);
								mazeSet.cellList[neighborIndex].smashWall(wall + 1);
							}
							else {
								// Wall broken is in the positive direction of this dimension for selected
								mazeSet.cellList[currIndex].smashWall(wall + 1);
								mazeSet.cellList[neighborIndex].smashWall(wall);
							}
						}
					}
				} 
			}
		}
		// Exited main program loop. Write to file.
		try {
			File output = new File("maze.txt");
			if(output.createNewFile()) {
				System.out.println("File successfully created.");
			}
			else {
				// File already exists, do nothing
			}
			
			FileWriter writer = new FileWriter(output);
			for(int i = 0; i < MAXSET; i++) {
				writer.write(mazeSet.cellList[i].toString());
				}
			writer.close();
			
		}
		catch(IOException err) {
			//Error handling
			err.printStackTrace();
		}
	}
	
	
	public static void breakWall(int s, int n, int[] selected, int[] neighbor) {
		

	}
	
	// Function to create a stack of randomized cell indexes
	public static Stack<Integer> fillIndexes(int max) {
		
		// Create an array list to hold all indexes of cells
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		// Populate list with cell indexes
		for(int i = 0; i < max; i++) {
			list.add(i);
		}
		
		// Shuffle list to create random order
		Collections.shuffle(list);
		
		// Create a new stack and fill it with the shuffled indexes
		Stack<Integer> stack = new Stack<Integer>();
		for(int i = 0; i < max; i++) {
			stack.add(list.get(i));
		}
		
		return stack;
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
		// Create arraylist to shuffle data in
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0,1,2,3));
		// Shuffle list
		Collections.shuffle(list);
		// Create a new stack and fill it with the shuffled indexes
		Stack<Integer> stack = new Stack<Integer>();
		for(int i = 0; i < 4; i++) {
			stack.add(list.get(i));
		}
		
		// Create an array to change and pass back at function end
		int[] result = new int[4];
		for(int i = 0; i < 4; i++) {
			result[i] = selectedCoords[i];
		}
		
		// Loop to check all possible axis in a random order for a valid neighbor
		// Dimensions = (x,y,z,t) = (0,1,2,3)
		boolean complete = false;
		while(!complete) {
			// Create variable to hold what dimension is being checked
			int dimension = 0;
			// If stack is exhausted, no valid moves. Mark result[0] -1 to signify
			if(stack.isEmpty()) {
				complete = true;
				result[0] = -1;
			}
			else {
				// Grab an int off the stack
				dimension = stack.pop();
			}

			// Assess checking for a neighbor in + or - direction of dimension
			switch(dimension) {
				case 0:
					if(selectedCoords[0] > 0 && selectedCoords[0] < max) {
						// Valid to either increment or decrement
						result[0] = result[0] + coinFlip();
						complete = true;
					}
					else if(selectedCoords[0] <= 0) {
						// Too low to decrement, would open to outside
						result[0] = result[0] + 1;
					}
					else {
						// Too high to increment, would open to outside
						result[0] = result[0] - 1;
					}
					break;
				case 1:
					if(selectedCoords[1] > 0 && selectedCoords[1] < max) {
						// Valid to either increment or decrement
						result[1] = result[1] + coinFlip();
						complete = true;
					}
					else if(selectedCoords[1] <= 0) {
						// Too low to decrement, would open to outside
						result[1] = result[1] + 1;
					}
					else {
						// Too high to increment, would open to outside
						result[1] = result[1] - 1;
					}
					break;
				case 2: 
					if(selectedCoords[2] > 0 && selectedCoords[2] < max) {
						// Valid to either increment or decrement
						result[2] = result[2] + coinFlip();
						complete = true;
					}
					else if(selectedCoords[2] <= 0) {
						// Too low to decrement, would open to outside
						result[2] = result[2] + 1;
					}
					else {
						// Too high to increment, would open to outside
						result[2] = result[2] - 1;
					}
					break;
				case 3:
					if(selectedCoords[3] > 0 && selectedCoords[3] < max) {
						// Valid to either increment or decrement
						result[3] = result[3] + coinFlip();
						complete = true;
					}
					else if(selectedCoords[3] <= 0) {
						// Too low to decrement, would open to outside
						result[3] = result[3] + 1;
					}
					else {
						// Too high to increment, would open to outside
						result[3] = result[3] - 1;
					}
					break;
				default:
					System.out.println("default case of switch in findValidNeighbor function???");
					break;
			}
			
		}
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
