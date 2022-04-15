import java.util.ArrayList;
import java.util.Arrays;

public class Cell {
	
	// Static counter gives each new cell a unique ID
	static int counter = 0;
	// Variable to hold ID
	int id;
	// Representative of the set this cell belongs to
	int parent;
	// Characters to represent degrees of freedom in this cell (char[0] == X-, char[1] == X+, ETC.)
	int[] walls = {1,1,1,1,1,1,1,1};
	// Holds indexes of connected cells 
	ArrayList<Integer> connectedCells = new ArrayList<>();
	
	// Default constructor
	public Cell() {
		id = counter;
		parent = id;
		counter++;
	}
	
	// Getter for ID value
	int getID() {
		return id;
	}
	
	int getParent() {
		return parent;
	}
	
	void setParent(int p) {
		parent = p;
	}
	// Add a cell index to the list of neighbors
	public void addConnection(int c) {
		connectedCells.add(c);
	}
	
	void smashWall(int wall) {
		// Check if this wall was already removed
		if(walls[wall] == 0) {
			// Invalid smash, do nothing
			System.out.println("Already smashed this wall in cell " + id);
		}
		else {
			walls[wall] = 0;
		}
	}
	
	// Returns the bits representation of this cell's walls
	public String toString() {
		// Create a string from the walls array, removing unneeded characters
		String result = Arrays.toString(walls).replaceAll("\\]|\\[|,|\\s", "");
		return result;
	}
	
	
}
