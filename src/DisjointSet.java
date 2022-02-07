
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
		makeSet();
	}
	
	// Function to create singleton sets for all cells in the maze
	void makeSet() {
		for(int i = 0; i < cells; i++) {
			// Fills the parent array with integers ascending from 0
			parent[i] = i;
		}
	}
	
}
