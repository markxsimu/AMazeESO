package falstad; //Done
import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.Distance;

/**
 * The Pledge Class implements the RobotDriver interface, the Pledge object is created inside the MazeController file before the 
 * BasicRobot is passed to it. The pledge algorithm is designed to keep the robot moving forward if the counter is equal to zero and glued to the wall 
 * on its right side if the counter is not equal to zero, the counter decrements by 1 if the robot takes a left turn and increments by 1 if the robot 
 * takes a right turn. The pledge algorithm bypasses the problem with the wallFollower algorithm because it is able to leave the possible obstacle created 
 * by rooms thanks to the counter.
 * 
 * Collaborators: RobotDriver, BasicRobot, and MazeController
 * 
 * @authors Chris Wolinski & Marcelino Dayrit
 *
 */
public class Pledge implements RobotDriver {
	private int[][] mazeBoard;
	private BasicRobot basic;
	private int pathLength=0;
	private int width;
	private int height;
	private Distance dist;
	private float Battery;
	private Robot robot;
	private MazeController controller;
	private int counter;
	
	public Pledge(MazeController maze) {
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
		counter = 0;
		
		while(!getRealRobot().isAtExit()) {
			Thread.sleep(300);
			if (getRealRobot().getBatteryLevel() == 0) {
				return false;
			}
			if (counter==0) {
				if (getRealRobot().distanceToObstacle(Direction.FORWARD) > 0 && counter == 0) {
					move();	
				}
				else if(getRealRobot().distanceToObstacle(Direction.FORWARD)==0) {
					turnLeft();				
				}
			}
			else if(counter!=0) {
				if(getRealRobot().distanceToObstacle(Direction.FORWARD)==0 &&getRealRobot().distanceToObstacle(Direction.RIGHT)==0){
					turnLeft();
				}
				else if(getRealRobot().distanceToObstacle(Direction.FORWARD)>0 &&getRealRobot().distanceToObstacle(Direction.RIGHT)==0) {
					move();
				}
				else if (getRealRobot().distanceToObstacle(Direction.RIGHT)>0) {
					turnRight();
					if(getRealRobot().distanceToObstacle(Direction.FORWARD)>0) {
						move();
					}	
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
		Thread.sleep(300);
	}
	/**
	 *turnLeft() is designed to turn the robot right and decrement the counter by 1. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	void turnLeft() throws Exception {
		getRealRobot().rotate(Turn.LEFT);
		controller.notifyViewerRedraw();
		Thread.sleep(300);
		counter -= 1;
	}
	/**
	 *turnRight() is designed to turn the robot right and increment the counter by 1. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	void turnRight() throws Exception {
		getRealRobot().rotate(Turn.RIGHT);
		controller.notifyViewerRedraw();
		Thread.sleep(300);
			counter += 1;
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

	public int[][] getMazeBoard() {
		return mazeBoard;
	}

	public void setMazeBoard(int[][] mazeBoard) {
		this.mazeBoard = mazeBoard;
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

	public Distance getDist() {
		return dist;
	}

	public void setDist(Distance dist) {
		this.dist = dist;
	}

}