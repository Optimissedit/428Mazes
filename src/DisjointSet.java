
// Class to create a disjoint set data structure to more easily track set operations
// Index represents cells unique ID, value at index shows what set it is currently in
public class DisjointSet {
	
	// Array to hold parent of sets
	Cell[] cellList;
	// Number of cells in the maze
	int numCells;
	
	// Constructor
	public DisjointSet(int c) {
		numCells = c;
		cellList = new Cell [c];
		// Call function to fill indexes with corresponding values (index[0] = 0... etc)
		makeSets();
	}
	
	
	
	// Function to make singleton sets for all cells in the maze
	void makeSets() {
		for(int i = 0; i < numCells; i++) {
			// Fills the parent array with integers ascending from 0 that serve as root to trees
			cellList[i] = new Cell();
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
	
	
	// Unions two sets into a new subset
	boolean unionSets(int i0, int i1) {
		
		// find parent of each set
		int parent0 = findParent(i0);
		int parent1 = findParent(i1);
		
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
