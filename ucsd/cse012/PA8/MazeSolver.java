// Import packages
import java.util.ArrayList;

/* 
 * Class MazeSolver uses priority queue to solve the maze.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang to debug helper methods.
 */

public abstract class MazeSolver {
	
	/* 
	 * solve method to solve the maze with a given priority queue.
	 * 
	 * @param maze maze passed in to solve
	 * @param pq priority queue
	 * @return solved square, null otherwise
	 */
	
	public static Square solve(Maze maze, PriorityQueue<Integer,Square> pq) {
		pq.add(maze.start.getCost(), maze.start);
		// Loops while pq is not empty
		while (!pq.isEmpty()) {
			Entry<Integer, Square> curr = pq.poll();
			Square currSq = curr.value;
			currSq.visit();
			// Return if at finish
			if (currSq.equals(maze.finish)) { return currSq; }
			else {
				// Check adjacent squares in order N, S, E, W
				// if s can move north
				if (checkNorth(maze.contents, curr.value)) {
					// stores square at north position
					Square north = maze.contents[curr.value.getRow()-1][curr.value.getCol()];
					int currCost = curr.key + north.getCost();
					// update and add cost to queue as necessary
					if (currCost < north.getRunningCost()) {
						north.setPrevious(currSq);
						north.setRunningCost(currCost);
						pq.add(currCost, north);
					}
				}
				// if s can move south
				if (checkSouth(maze.contents, curr.value)) {
					// stores square at south position
					Square south = maze.contents[curr.value.getRow()+1][curr.value.getCol()];
					int currCost = curr.key + south.getCost();
					// update and add cost to queue as necessary
					if (currCost < south.getRunningCost()) {
						south.setPrevious(currSq);
						south.setRunningCost(currCost);
						pq.add(currCost, south);
					}
				}
				// if s can move east
				if (checkEast(maze.contents, curr.value)) {
					// stores square at east position
					Square east = maze.contents[curr.value.getRow()][curr.value.getCol()+1];
					int currCost = curr.key + east.getCost();
					// update and add cost to queue as necessary
					if (currCost < east.getRunningCost()) {
						east.setPrevious(currSq);
						east.setRunningCost(currCost);
						pq.add(currCost, east);
					}
				}
				// if s can move west
				if (checkWest(maze.contents, curr.value)) {
					// stores square at west position
					Square west = maze.contents[curr.value.getRow()][curr.value.getCol()-1];
					int currCost = curr.key + west.getCost();
					// update and add cost to queue as necessary
					if (currCost < west.getRunningCost()) {
						west.setPrevious(currSq);
						west.setRunningCost(currCost);
						pq.add(currCost, west);
					}
				}
			}
		}
		return null;
	}
	
	// Helper methods below to make code more readable
	
	/* 
	 * checks to see if you can move north from s.
	 * 
	 * @param contents of square[][]
	 * @param s square we are moving from
	 * @return true if move valid, false if not.
	 */
	
	public static boolean checkNorth(Square[][] contents, Square s) {
//		boolean inBound = s.getRow() - 1 >= 0;
//		boolean notWall = !contents[s.getRow() - 1][s.getCol()].getIsWall();
//		boolean notVisited = !contents[s.getRow() - 1][s.getCol()].isVisited();
//		return (inBound && notWall && notVisited);
		// check to see if moving North is valid.
		return (s.getRow() - 1 >= 0 &&
				!contents[s.getRow() - 1][s.getCol()].getIsWall()
				&& !contents[s.getRow() - 1][s.getCol()].isVisited());
	}
	
	/* 
	 * checks to see if you can move south from s.
	 * 
	 * @param contents of square[][]
	 * @param s square we are moving from
	 * @return true if move valid, false if not.
	 */
	
	public static boolean checkSouth(Square[][] contents, Square s) {
//		boolean inBound = s.getRow() + 1 < contents.length;
//		boolean notWall = !contents[s.getRow() + 1][s.getCol()].getIsWall();
//		boolean notVisited = !contents[s.getRow() + 1][s.getCol()].isVisited();
//		return (inBound && notWall && notVisited);
		// check to see if moving South is valid.
		return (s.getRow() + 1 < contents.length &&
				!contents[s.getRow() + 1][s.getCol()].getIsWall()
				&& !contents[s.getRow() + 1][s.getCol()].isVisited());
	}
	
	/* 
	 * checks to see if you can move east from s.
	 * 
	 * @param contents of square[][]
	 * @param s square we are moving from
	 * @return true if move valid, false if not.
	 */
	
	public static boolean checkEast(Square[][] contents, Square s) {
//		boolean inBound = s.getCol() + 1 < contents[0].length;
//		boolean notWall = !contents[s.getRow()][s.getCol() + 1].getIsWall();
//		boolean notVisited = !contents[s.getRow()][s.getCol() + 1].isVisited();
//		return (inBound && notWall && notVisited);
		// check to see if moving East is valid.
		return (s.getCol() + 1 < contents[0].length &&
				!contents[s.getRow()][s.getCol() + 1].getIsWall()
				&& !contents[s.getRow()][s.getCol() + 1].isVisited());
	}
	
	/* 
	 * checks to see if you can move west from s.
	 * 
	 * @param contents of square[][]
	 * @param s square we are moving from
	 * @return true if move valid, false if not.
	 */
	
	public static boolean checkWest(Square[][] contents, Square s) {
//		boolean inBound = s.getCol() - 1 >= 0;
//		boolean notWall = !contents[s.getRow()][s.getCol() - 1].getIsWall();
//		boolean notVisited = !contents[s.getRow()][s.getCol() - 1].isVisited();
//		return (inBound && notWall && notVisited);
		// check to see if moving West is valid.
		return (s.getCol() - 1 >= 0 && 
				!contents[s.getRow()][s.getCol() - 1].getIsWall()
				&& !contents[s.getRow()][s.getCol() - 1].isVisited());
	}
}