import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

/*
 * Write your JUnit tests here
 * Use the formatMaze() method to get nicer JUnit output
 */

import org.junit.Test;
import static org.junit.Assert.*;

class IntComparator implements Comparator<Integer> {
	public int compare(Integer a, Integer b) {
		return b - a;
	}
}


public class TestSolvers {

	/* Helper method to compare two mazes */
	public void checkMaze(PriorityQueue<Integer,Square> pq, Maze startMaze, String[] expected) {
		Square s = MazeSolver.solve(startMaze, pq);
		if(expected == null) { assertNull(s); }
		else {
			ArrayList<Square> sp = startMaze.storePath();
			String actualStr = formatMaze(startMaze.showSolution(sp));
			String expectedStr = formatMaze(expected);
			assertEquals(expectedStr, actualStr);
		}
	}	

	/* Helper method to format String[] output as String */
	public String formatMaze(String[] arr) {
		String result = "";
		for (String s: arr)
			result += "\n"+s;
		return (result+"\n");
	}

	/* Add your own Worklist tests below */

	/* ************** HINT ******************** 
	 * Use the helper methods to create simple
	 * tests that are easier to debug. 
	 */

	// Given test.
	@Test
	public void testshortExample() {
		String[] mazeString = new String[] { 
				"#_#_", 
				"____", 
				"_##S", 
				"F___" };
		int[][] costArray = new int[][] {	
				{0,0,0,0}, 
				{4,3,2,1}, 
				{5,0,0,0}, 
				{50,8,2,1} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"#_#_", 
				"____", 
				"_##S", 
				"F***", };
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with long solution.
	@Test
	public void testMazeSolver_solutionLong() {
		String[] mazeString = new String[] { 
				"___S",
				  "_##_",
				  "_##_",
				  "___F" 
				};
		int[][] costArray = new int[][] {	
			{1,1,1,0},
			  {1,0,0,5},
			  {1,0,0,5},
			  {1,1,1,1} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"***S",
				  "*##_",
				  "*##_",
				  "***F" 
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with short solution
	@Test
	public void testMazeSolver_solutionShort() {
		String[] mazeString = new String[] { 
				"___S",
				  "_##_",
				  "_##_",
				  "___F" 
				};
		int[][] costArray = new int[][] {	
			{2,2,2,0},
			  {2,0,0,5},
			  {2,0,0,5},
			  {2,2,2,1} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"___S",
				  "_##*",
				  "_##*",
				  "___F" 
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with 1x8.
	@Test
	public void testMazeSolver_solution_1x8() {
		String[] mazeString = new String[] { 
				"F______S" 
				};
		int[][] costArray = new int[][] {	
			{1,1,1,1,1,1,1,0} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"F******S" 
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with 4x1..
	@Test
	public void testMazeSolver_solution_4x1() {
		String[] mazeString = new String[] { 
				"S",
				  "_",
				  "_",
				  "F"
				};
		int[][] costArray = new int[][] {	
			{0},
			  {1},
			  {1},
			  {1} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"S",
				  "*",
				  "*",
				  "F"
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with 10x10.
	@Test
	public void testMazeSolver_Solution_10x10() {
		String[] mazeString = new String[] { 
				"##S____#_#",
				  "_#_#_#_#__",
				  "#_##______",
				  "____##__#_",
				  "##_##_____",
				  "__#_______",
				  "##_#______",
				  "_#___##___",
				  "____##___#",
				  "_#_##____F"
				};
		int[][] costArray = new int[][] {	
			{0,  0, 0,  6, 17,  5,  8,  0,  2,  0},
			{6,  0, 12,  0, 17,  0,  7,  0,  5,  2},
			{0, 17,  0,  0,  7,  8, 10, 11, 15,  3},
			{6, 17,  3,  4,  0,  0, 12, 10,  0,  2},
			{0,  0, 10,  0,  0,  9,  1, 10,  6,  1},
			{12, 17,  0, 10,  3,  1,  7, 16, 16,  9},
			{0,  0, 10,  0,  2,  2, 14, 18,  7, 15},
			{7,  0,  7, 15,  4,  0,  0,  9, 15, 17},
			{5, 17, 14, 19,  0,  0,  4,  2,  7,  0},
			{3,  0, 12,  0,  0,  5, 12, 13, 18, 14} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"##S****#_#",
				"_#_#_#*#__",
				"#_##__*___",
				"____##*_#_",
				"##_##_*___",
				"__#___*___",
				"##_#__**__",
				"_#___##*__",
				"____##_**#",
				"_#_##___*F"
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with 15x10.
	@Test
	public void testMazeSolver_Solution_15x10() {
		String[] mazeString = new String[] { 
				"F_______#_",
				  "_________#",
				  "__#_______",
				  "_____#__#_",
				  "__#__#___#",
				  "____######",
				  "___#______",
				  "__##_#___#",
				  "___##__#_#",
				  "_____#__#_",
				  "_#____#___",
				  "__#______#",
				  "#_____#___",
				  "___#__#___",
				  "#___##___S"
				};
		int[][] costArray = new int[][] {	
			{3,  8,  6,  9,  6,  4, 13, 16,  0,  2},
			 {10,  4, 13, 17, 17,  4, 17, 13,  5,  0},
			 {8,  2,  0,  9,  3, 11, 13, 14, 10,  3},
			 {19, 13, 16, 11,  6,  0, 16,  4,  0, 15},
			 {11,  7,  0, 19,  4,  0,  1,  9,  3,  0},
			 {7, 14, 19, 16,  0,  0,  0,  0,  0,  0},
			 {2,  2,  6,  0,  9, 13,  5, 18, 19,  6},
			 {19, 13,  0,  0,  6,  0,  3, 19, 12,  0},
			 {9,  6, 17,  0,  0, 10, 15,  0, 12,  0},
			 {6,  4,  3,  8, 19,  0,  1,  1,  0, 15},
			 {17,  0, 18,  4, 19,  6,  0,  2,  1,  9},
			 {8, 10,  0, 19, 13, 14,  7, 16, 13,  0},
			 {0,  7, 18, 17,  1, 15,  0,  8, 10,  5},
			 {19,  8, 11,  0,  7,  3,  0,  1, 11,  6},
			 {0, 10,  6, 18,  0,  0,  4, 16, 14,  0} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"F*______#_",
				  "_*_______#",
				  "_*#_______",
				  "_*___#__#_",
				  "_*#__#___#",
				  "_*__######",
				  "_*_#______",
				  "_*##_#___#",
				  "_*_##__#_#",
				  "_***_#__#_",
				  "_#_***#___",
				  "__#__***_#",
				  "#_____#*__",
				  "___#__#***",
				  "#___##___S"
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
	// Tests maze solver with 30x10.
	@Test
	public void testMazeSolver_Solution_30x10() {
		String[] mazeString = new String[] { 
				"S____#_#_#",
				   "#_________",
				   "______#_##",
				   "#____#____",
				   "##_#______",
				   "_##___#__#",
				   "_#___##_##",
				   "#__##_#___",
				   "__________",
				   "__________",
				   "_#________",
				   "##________",
				   "#_#_#__#__",
				   "____##_#_#",
				   "_____#__#_",
				   "____#_#___",
				   "__________",
				   "___#_###__",
				   "__________",
				   "_##__#____",
				   "_____##__#",
				   "__#_#__###",
				   "____#_____",
				   "___#_____#",
				   "#__#__#___",
				   "__##___#__",
				   "________##",
				   "##__#___#_",
				   "__#___#___",
				   "__#______F"
				};
		int[][] costArray = new int[][] {	
			{0, 12,  7, 16,  9,  0, 13,  0, 19,  0},
			 {0,  5, 18,  3, 19,  7, 18,  1,  9, 19},
			 {15, 11, 11,  8,  8,  4,  0,  8,  0,  0},
			 {0, 14, 12, 10,  4,  0, 15, 14,  9, 11},
			 {0,  0,  5,  0, 13, 10,  2,  8, 15, 14},
			 {16,  0,  0, 12, 12,  1,  0,  4,  5,  0},
			 {3,  0, 11, 13,  9,  0,  0, 10,  0,  0},
			 {0, 10, 13,  0,  0,  6,  0, 10, 15,  8},
			 {1,  7,  3,  9,  6,  6, 10,  4,  5, 16},
			 {10,  4,  6, 15, 15,  2, 17, 19,  6,  6},
			 {1,  0,  3, 14, 19,  6, 14, 11, 19, 17},
			 {0,  0, 19,  1,  2, 15,  9,  4, 11, 14},
			 {0,  9,  0,  2,  0,  1,  6,  0, 16, 12},
			 {11,  1,  3,  1,  0,  0,  7,  0, 14,  0},
			 {1, 19,  4, 11, 13,  0,  8, 10,  0,  4},
			 {9,  1, 10,  5,  0,  5,  0, 16,  2, 18},
			 {19, 12, 17, 10,  9,  8, 11,  8,  9, 13},
			 {5,  6,  2,  0, 13,  0,  0,  0, 19,  2},
			 {9, 12,  7,  7,  8, 10, 12, 15,  5,  5},
			 {8,  0,  0, 15, 11,  0, 12,  8,  9, 18},
			 {9,  2,  5,  4, 12,  0,  0,  5, 11,  0},
			 {3, 17,  0, 18,  0, 19,  5,  0,  0,  0},
			 {12, 19,  6, 12,  0,  8,  1,  7,  7,  8},
			 {12, 10, 15,  0,  7, 18,  6, 16,  9,  0},
			 {0, 18,  3,  0, 13, 14,  0, 19, 13, 16},
			 {11,  13,  0,  0, 19, 15, 10,  0,  2, 18},
			 {1,  1, 18,  5, 15,  7, 10, 11,  0,  0},
			 {0,  0, 15, 19,  0,  4, 17,  8,  0, 10},
			 {14, 11,  0,  2,  8, 15,  0,  8, 10,  9},
			 {11,  3,  0,  5, 18, 16,  2, 16, 16, 13} };
		Maze m = new Maze(mazeString, costArray);
		String[] queueExpected = { 
				"S***_#_#_#",
				  "#__*______",
				  "___**_#_##",
				  "#___*#____",
				  "##_#*_____",
				  "_##_*_#__#",
				  "_#***##_##",
				  "#_*##_#___",
				  "__*_______",
				  "__*_______",
				  "_#**______",
				  "##_*______",
				  "#_#*#__#__",
				  "__**##_#_#",
				  "__*__#__#_",
				  "_**_#_#___",
				  "_*________",
				  "**_#_###__",
				  "*_________",
				  "*##__#____",
				  "*____##__#",
				  "*_#_#__###",
				  "*___#_____",
				  "**_#_____#",
				  "#*_#__#___",
				  "_*##___#__",
				  "_*******##",
				  "##__#__*#_",
				  "__#___#***",
				  "__#______F"
				};
		checkMaze((PriorityQueue<Integer, Square>) new Heap<Integer, Square>(new IntComparator()), m, queueExpected);
	}
}