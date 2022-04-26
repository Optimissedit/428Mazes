import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeReader {

	public static void main(String[] args) {
		
		ArrayList<Cell> mazeCells = new ArrayList<>();
		
		int mazeSize = 20;
		
		try {
			
			File maze = new File("maze.txt");
			File output = new File("mazeresults.txt");
			
			InputStream in = new FileInputStream(maze);
			OutputStream out = new FileOutputStream(output);
			
			Scanner user = new Scanner(System.in);
			
			int counter = 0;
			
			for(int x = 0; x < mazeSize; x++) {
					
				for(int y = 0; y < mazeSize; y++) {
						
					for(int z = 0; z < mazeSize; z++) {
						
						for(int t = 0; t < mazeSize; t++) {
							
							int[] pos = {x,y,z,t};
							
							String position = "" + pos[0] + pos[1] + pos[2] + pos[3];
							int index = findCell(pos,mazeSize);
							
							Integer cell = in.read();
							
							String result = Integer.toBinaryString(cell);
							
							while(result.length() < 8) {
								result = "0"+result;
							}
							
							String write = "Cell " + position + " (" + counter + "): " + result + "\n";
							System.out.println(write);
							
							out.write(write.getBytes());
							
							
							counter++;
							
						}
					}
				}
			}
			
			in.close();
			user.close();
			
		} catch (IOException e) {
			e.getMessage();
		}
		

	}
	

	public static int[] findNeighbors(int index, int N) {
		
		int[] result = new int[8];
		// X neighbors
		result[6] = index - 1;
		result[7] = index + 1;
		// Y Neighbors
		result[4] = index - N;
		result[5] = index + N;
		// Z neighbors
		result[2] = index - (N*N);
		result[3] = index + (N*N);
		// T Neighbors
		result[0] = index - (N*N*N);
		result[1] = index + (N*N*N);
		
		return result;
		
	}
	static int findCell(int[] x, int mazeSize) {
		// Index can be found with x(N^0) + y(N^1) + z(N^2) + t(N^3)
		int result = x[0] + (x[1] * mazeSize) + (x[2] * (int) Math.pow(mazeSize, 2)) + (x[3] * (int) Math.pow(mazeSize, 3));
		return result;
	}

}
