package falstad;//Done

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;

/**
 * The Wizard Class implements the RobotDriver interface, the Wizard object is created inside the MazeController file before the 
 * BasicRobot is passed to it. The wizard algorithm is designed to keep the robot moving into the next position that is closer to 
 * the exit when compared to its current position. The wizard algorithm follows the show solution line to the exit. It is known as 
 * the most effective algorithm due to its ability to cheat.
 * 
 * Collaborators: RobotDriver, BasicRobot, and MazeController
 * 
 * @authors Chris Wolinski & Marcelino Dayrit
 *
 */
public class Wizard implements RobotDriver{
	private int[][] mazeBoard;
	private BasicRobot basic;
	private int pathLength=0;
	private int width;
	private int height;
	private Distance dist;
	private float Battery;
	private Robot robot;
	private MazeController controller;
	
	public Wizard(MazeController maze) {
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
			if(getRealRobot().distanceToObstacle(Direction.FORWARD) == 0 && getRealRobot().distanceToObstacle(Direction.LEFT) == 0 && getRealRobot().distanceToObstacle(Direction.RIGHT) == 0){
				turnAround();
			}
			if (getRealRobot().distanceToObstacle(Direction.FORWARD) > 0) {
				int x = controller.getCurrentPosition()[0];
				int y = controller.getCurrentPosition()[1];
				CardinalDirection z = controller.getCurrentDirection();
				switch (z) {
				case East:
					int curDist = getDist().getDistance(x,y);
					if (getRealRobot().isAtExit()){
						checkForExit();
						break;
					}
					int FowDist = getDist().getDistance(x+1,y);
					if (curDist > FowDist) {
						 move();
					}
					else if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
						wizardLeft();
						if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
							wizardRight();
						}
					}
					else if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
						wizardRight();
						if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
							wizardLeft();
						}
					}
					break;
				case South:
					 curDist = getDist().getDistance(x,y);
					 if (getRealRobot().isAtExit()){
							checkForExit();
							break;
						}
					 FowDist = getDist().getDistance(x,y+1);
					if (curDist > FowDist) {
						 move();
					}
					else if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
						wizardLeft();
						if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
							wizardRight();
						}
					}
					else if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
						wizardRight();
						if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
							wizardLeft();
						}
					}
					break;
				case North:
					 curDist = getDist().getDistance(x,y);
					 if (getRealRobot().isAtExit()){
							checkForExit();
							break;
						}
					 FowDist = getDist().getDistance(x,y-1);
					if (curDist > FowDist) {
						 move();
					}
					else if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
						wizardLeft();
						if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
							wizardRight();
						}
					}
					else if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
						wizardRight();
						if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
							wizardLeft();
						}
					}
					break;
				case West:
					 curDist = getDist().getDistance(x,y);
					 if (getRealRobot().isAtExit()){
							checkForExit();
							break;
						}
					 FowDist = getDist().getDistance(x-1,y);
					if (curDist > FowDist) {
						 move();
					}
					else if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
						wizardLeft();
						if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
							wizardRight();
						}
					}
					else if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
						wizardRight();
						if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
							wizardLeft();
						}
					}
					break;
				default:
					break;
				}
			}
			else {
				 if (getRealRobot().distanceToObstacle(Direction.LEFT) > 0) {
					 wizardLeft();
				 }
			     if (getRealRobot().distanceToObstacle(Direction.RIGHT) > 0) {
			    	 wizardRight();
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
		if (getRealRobot().isAtExit()){
			checkForExit();
		}
		else {
		getRealRobot().move(1, false);
		}
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *turnRightMove() is designed to turn the robot right and move the robot forward one cell. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	void turnRightMove() throws Exception {
		getRealRobot().rotate(Turn.RIGHT);
		if (getRealRobot().isAtExit()){
			checkForExit();
		}
		else {
		getRealRobot().move(1, false);
		}
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *turnAround() is designed to turn the robot. 
	 *It then calls notifyViewerRedraw() and puts the thread to sleep.
	 */
	private void turnAround() throws Exception {
		getRealRobot().rotate(Turn.AROUND);
		controller.notifyViewerRedraw();
		Thread.sleep(250);
	}
	/**
	 *wizardRight() is designed to get the current position of the robot and the direction its facing
	 *and with that information it determines if turning right and moving will bring it closer to the exit.
	 *It makes this calculation by checking if the future location will have a lower distance number when 
	 *compared to the current location if it will it goes ahead and calls the turnRightMove().
	 */
	private void wizardRight() throws Exception {
		
		int x = controller.getCurrentPosition()[0];
		int y = controller.getCurrentPosition()[1];
		CardinalDirection z = controller.getCurrentDirection();
		switch (z) {
		case East:
			int curDist = getDist().getDistance(x,y);
			if (getRealRobot().isAtExit()){
				checkForExit();
				break;
			}
			int rightDist = getDist().getDistance(x,y-1);
			if (curDist > rightDist) {
				turnRightMove();
			}
			break;
		case South:
			 curDist = getDist().getDistance(x,y);
			 if (getRealRobot().isAtExit()){
					checkForExit();
					break;
				}
			 rightDist = getDist().getDistance(x+1,y);
			if (curDist > rightDist) {
				turnRightMove();
			}
			break;
		case North:
			 curDist = getDist().getDistance(x,y);
			 if (getRealRobot().isAtExit()){
					checkForExit();
					break;
				}
			 rightDist = getDist().getDistance(x-1,y);
			if (curDist > rightDist) {
				turnRightMove();
			}
			break;
		case West:
			 curDist = getDist().getDistance(x,y);
			 if (getRealRobot().isAtExit()){
					checkForExit();
					break;
				}
			 rightDist = getDist().getDistance(x,y+1);
			if (curDist > rightDist) {
				turnRightMove();
			}
			break;
		default:
			break;
		}
	}
	/**
	 *wizardLeft() is designed to get the current position of the robot and the direction its facing
	 *and with that information it determines if turning left and moving will bring it closer to the exit.
	 *It makes this calculation by checking if the future location will have a lower distance number when 
	 *compared to the current location if it will it goes ahead and calls the turnLeftMove().
	 */
	private void wizardLeft() throws Exception {
		int x = controller.getCurrentPosition()[0];
		int y = controller.getCurrentPosition()[1];
		CardinalDirection z = controller.getCurrentDirection();
		switch (z) {
		case East:
			int curDist = getDist().getDistance(x,y);
			if (getRealRobot().isAtExit()){
				checkForExit();
				break;
			}
			int leftDist = getDist().getDistance(x,y+1);
			if (curDist > leftDist) {
				 turnLeftMove();
			}
			break;
		case South:
			 curDist = getDist().getDistance(x,y);
			 if (getRealRobot().isAtExit()){
					checkForExit();
					break;
				}
			 leftDist = getDist().getDistance(x-1,y);
			if (curDist > leftDist) {
				turnLeftMove();
			}
			break;
		case North:
			 curDist = getDist().getDistance(x,y);
			 if (getRealRobot().isAtExit()){
					checkForExit();
					break;
				}
			 leftDist = getDist().getDistance(x+1,y);
			if (curDist > leftDist) {
				turnLeftMove();
			}
			break;
		case West:
			 curDist = getDist().getDistance(x,y);
			 if (getRealRobot().isAtExit()){
					checkForExit();
					break;
				}
			 leftDist = getDist().getDistance(x,y-1);
			if (curDist > leftDist) {
				turnLeftMove();
			}
			break;
		default:
			break;
		}
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