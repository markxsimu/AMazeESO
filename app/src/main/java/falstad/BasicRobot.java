package falstad;

import falstad.MazeController.UserInput;
import generation.CardinalDirection;
import generation.Cells;
import generation.MazeConfiguration;

/**
 * The BasicRobot Class implements the Robot interface, the BasicRobot object called inside the MazeApplication.java file after the 
 *  ManualDriver.java object is created. The BasicRobot  setMaze is set in mazeController inside switch to switchToPlayingScreen() so that
 *  it receives the properties of a maze after it has been created. setMaze is then called again in setState under case STATE_GENERATING: so that
 *  it can be reset when we finish a game and decide to start over.
 *  
 *  The BasicRobot Class is responsible for taking the commands passed to it from ManualDriver.java and either rotating the robot 
 *  or moving the robot to a new cell location. The rotate method passes a keyDown(direction) call to the maze controller, which then  
 *  actually rotates the robot in the visual maze. The same applies to the move method. The distanceToObstacle Method is called inside of the move
 *  method in order to test to see if...  We do not currently use the isAtExit, CanSeeExit, IsInsideRoom, hasRoomSensor, getCurrentDirection, 
 *  getOdometerReading, resetOdometer, getEnergyForFullRotation, getEnergyForStepFoward methods. They are not needed for the driving the maze manually.
 * 
 * Collaborators: Robot, ManualDriver, MazeApplication, MazeController
 * 
 * @authors Chris Wolinski & Marcelino Dayrit
 *
 */
public class BasicRobot implements Robot{
	private static float battery;
	private boolean hasStopped;
	private MazeController control;
	private CardinalDirection curDir ;
	private boolean forwardSensor;
	private boolean backwardSensor ;
	private boolean leftSensor ;
	private boolean rightSensor;	
	private int odometer;
	private static int pathLength;
	private int curPos[];
	private Cells cells;
	
	public BasicRobot(){
		this.control = null;
		hasStopped = false;
		battery = 3000;
		setForwardSensor(true);
		setBackwardSensor(true);
		setLeftSensor(true);
		setRightSensor(true);	
		this.curPos = new int[2];
		pathLength = 0;
		this.curPos[0] = 0;
		this.curPos[1] = 0;
		setCurDir(CardinalDirection.East);
	}
	public void test() {
	}
	@Override
	public void rotate(Turn turn) {

		if(!hasStopped()) {
		switch(turn) {
		case LEFT:
			if(battery < 3) {
				hasStopped = true;	
				switchToExitScreen();
				break;
			}
			setCurDir(getCurDir().rotateClockwise());

			control.keyDown(UserInput.Left, 1);
			battery = battery-3;
			if (battery == 0) {
				hasStopped = true;
				switchToExitScreen();
			}
			break;
			
			
		case RIGHT:
			if(battery < 3) {
				hasStopped = true;	
				switchToExitScreen();
				break;
			}

			setCurDir(getCurDir().rotateCounterClockwise());
			control.keyDown(UserInput.Right, -1);
			battery = battery-3;
			if (battery == 0) {
				hasStopped = true;
				switchToExitScreen();
			}
			break;
			
		case AROUND:
			if(battery<6) {
				hasStopped = true;
				switchToExitScreen();
				break;
			}
			if(battery >= 6) {
				setCurDir(getCurDir().oppositeDirection());
				control.keyDown(UserInput.Left, 1);
				control.keyDown(UserInput.Left, 1);
				battery = battery - 6;
				if (battery == 0) {
					hasStopped = true;
					switchToExitScreen();
				}		

				break;
			}
		  }
		}	


				
	}
	@Override
	public void move(int distance, boolean manual) {

		while(distance > 0){
			
			if(battery<5){
				battery = 0;
				hasStopped = true;
				switchToExitScreen();
				break;
			}
			if(manual){
				distance = 1;
			}
			if(distanceToObstacle(Direction.FORWARD) > 0){
			
			switch(getCurDir()){
			
				case East:
					control.keyDown(UserInput.Up,1);
					battery = battery -5;
					distance--;
					pathLength++;
					break;
				case West:
					control.keyDown(UserInput.Up,1);
					battery = battery -5;
					distance--;
					pathLength++;
					break;
				case North:
					control.keyDown(UserInput.Up,1);
					battery = battery -5;
					distance--;
					pathLength++;
					break;
				case South:
					control.keyDown(UserInput.Up,1);
					battery = battery -5;
					distance--;
					pathLength++;
					break;
			  }
			if(battery <= 0) {
				hasStopped=true;
				switchToExitScreen();
				
			}
			}	
			else {
				control.setCurrentPosition(this.control.getCurrentPosition()[0],this.control.getCurrentPosition()[1]);
				battery = battery -5;
				distance--;			
				if(battery <= 0) {
					hasStopped=true;
					switchToExitScreen();
					
				}
			}
				
		}
	}
	@Override
	public int[] getCurrentPosition() throws Exception {
		return this.control.getCurrentPosition();
	}
	@Override
	public void setMaze(MazeController maze) {
		this.control = maze;
		curPos[0] = this.control.getCurrentPosition()[0];
		curPos[1] = this.control.getCurrentPosition()[1];	
		cells= new Cells(this.control.mazeConfig.getWidth(),this.control.mazeConfig.getHeight());
		cells = this.control.mazeConfig.getMazecells();
		setCurDir(CardinalDirection.East);
		pathLength = 0;
		battery = 3000;
	}
	@Override
	public boolean isAtExit() {
	    curPos = this.control.getCurrentPosition();
	    if(cells.isExitPosition(curPos[0], curPos[1])) {
	    	hasStopped = true;
	    }
	    return cells.isExitPosition(curPos[0], curPos[1]);
	}
	/**
	 * The switchToExitScreen method is usually called after hasStopped is changed to true in order to
	 * end the game and send the screen to the finish screen.
	 *
	 */
	public void switchToExitScreen() {
		if(hasStopped()) {
			this.control.switchToFinishScreen();
		}
		else {
			return;
		}
	}
	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		assert hasDistanceSensor(direction);
		if (!hasDistanceSensor(direction)) {
			throw new UnsupportedOperationException();
		}
		if (distanceToObstacle(direction) != Integer.MAX_VALUE) {
				return false;
		}	
		else {
				return true;
			}
	}
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		
		curPos = this.control.getCurrentPosition();
		
		return cells.isInRoom(curPos[0], curPos[1]);
		
	}
	@Override
	public boolean hasRoomSensor() {
		
		return true;
	}
	@Override
	public CardinalDirection getCurrentDirection() {
		
		return this.control.getCurrentDirection();
	}
	@Override
	public float getBatteryLevel() {
		return battery;
	}
	@Override
	public void setBatteryLevel(float level) {
		if(battery <=0) {
			hasStopped = true;
			switchToExitScreen();
		}
		battery = level;	
	}
	@Override
	public int getOdometerReading() {
		return odometer;
	}
	@Override
	public void resetOdometer() {
		odometer = 0;
	}
	@Override
	public float getEnergyForFullRotation() {
		return 12;
	}
	@Override
	public float getEnergyForStepForward() {
		return 5;
	}
	@Override
	public boolean hasStopped() {
		return hasStopped;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		assert hasDistanceSensor(direction);
		if(!hasDistanceSensor(direction)) {

				throw new UnsupportedOperationException();
		}
			int steps = 0;
			curPos = this.control.getCurrentPosition();
			int curX = this.control.getCurrentPosition()[0];
			int curY = this.control.getCurrentPosition()[1];
			if(battery>0){
				battery = battery - 1;
			}
			else {
				hasStopped = true;
				switchToExitScreen();
			}
			CardinalDirection SensorDirection;
			SensorDirection = getCurDir();
			
			switch(direction) {
			
			case LEFT:
				SensorDirection = getCurDir().rotateClockwise();
				break;
			case RIGHT:
				SensorDirection = getCurDir().rotateCounterClockwise();
				break;
			case BACKWARD:
				SensorDirection = getCurDir().oppositeDirection();
				break;
			case FORWARD:
				SensorDirection = getCurDir();
				break;		
			}

			int mWidth = this.control.mazeConfig.getWidth();
			int mHeight = this.control.mazeConfig.getHeight();
			
			while(true) {
				if (curX>= mWidth || curY>=mHeight || curX < 0 || curY<0){
					return Integer.MAX_VALUE;	
				}
				else {
				switch(SensorDirection){
				case East:
					if(cells.hasWall(curX, curY, CardinalDirection.East)){
						return steps;
					}
					curX++;
					break;
				case West:
					if(cells.hasWall(curX, curY, CardinalDirection.West)){
						return steps;
					}
					curX--;
					break;
				case South:
					if(cells.hasWall(curX, curY, CardinalDirection.South)){
						return steps;
					}
					curY++;
					break;
				case North:
					if(cells.hasWall(curX, curY, CardinalDirection.North)){
						return steps;
					}
					curY--;
					break;	

				}
				steps++;

			}	
		}		
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		if (direction == Direction.FORWARD)
			return isForwardSensor();
		else if (direction == Direction.BACKWARD)
			return isBackwardSensor();
		else if (direction == Direction.LEFT)
			return isLeftSensor();
		else if (direction == Direction.RIGHT)
			return isRightSensor();
		else
			return false;
	}
	/**
	 * getPathLength gets the pathlength for the Endscreen in MazeView.java
	 * @return pathlength
	 */
	public static int getPathLength() {
		return pathLength;
	}
	/**
	 * getBatteryLevelGame gets the battery left for the Endscreen in MazeView.java
	 * @return battery
	 */
	public static float getBatteryLevelGame() {
		return battery;
	}
	public boolean isForwardSensor() {
		return forwardSensor;
	}
	public void setForwardSensor(boolean forwardSensor) {
		this.forwardSensor = forwardSensor;
	}
	public boolean isBackwardSensor() {
		return backwardSensor;
	}
	public void setBackwardSensor(boolean backwardSensor) {
		this.backwardSensor = backwardSensor;
	}
	public boolean isLeftSensor() {
		return leftSensor;
	}
	public void setLeftSensor(boolean leftSensor) {
		this.leftSensor = leftSensor;
	}
	public boolean isRightSensor() {
		return rightSensor;
	}
	public void setRightSensor(boolean rightSensor) {
		this.rightSensor = rightSensor;
	}
	public CardinalDirection getCurDir() {
		return curDir;
	}
	public void setCurDir(CardinalDirection curDir) {
		this.curDir = curDir;
	}

}
