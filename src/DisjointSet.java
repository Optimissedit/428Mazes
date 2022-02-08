
// Class to create a disjoint set data structure to more easily track set operations
// Index represents cells unique ID, value at index shows what set it is currently in
public class DisjointSet {
	
	// Array to hold parent of sets
	int[] parent;
	// Number of cells in the maze
	int cells;
	
	// Constructor
	public DisjointSet(int c) {
		cells = c;
		parent = new int [c];
		// Call function to fill indexes with corresponding values (index[0] = 0... etc)
		makeSets();
	}
	
	// Function to make singleton sets for all cells in the maze
	void makeSets() {
		for(int i = 0; i < cells; i++) {
			// Fills the parent array with integers ascending from 0 that serve as root to trees
			parent[i] = i;
		}
	}
	
	// Method to find the parent integer of a set
	int findParent(int c) {
		
		// Given integer is parent of itself
		if(parent[c] == c) {
			return c;
		}
		else
		{
			System.out.println("Test: INT " + parent[c] + " is not parent of " + c + ", recalling function.");
			// Given integer is not the parent, recursively check c's parent until found
			return findParent(parent[c]);
		}
	}
	
	
	// Unions two sets into a new subset
	void unionSets(int c0, int c1) {
		
		// find parent of each set
		int parent0 = findParent(c0);
		int parent1 = findParent(c1);
		
		if(parent0 == parent1) {
			// Both sets are already the same, no need to union
			System.out.println("Cannot union identical sets");
		}
		else {
			// Set parent of c0 to parent of c1 to move sets under same parent
			parent[parent0] = parent1;
		}

	}
	
}
