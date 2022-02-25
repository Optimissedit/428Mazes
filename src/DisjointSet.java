import java.util.Arrays;

// Class to create a disjoint set data structure to more easily track set operations
// Index represents cells unique ID, value at index shows what set it is currently in
public class DisjointSet {
	
	// Array to hold cell objects (ID of cell will act as parent)
	Cell[] cellList;
	// Max index of each dimension in 4d maze
	int mazeSize;
	
	// Constructor
	public DisjointSet(int c) {
		mazeSize = c;
		cellList = new Cell [(int) Math.pow(mazeSize, 4)];
		// Call function to fill indexes with corresponding values (index[0] = 0... etc)
		makeSets();
	}
	
	// Function to make singleton sets for all cells in the maze
	void makeSets() {
		for(int x = 0; x < mazeSize; x++) {
			
			for(int y = 0; y < mazeSize; y++) {
				
				for(int z = 0; z < mazeSize; z++) {
					
					for(int t = 0; t < mazeSize; t++) {
						// Get coordinates from loops as an array
						int[] pos = {x,y,z,t};
						// Create a new cell at those coordinates
						Cell arr = new Cell(pos);
						// Add that new cell to its index in cellList
						cellList[arr.getParent()] = arr;
					}
				}
			}
		}
	}
	
	// Method to find the parent integer of a set
	int findParent(int c) {
		
		// Given integer is parent of itself
		if(cellList[c].getParent() == c) {
			return c;
		}
		else
		{
			//System.out.println("Test: INT " + parent[c] + " is not parent of " + c + ", recalling function.");
			// Given integer is not the parent, recursively check c's parent until found
			return findParent(cellList[c].getParent());
		}
	}
	
	// Function to find a cell based on its given coordinates
	// TODO: Find out why this is failing to find coords
	int findCell(int[] x) {
		// Temp variable to compare given coords to
		int[] holder = new int[4];
		boolean failed = false;
		
		for(int i = 0; i < (int) Math.pow(mazeSize, 4); i++) {
			holder = cellList[i].getCoords();
			
			if(Arrays.equals(x, holder)) {
				//System.out.println("Found coordinates! :)");
				return i;
			}
		}
		System.out.println("Failed to find coords");
		return -1;
	}
	
	
	// Unions two sets into a new subset
	// TODO: Implement wall checking here?
	boolean unionSets(int c0, int c1) {
		
		// find parent of each set
		int parent0 = findParent(c0);
		int parent1 = findParent(c1);
		
		if(parent0 == parent1) {
			// Both sets are already the same, no need to union
			return false;
		}
		else {
			// Set parent of c0 to parent of c1 to move sets under same parent
			cellList[parent0].setParent(parent1);
			return true;
		}

	}
	
}
