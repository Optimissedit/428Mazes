# 4D - Maze Generator

This program takes in a value N and randomly generates a 4d maze with dimensions `(N-1, N-1, N-1, N-1)`

Every cell in the maze is accessible from any other and there are no paths to the 'outside'.

## Output

Output is written to a file, maze.txt. This file contains 8 bit representations of every cell, where the polarity of each bit represents movement potential in a direction. This format can be visualized as `[(-X, X), (-Y, Y), (-Z, Z), (-T, T)]` where a 0 shows allowing movement and a 1 shows restricted movement.

For example, a cell with bits `10 11 11 11` only allows movement in the positive X direction.


