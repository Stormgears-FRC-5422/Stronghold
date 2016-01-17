package org.usfirst.frc.team5422.DSIO;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class DSIO {
	//This class controls the I/O of the driver station, basically the back-end of the user interface
	//It takes in inputs such as button presses and the x and y of the joystick; does not drive anything
	//Depending on the method used, it outputs either a boolean or an x and y value that should be passed on to another class

	//Constants may need to be changed

	Joystick joystick;
	Joystick buttonBoard;
	boolean buttonPressed;

	//Constructor
	public DSIO(int joyStickChannel, int buttonBoardChannel) {
		joystick = new Joystick(joyStickChannel);
		buttonBoard = new Joystick(buttonBoardChannel);
	}

	//Check if a button is pressed; if it is, do the respective command
	public boolean getButton(int buttonID) {
		JoystickButton button = new JoystickButton (buttonBoard, buttonID);

		if (buttonBoard.getRawButton(buttonID)) {
			//TODO create commands for each operation (i.e. if button id is 0, shoot the ball high; 1.. shoot the ball low, etc
		}
		return buttonPressed;
	}

	//Inputs: nothing
	//Outputs: x and y of joystick (raw)
	public double getLinearX() {
		double xPos = joystick.getX();
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (xPos >= -0.2 & xPos <= 0.2) xPos = 0;
		
		return xPos;
	}

	public double getLinearY() {
		double yPos = joystick.getY();
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

		return yPos;
	}


	//Inputs: nothing
	//Outputs: averaged x and y of joystick over a period of 15 milliseconds
	//Sample size (length of x and y arrays) may need to be changed depending on how much you want the vals to be dampened
	public double dampenWithAvgX() {		
		double xPos[] = new double[15];

		double xPosAvg = 0;

		for (int c = 0; c < xPos.length; c++) {
			xPos[c] = joystick.getX();

			xPosAvg += xPos[c];

			//Wait 1 millisecond
			try {
				Thread.sleep(1);
			}
			catch (Exception e) {
				System.out.println("Couldn't sleep the thread.");
			}
		}

		//Avg the positions from the total calculated in above for loop
		xPosAvg /= 15;
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (xPosAvg >= -0.2 & xPosAvg <= 0.2) xPosAvg = 0;

		return xPosAvg;
	}

	public double dampenWithAvgY() {		
		double yPos[] = new double[15];

		double yPosAvg = 0;

		for (int c = 0; c < yPos.length; c++) {
			yPos[c] = joystick.getY();

			yPosAvg += yPos[c];

			//Wait 1 millisecond
			try {
				Thread.sleep(1);
			}
			catch (Exception e) {
				System.out.println("Couldn't sleep the thread.");
			}
		}

		//Avg the positions from the total calculated in above for loop
		yPosAvg /= 15;
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (yPosAvg >= -0.2 & yPosAvg <= 0.2) yPosAvg = 0;

		return yPosAvg;
	}

	//Inputs: nothing
	//Outputs: x and y of joystick after going through a x^2 graph
	public double dampenWithCurveX() {
		double xPos = joystick.getX();

		double xFinal;

		//Put the y value (fwd, back) through a curved graph (quadratic function)
		xFinal = xPos * xPos;
		
		if (xPos < 0) xFinal *= -1;
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (xPos >= -0.2 & xPos <= 0.2) xPos = 0;

		return xFinal;
	}

	public double dampenWithCurveY() {
		double yPos = joystick.getY();

		double yFinal;

		//Put the y value (fwd, back) through a curved graph (quadratic function)
		yFinal = yPos * yPos;
		
		if (yPos < 0) yFinal *= -1;
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

		return yFinal;
	}
	
	//Inputs: what to print out
	//Outputs: nothing
	//If the integer of the specified thing is true, display it; otherwise, don't display it
	public void outputToSFX(boolean leftSpeed, boolean rightSpeed, boolean xPosition, boolean yPosition, boolean theta, boolean battVoltage) {
		//TODO output the numbers based on which ones selected
	}
}

