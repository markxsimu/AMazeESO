package falstad; //Done

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.Distance;

/**
 * The WallFollower Class implements the RobotDriver interface, the WallFollower object is created inside the MazeController file before the 
 * BasicRobot is passed to it. The wallFollower algorithm is designed to keep the robot glued to the wall on its left side and follow it to the
 * exit location. The only possible problem with this algorithm is it starts in a room that causes the function follow a wall that is an island and is
 * disconnected from the rest of the maze causing the algorithm to loop until it runs out of battery.
 * 
 * Collaborators: RobotDriver, BasicRobot, and MazeController
 * 
 * @authors Chris Wolinski & Marcelino Dayrit
 *
 */
public class WallFollower implements RobotDriver {
	private int[][] mazeBoard;
	private BasicRobot basic;
	private int pathLength=0;
	private int width;
	private int height;
	private Distance dist;
	private float Battery;
	private Robot robot;
	private MazeController controller;
	
	public WallFollower(MazeController maze) {
		setMazeBoard(null);
		setRealRobot(null);
		setDist(null);
		pathLength = 0;
		this.controller = maze;
	}

	@Override
	public void setRobot(Robot r) {
		assert r != null;
		setRealRobot(r);
		setBattery(getRealRobot().getBatteryLevel());
	}

	@Override
	public void setDimensions(int width, int height) {
		this.setHeight(height);
		this.setWidth(width);
		setMazeBoard(new int[width][height]);
	}

	@Override
	public void setDistance(Distance distance) {
		this.setDist(distance);	
	}

	@Override
	public boolean drive2Exit() throws Exception {

		controller.showMaze = true;
		controller.showSolution = true;
		controller.mapMode = true;
		
		while(!getRealRobot().isAtExit()) {
			if (getRealRobot().getBatteryLevel() == 0) {
				return false;
			}
			if (getRealRobot().distanceToObstacle(Direction.FORWARD) > 0 && getRealRobot().distanceToObstacle(Direction.LEFT) == 0) {
				move();
			}
			else {
				 if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
					 turnLeftMove();
				 }
			    else if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
			    	turnRightMove();
			    }
			    else {
					turnAroundMove();
			    }
			}
		}
		checkForExit();
		return true;
	}
	/**
	 *checkForExit() is designed to check all 4 directions and see if the robot is next to the maze exit
	 *if it is then it calls setEndGame().
	 */
	void checkForExit() throws Exception {
		if (getRealRobot().canSeeExit(Direction.FORWARD)) {
			setEndGame();
		}
		else if (getRealRobot().canSeeExit(Direction.BACKWARD)) {
			setEndGame();
		}
		else if (getRealRobot().canSeeExit(Direction.RIGHT)) {
			setEndGame();
		}
		else if (getRealRobot().canSeeExit(Direction.LEFT)) {
			setEndGame();
		}
	}
	/**
	 *move() is designed to move the robot forward one cell. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	private void move() throws Exception {
		getRealRobot().move(1, false);
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *turnLeftMove() is designed to turn the robot left and move the robot forward one cell. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	void turnLeftMove() throws Exception {
		getRealRobot().rotate(Turn.LEFT);
		getRealRobot().move(1, false);
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *turnRightMove() is designed to turn the robot right and move the robot forward one cell. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	void turnRightMove() throws Exception {
		getRealRobot().rotate(Turn.RIGHT);
		getRealRobot().move(1, false);
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *turnAroundMove() is designed to turn the robot around and move the robot forward one cell. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	private void turnAroundMove() throws Exception {
		getRealRobot().rotate(Turn.AROUND);
		getRealRobot().move(1, false);
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *setEndGame() is designed to change the maze's state to end game then to call the notifyViewerRedraw().
	 */
	private void setEndGame() throws Exception {
		getRealRobot().isAtExit();
		controller.state = Constants.StateGUI.STATE_FINISH;
		controller.notifyViewerRedraw();
	}
	
	@Override
	public float getEnergyConsumption() {
		return 3000 - getRealRobot().getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		return pathLength;
	}

	public Robot getRealRobot() {
		return robot;
	}

	public void setRealRobot(Robot robot) {
		this.robot = robot;
	}

	public float getBattery() {
		return Battery;
	}

	public void setBattery(float battery) {
		Battery = battery;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int[][] getMazeBoard() {
		return mazeBoard;
	}

	public void setMazeBoard(int[][] mazeBoard) {
		this.mazeBoard = mazeBoard;
	}

	public Distance getDist() {
		return dist;
	}

	public void setDist(Distance dist) {
		this.dist = dist;
	}

}