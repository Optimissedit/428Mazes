
// Class to create a disjoint set data structure to more easily track set operations
public class DisjointSet {
	
	// Properties to control tree structure 
	int[] rank;
	int[] parent;
	// Number of cells in the maze
	int cells;
	
	// Constructor
	public DisjointSet(int c) {
		cells = c;
		rank = new int [c];
		parent = new int [c];
		// Call function to create singleton sets for all cells
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
			// Given integer is not the parent, recursively check c's parent until found
			return findParent(parent[c]);
		}
	}
	
	
	// Unions two sets into a new subset
	void unionSets(int c0, int c1) {
		
		// find parent of each set
		int parent0 = findParent(c0);
		int parent1 = findParent(c1);
		
		// Set parent of c0 to parent of c1 to move sets under same parent
		parent[parent0] = parent1;
	}
	
}
