package falstad;

import generation.Distance;
import falstad.MazeController.UserInput;
import falstad.Robot.Turn;

/**
 * The ManualDriver Class implements the RobotDriver interface, the ManualDriver object is created inside the MazeApplication.java file before the 
 *  BasicRobot.java object is created.  the maze controller is created inside ManualDriver in order to pass it into the 
 *  SimpleKeyListener so that it can redirect the necessary commands to manualDriver instead of mazeController.
 *  
 *   The ManualDriver Class is responsible for taking the commands passed to it from SimpleKeyListener.java and pushing them 
 *  through the klKeys method. The klKeys Method is a switch that takes the key input and passes it to the corresponding functions inside the 
 *  ManualDriver Class. The possible functions that can be called through klKeys are: moveRobot, which calls the robot object and activates the move 
 *  function from BasicRobot, rotateRobotRight, which calls the robot object and activates the rotate right function from BasicRobot, 
 *  rotateLeftRight, which calls the robot object and activates the rotate left function from BasicRobot, The turnRobotAround method is 
 *  saved for the auto drivers. SetRobot sets the robot inside of MazeController.java. We do not currently use the setDistance, 
 *  setDimensions, getPathLength, getEnergyConsumption, drive2Exit methods. They are not needed for the driving the maze manually.
 * 
 * Collaborators: SimpleKeyListener, RobotDriver, ManualDriver, MazeApplication
 * 
 * @authors Chris Wolinski & Marcelino Dayrit
 *
 */
public class ManualDriver implements RobotDriver{
	public int[][] mazeBoard;
	public BasicRobot basic;
	public int pathLength=0;
	public int width;
	public int height;
	public Distance dist;
	public float Battery;
	public Robot robot;

	public MazeController controller;
	public WallFollower wallfollower;
	
	
	public ManualDriver(){
		
	}
	/**
	 * Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, this method provides it with this necessary information.

	 */
	public void klKeys(MazeController.UserInput kl) {
		assert kl != null;
		switch (kl) {

		case Up: 
			moveRobot();
			break;
		case Left:
			rotateRobotLeft();
			break;
		case Right:
			rotateRobotRight();
			break;
		default:
			break;
	  }
	}
	public void setDriverType(int choice) {
		switch(choice) {
		case 1:
			break;

		case 2:
			break;

		case 3:
			WallFollower wallfollower= new WallFollower(this.controller);
			wallfollower.setRobot(robot);			
			try {
				wallfollower.drive2Exit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			break;
		default:
			break;
		}
	}
	@Override
	public void setRobot(Robot r) {
		assert r != null;
		robot= r;
		Battery = robot.getBatteryLevel();
	
	}
	@Override
	public void setDimensions(int width, int height) {
		this.height = height;
		this.width = width;
		mazeBoard = new int[width][height];

		
	}
	@Override
	public void setDistance(Distance distance) {
		this.dist = distance;
		
	}
	@Override
	public boolean drive2Exit() throws Exception {
		if(robot.hasStopped()) {
			throw new Exception();
		}
		else if(robot.isAtExit()){
			return true;
		}
		else {
			return false;
		}	
	}
	@Override
	public float getEnergyConsumption() {
		return 5;
	}
	@Override
	public int getPathLength() {

		return this.pathLength;
	}
	/**
	 * MoveRobot is called inside the public void klKeys() switch statement, it calls the basicRobots
	 * move function to alter the maze that the robot is currently in and move 1 position forward. 
	 */
	public void moveRobot() {
		robot.move(1, true);
		this.pathLength++;
	}
	/**
	 * RotateRobotRight is called inside the public void klKeys() switch statement, it calls the basicRobots
	 * rotate function to alter the maze that the robot is currently in and rotate right. 
	 */
	public void rotateRobotRight() {
		robot.rotate(Turn.RIGHT);
		this.pathLength++;
	}
	/**
	 * RotateRobotLeft is called inside the public void klKeys() switch statement, it calls the basicRobots
	 * rotate function to alter the maze that the robot is currently in and rotate left. 
	 */
	public void rotateRobotLeft() {
		robot.rotate(Turn.LEFT);
		this.pathLength++;
	}
	/**
	 * TurnRobotAround is not currently called inside the public void klKeys() switch statement, we are saving it for the auto drivers,
	 *  it calls the basicRobots move function to alter the maze that the robot is currently in.. 
	 */
	public void turnRobotAround() {
		robot.rotate(Turn.AROUND);
		this.pathLength++;
	}
}
