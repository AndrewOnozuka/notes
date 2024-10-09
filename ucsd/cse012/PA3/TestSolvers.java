import java.util.ArrayList;

/*
 * Write your JUnit tests here
 * Use the formatMaze() method to get nicer JUnit output
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSolvers {

	/* Helper method to compare two mazes */
	public void checkMaze(SearchWorklist wl, Maze startMaze, String[] expected) {
		Square s = MazeSolver.solve(startMaze, wl);
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
	
	@Test
	public void dfs() {
		Maze m = new Maze(new String[] {
	            "#___",
	            "__F_",
	            "S##_",
	            "____"
	    });
		SearchWorklist stack = new StackWorklist();
		String[] expected = {
				"#___",
				"__F*",
				"S##*",
				"****"
		};
		checkMaze(stack, m, expected);
	}
	@Test
	public void bfs() {
		Maze m = new Maze(new String[] {
	            "#___",
	            "__F_",
	            "S##_",
	            "____"
	    });
		SearchWorklist queue = new QueueWorklist();
		String[] expected = {
				"#___",
				"**F_",
				"S##_",
				"____"
		};
		checkMaze(queue, m, expected);
	}
	@Test
	public void stackSolveDiagonalMoves() {
		Maze m = new Maze(new String[] {
	            "#___",
	            "_#F_",
	            "S_#_",
	            "____"
	    });
		SearchWorklist stack = new StackWorklist();
		String[] expected = {
				"#___",
				"_#F*",
				"S*#*",
				"_***"
		};
		checkMaze(stack, m, expected);
	}
	@Test
	public void queueSolveDiagonalMoves() {
		Maze m = new Maze(new String[] {
	            "#___",
	            "_#F_",
	            "S_#_",
	            "____"
	    });
		SearchWorklist queue = new QueueWorklist();
		String[] expected = {
				"#___",
				"_#F*",
				"S_#*",
				"****"
		};
		checkMaze(queue, m, expected);
	}
//	@Test
//	public void chaffSolveStopEarly() {
//		Maze m = new Maze(new String[] {
//	            "#___",
//	            "__F_",
//	            "S##_",
//	            "____"
//	    });
//		SearchWorklist stack = new StackWorklist();
//		// TODO
//	}
//	@Test
//	public void chaffsolve_difforder() {
//		// TODO
//	}
	@Test
	public void stackHasNoWalls() {
		Maze m = new Maze(new String[] {
	            "____",
	            "__F_",
	            "S___",
	            "____"
	    });
		SearchWorklist stack = new StackWorklist();
		String[] expected = {
				"____",
				"__F_",
				"S**_",
				"____"
		};
		checkMaze(stack, m, expected);
	}
	@Test
	public void queueHasNoWalls() {
		Maze m = new Maze(new String[] {
	            "____",
	            "__F_",
	            "S___",
	            "____"
	    });
		SearchWorklist queue = new QueueWorklist();
		String[] expected = {
				"____",
				"**F_",
				"S___",
				"____"
		};
		checkMaze(queue, m, expected);
	}
	@Test
	public void stackLotOfWalls() {
		Maze m = new Maze(new String[] {
		        "####",
		        "__F#",
		        "S__#",
		        "####"
		});
		SearchWorklist stack = new StackWorklist();
		String[] expected = {
				"####",
				"__F#",
				"S**#",
				"####"
		};
		checkMaze(stack, m, expected);
	}
	@Test
	public void queueLotOfWalls() {
		Maze m = new Maze(new String[] {
		        "####",
		        "__F#",
		        "S__#",
		        "####"
		});
		SearchWorklist queue = new QueueWorklist();
		String[] expected = {
				"####",
				"**F#",
				"S__#",
				"####"
		};
		checkMaze(queue, m, expected);
	}
	@Test
	public void stackFinishNextToStart() {
		Maze m = new Maze(new String[] {
	            "____",
	            "_SF_",
	            "____",
	            "____"
	    });
		SearchWorklist stack = new StackWorklist();
		String[] expected = {
				"____",
				"_SF_",
				"____",
				"____"
		};
		checkMaze(stack, m, expected);
	}
	@Test
	public void queueFinishNextToStart() {
		Maze m = new Maze(new String[] {
		        "____",
		        "_SF_",
		        "____",
		        "____"
		});
		SearchWorklist queue = new QueueWorklist();
		String[] expected = {
				"____",
				"_SF_",
				"____",
				"____"
		};
		checkMaze(queue, m, expected);
	}
	@Test
	public void stackFinishAndStartCorners() {
		Maze m = new Maze(new String[] {
	            "___F",
	            "____",
	            "____",
	            "S___"
	    });
		SearchWorklist stack = new StackWorklist();
		String[] expected = {
				"___F",
				"___*",
				"___*",
				"S***"
		};
		checkMaze(stack, m, expected);
	}
	@Test
	public void queueFinishAndStartCorners() {
		Maze m = new Maze(new String[] {
		        "___F",
		        "____",
		        "____",
		        "S___"
		});
		SearchWorklist queue = new QueueWorklist();
		String[] expected = {
				"***F",
				"*___",
				"*___",
				"S___"
		};
		checkMaze(queue, m, expected);
	}
	@Test 
	public void mazeSolver()
	{
		Maze m = new Maze(new String[] {
	            "#___",
	            "__F_",
	            "S##_",
	            "____"
	        });
		SearchWorklist stack = new StackWorklist();
		assertEquals(m.finish, MazeSolver.solve(m, stack));
	}
}



