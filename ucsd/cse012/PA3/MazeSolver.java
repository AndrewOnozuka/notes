/* Class to implement a Maze solver */

public abstract class MazeSolver {
	
	public static Square solve(Maze maze, SearchWorklist wl) {
		Square firstElem = maze.start;
		wl.add(firstElem);
		firstElem.visit();
		while (!wl.isEmpty()) {
			Square current = wl.remove();
			if (current.equals(maze.finish)) {
				return current;
			} else {
				if (current.getRow()-1 >= 0 &&
					!maze.contents[current.getRow()-1][current.getCol()].getIsWall() &&
					!maze.contents[current.getRow()-1][current.getCol()].isVisited()) {
					
					maze.contents[current.getRow()-1][current.getCol()].visit();
					maze.contents[current.getRow()-1][current.getCol()].setPrevious(current);
					wl.add(maze.contents[current.getRow()-1][current.getCol()]);					
				}
				if (current.getRow()+1 < maze.contents.length &&
					!maze.contents[current.getRow()+1][current.getCol()].getIsWall() &&
					!maze.contents[current.getRow()+1][current.getCol()].isVisited()) {
					
					maze.contents[current.getRow()+1][current.getCol()].visit();
					maze.contents[current.getRow()+1][current.getCol()].setPrevious(current);
					wl.add(maze.contents[current.getRow()+1][current.getCol()]);
				}
				if (current.getCol()+1 < maze.contents[0].length &&
					!maze.contents[current.getRow()][current.getCol()+1].getIsWall() &&
					!maze.contents[current.getRow()][current.getCol()+1].isVisited()) {
					
					maze.contents[current.getRow()][current.getCol()+1].visit();
					maze.contents[current.getRow()][current.getCol()+1].setPrevious(current);
					wl.add(maze.contents[current.getRow()][current.getCol()+1]);
				}
				if (current.getCol()-1 >= 0 &&
					!maze.contents[current.getRow()][current.getCol()-1].getIsWall() &&
					!maze.contents[current.getRow()][current.getCol()-1].isVisited()) {
					
					maze.contents[current.getRow()][current.getCol()-1].visit();
					maze.contents[current.getRow()][current.getCol()-1].setPrevious(current);
					wl.add(maze.contents[current.getRow()][current.getCol()-1]);
				}
			}
		}
		return null;
	}
}
