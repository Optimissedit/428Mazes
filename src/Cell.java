import java.util.Arrays;

public class Cell {
	
	// Static counter gives each new cell a unique ID
	static int counter = 0;
	static int counter2 = 0;
	// Variable to hold ID
	int parent;
	// Variable to represent a cell's coordinates
	int[] coords;
	// Characters to represent degrees of freedom in this cell (char[0] == X-, char[1] == X+, ETC.)
	int[] walls = {1,1,1,1,1,1,1,1};
	
	// Default constructor
	public Cell() {
		parent = counter;
		counter++;
		coords = new int[4];
	}
	
	// Constructor to accept coordinates for the new cell
	public Cell(int[] x) {
		parent = counter;
		counter++;
		coords = new int[4];
		// Loop through given coords and change each element in original coords
		for(int i = 0; i < 4; i++) {
			coords[i] = x[i];
		}
	}
	
	// Function to change what index this cell points to as its parent
	void setParent(int x) {
		parent = x;
	}
	
	
	// Function to set Cell's coordinates. Should only be used with arrays of length 4
	void setCoords(int[] x) {
		// Loop through given coords and change each element in original coords
		for(int i = 0; i < 4; i++) {
			coords[i] = x[i];
		}
	}
	
	// Getter for coords
	int[] getCoords() {
		return coords;
	}
	
	// Getter for parent's value
	int getParent() {
		return parent;
	}
	
	void smashWall(int wall) {
		// Check if this wall was already removed
		if(walls[wall] == 0) {
			// Invalid smash, do nothing
			//System.out.println("Already smashed this wall in cell ");
		}
		else {
			walls[wall] = 0;
		}
	}
	
	// Returns the bits representation of this cell's walls
	public String toString() {
		// Create a string from the walls array, removing unneeded characters
		String result = Arrays.toString(walls).replaceAll("\\]|\\[|,|\\s", "");
		String result1 = Arrays.toString(coords).replaceAll("\\]|\\[|,|\\s", "");
		System.out.println(result + " -- " + result1 + " - " + counter2);
		counter2++;
		return result;
	}
	
	
}
