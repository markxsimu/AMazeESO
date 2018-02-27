package generation;
/**
 * 
 * @author Chris Wolinski
 *
 */
public class MazeBuilderEller extends MazeBuilder implements Runnable {
	
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	/**
	 * This method is called from generate pathways. The method goes through the columns 
	 * as long as the column position is not in the last column then it goes ahead and
	 * randomly deletes a wall between 0 and the current column position. If the column
	 * pointer is in the last position then it deletes any wall there in order to make sure
	 * the sets are connected.
	 * @param set (array used to store current set location throughout the maze)
	 * @param y (used to point to the current column location)
	 * @param x (used to point to the current row location)
	 * @param wall (used to set the location that a wall will be deleted)
	 */	
	protected void verticalDirection(int set[][], int y, int x, Wall wall) {
		int randomNum = 0;
		y = 0;
		while(y != width)  {
			if(x == (height - 1))
				break;
			int y1 = y;	
			if(y == (width - 1)) { 
				wall = new Wall(y, x, CardinalDirection.South);
				cells.deleteWall(wall);
				break;
			}	
			while(true) {
				
				if(y == (width - 1))
					break;
				if(set[y][x] == set[y+1][x]) {
					y++;
					continue;
				}
				else break;
			}
			randomNum = random.nextIntWithinInterval(y1, y);
			wall = new Wall(randomNum, x, CardinalDirection.South);
			cells.deleteWall(wall);
			y++;		
			
		}
	}
	/**
	 * This method is called from generate pathways. The method goes row by row, 
	 * beginning from the top of the maze. Walls are then deleted horizontally using 
	 * the random number that is between 0 and 1, unless x is set to the last row, then it
	 * deletes any walls that could be preventing the maze from being connected in the
	 * same set space.
	 * @param set (array used to store current set location throughout the maze)
	 * @param y (used to point to the current column location)
	 * @param x (used to point to the current row location)
	 * @param wall (used to set the location that a wall will be deleted)
	 */	
		public void horizontalDirection(int set[][], int y, int x,  Wall wall) {
			
			int randomNum = 0;
			while(y != width) {
				if(x == (height - 1)) {
				 for(int i = 0 ; i < (width - 1) ; i++) {
				   if(set[i][x] != set[i+1][x])
					  wall = new Wall(i, x, CardinalDirection.East);
					  cells.deleteWall(wall);
					 }
					break;
					}				
				if(y != (width - 1)) {
					if(set[y][x] != set[y + 1][x]) {
						randomNum =  random.nextIntWithinInterval(0, 1);
						}
					else {
						 y++;
						 continue;
						 }
									}
				else{
					y++;
					continue;
					}
					if(randomNum == 0) {
						wall = new Wall(y, x, CardinalDirection.East);
						cells.deleteWall(wall);
						set[y+1][x] = set[y][x];
							}
							else y++;
						}
		}
		
		/**
		 * This method is used to generate pathways inside the maze. It creates a array
		 * named set that stores the current width and height of the maze. The x variable 
		 * relates to current row pointer and the y variable relates to the current column 
		 * pointer. The z variable stores cells that have walls above their current position
		 * and stores them for shifting down a row. The method uses the array to go through
		 * the maze and as it is going through the rows and columns it calls upon Horizontal 
		 * Direction and Vertical Direction to delete walls. After the 2 methods are called
		 * the algorithm increments row and starts the process over.
		 * Generate Pathways overrides MazeBuilder.java's generate pathways. 
		 */		
	@Override
	protected void generatePathways() {
		int[][] set = new int[width][height];
		int x = 0;
		int y = 0;
		int z = width;
		int i = 0;
		Wall wall = null;
		
		while(x != height) {
			if(x == 0) {
				//number the walls for every spot in the row 
				for(i = 0; i < width ; i++)
					set[i][0] = i;
			}
			//if there is already a numbered wall present
			else {		
				//for every number spotted 
				for(i = 0 ; i < width ; i++) {
					//if the wall present is north 
					if(cells.hasWall(i, x, CardinalDirection.North)) 
						set[i][x] = z++;
					else
						set[i][x] = set[i][x - 1];
				}
			}
		horizontalDirection(set, y, x, wall);
		verticalDirection(set, y, x, wall);
		x++;
		y = 0;
	}	
  }
}