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
import java.util.HashMap;
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
					//System.out.println("Refillin' the index list, boss!");
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
				
				int [] neighborCoords = findValidNeighbor(currCoords, mazeSize - 1);

				// Index of neighboring cell
				int neighborIndex = mazeSet.findCell(neighborCoords);

					if(mazeSet.unionSets(currIndex, neighborIndex)) {
						// Loop through coordinates to find where they differ
						for(int i = 0; i < 4; i++) {
							if(currCoords[i] != neighborCoords[i]) {
								// Found dimension of broken wall
								int wallRoot = i * 2;
								if(currCoords[i] > neighborCoords[i]) {
									// Wall broken is in the negative direction of this dimension for selected cell
									mazeSet.cellList[currIndex].smashWall(wallRoot);
									mazeSet.cellList[neighborIndex].smashWall(wallRoot + 1);
								}
								else {
									// wallRoot broken is in the positive direction of this dimension for selected
									mazeSet.cellList[currIndex].smashWall(wallRoot + 1);
									mazeSet.cellList[neighborIndex].smashWall(wallRoot);
								}
							}
						}
					} else {
						//System.out.println("Union would create a cycle.");
					}
			}
		}
		// create a map of possible 4 bit binary
		// Horrible brute force method of doing this
		HashMap<Integer, Integer[]> m = new HashMap<Integer, Integer[]>();
		m.put(0, new Integer[] {0,0,0,0});
		m.put(1, new Integer[] {0,0,0,1});
		m.put(2, new Integer[] {0,0,1,0});
		m.put(3, new Integer[] {0,0,1,1});
		m.put(4, new Integer[] {0,1,0,0});
		m.put(5, new Integer[] {0,1,0,1});
		m.put(6, new Integer[] {0,1,1,0});
		m.put(7, new Integer[] {0,1,1,1});
		m.put(8, new Integer[] {1,0,0,0});
		m.put(9, new Integer[] {1,0,0,1});
		m.put(10, new Integer[] {1,0,1,0});
		m.put(11, new Integer[] {1,0,1,1});
		m.put(12, new Integer[] {1,1,0,0});
		m.put(13, new Integer[] {1,1,0,1});
		m.put(14, new Integer[] {1,1,1,0});
		m.put(15, new Integer[] {1,1,1,1});
		
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
			
			for(int x = 0; x < mazeSize; x++) {
				
				for(int y = 0; y < mazeSize; y++) {
					
					for(int z = 0; z < mazeSize; z++) {
						
						for(int t = 0; t < mazeSize; t++) {
							// Get coordinates from loops as an array
							int[] pos = {x,y,z,t};
							int result = mazeSet.findCell(pos);

							writer.write(mazeSet.cellList[result].toString());
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

}
