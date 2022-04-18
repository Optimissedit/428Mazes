
// Class to create a disjoint set data structure to more easily track set operations
// Index represents cells unique ID, value at index shows what set it is currently in
public class DisjointSet {
	
	// Array to hold parent of sets
	Cell[] cellList;
	
	int[] rank;
	// Number of cells in the maze
	int numCells;
	
	// Constructor
	public DisjointSet(int c) {
		numCells = c;
		rank = new int[c];
		cellList = new Cell [c];
		// Call function to fill indexes with corresponding values (index[0] = 0... etc)
		makeSets();
	}
	
	
	
	// Function to make singleton sets for all cells in the maze
	void makeSets() {
		for(int i = 0; i < numCells; i++) {
			// Fills the parent array with integers ascending from 0 that serve as root to trees
			cellList[i] = new Cell();
			rank[i] = 0;
		}
	}
	
	int findCell(int[] x, int mazeSize) {
		// Index can be found with x(N^0) + y(N^1) + z(N^2) + t(N^3)
		int result = x[0] + (x[1] * mazeSize) + (x[2] * (int) Math.pow(mazeSize, 2)) + (x[3] * (int) Math.pow(mazeSize, 3));
		return result;
	}
	
	// Method to find the parent integer of a set
	int findParent(int c) {
		
		// Given integer is parent of itself
		if(cellList[c].getParent() == c) {
			return c;
		}
		else
		{
			int find = findParent(cellList[c].getParent());
			
			cellList[c].parent = find;
			
			return find;
		}
	}
	
	
	// Unions two sets into a new subset
	boolean unionSets(int i0, int i1) {
		
		// find parent of each set
		int parent0 = findParent(i0);
		int parent1 = findParent(i1);
		
		if(parent0 == parent1) {
			// Both sets are already the same, no need to union
			return false;
		}
		if(rank[parent0] < rank[parent1]) {
			cellList[parent0].setParent(parent1);
			return true;
		}
		else if(rank[parent0] > rank[parent1]) {
			cellList[parent1].setParent(parent0);
			return true;
		}	
		else {
			
			cellList[parent1].setParent(parent0);
			
			rank[parent0] = rank[parent0] + 1;
			return true;
		}

	}
	
	// Method to return all cells and their parents in a list
	public String toString() {
		String result = "";
		for(int i = 0; i < cellList.length; i++) {
			if(cellList[i].getID() == cellList[i].getParent()) {
				result += "Cell " + cellList[i].getID() + " parents itself!" + "\n";
			}
			else {
				result += "Cell " + cellList[i].getID() + " has parent " + cellList[i].getParent() + "\n";
			}
		}
		return result;
	}
	// Method to return t cells starting from 0 as string
	public String toString(int t) {
		String result = "";
		for(int i = 0; i < t; i++) {
			
			if(cellList[i].getID() == cellList[i].getParent()) {
				result += "Cell " + cellList[i].getID() + " parents itself!" + "\n";
			}
			else {
				result += "Cell " + cellList[i].getID() + " has parent " + cellList[i].getParent() + "\n";
			}

		}
		return result;
	}
	
}
