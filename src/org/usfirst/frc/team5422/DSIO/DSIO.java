package org.usfirst.frc.team5422.DSIO;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class DSIO {
	//This classes controls the I/O of the driver station, basically the back-end of the user interface
	//It takes in inputs such as button presses and the x and y of the joystick
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

		if (buttonBoard.getRawButton(buttonID) == true) {
			//TODO create commands for each operation (i.e. if button id is 0, shoot the ball high; 1.. shoot the ball low, etc
		}
		return buttonPressed;
	}

	//Inputs: nothing
	//Outputs: x and y of joystick (raw)
	public double driveLinearX() {
		double xPos = joystick.getX();

		return xPos;
	}

	public double driveLinearY() {
		double yPos = joystick.getY();

		return yPos;
	}


	//Inputs: nothing
	//Outputs: averaged x and y of joystick over a period of 15 milliseconds
	//Sample size (length of x and y arrays) may need to be changed depending on how much you want the vals to be dampened
	public double dampenAvgX() {		
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

		return xPosAvg;
	}

	public double dampenAvgY() {		
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

		return xFinal;
	}

	public double dampenWithCurveY() {
		double yPos = joystick.getY();

		double yFinal;

		//Put the y value (fwd, back) through a curved graph (quadratic function)
		yFinal = yPos * yPos;
		
		if (yPos < 0) yFinal *= -1;

		return yFinal;
	}
	
	//Inputs: what to print out
	//Outputs: nothing
	//If the integer of the specified thing is 1, display it; otherwise, don't display it
	public void outputToSFX(int leftSpeed, int rightSpeed, int xPosition, int yPosition, int theta, int battVoltage) {
		//TODO output the numbers based on which ones selected
	}
}

