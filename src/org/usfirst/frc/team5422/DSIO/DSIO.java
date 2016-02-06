package org.usfirst.frc.team5422.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DSIO {
	//This class controls the I/O of the driver station, basically the back-end of the user interface
	//It takes in inputs such as button presses and the x and y of the joystick; does not drive anything
	//Depending on the method used, it outputs either a boolean or an x and y value that should be passed on to another class

	//Constants may need to be changed

	static Joystick joystick;
	static Joystick buttonBoard;
	static boolean buttonPressed;
	
	public static int pos[] = new int[5];
	public static int numMoves;
	
	public static String autoSequence;
	
	private static SendableChooser defenseChooser, shootChooser, endChooser; 

	//Constructor
	public DSIO(int joyStickChannel, int buttonBoardChannel) {
		joystick = new Joystick(joyStickChannel);
		buttonBoard = new Joystick(buttonBoardChannel);
	}
	
	//Check if a button is pressed; if it is, do the respective command
	public static boolean getButton(int buttonID) {
		JoystickButton button = new JoystickButton (buttonBoard, buttonID);

		if (buttonBoard.getRawButton(buttonID)) {
			//TODO commands for each operation (i.e. if button id is 0, shoot the ball high; 1.. shoot the ball low, etc
		}
		return buttonPressed;
	}

	//Inputs: nothing
	//Outputs: x and y of joystick (raw) respectively 
	//Linear joystick position recieving methods
	public static double getLinearX() {
		double xPos = joystick.getX();
		
		//Put a nullzone on values that are between -0.2 and 0.2
		if (xPos >= -0.2 & xPos <= 0.2) xPos = 0;

		return xPos;
	}

	public static double getLinearY() {
		double yPos = joystick.getY();

		//Put a nullzone on values that are between -0.2 and 0.2
		if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

		return yPos;
	}


	//Inputs: nothing
	//Outputs: averaged x and y of joystick over a period of 15 milliseconds respectively
	//Sample size (length of x and y arrays) may need to be changed depending on how much you want the vals to be dampened
	public static double dampenWithAvgX() {		
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

	public static double dampenWithAvgY() {		
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
	//Outputs: x and y of joystick after going through a x^2 graph respectively
	public static double dampenWithCurveX() {
		double xPos = joystick.getX();
		double yPos = joystick.getY();

		double xFinal;

		//Put the x (left, right) through a curved graph (quadratic function)
		xFinal = xPos * xPos;

		//If x is negative, reverse the direction of the graph
		if (xPos < 0) xFinal *= -1;

		//Put a nullzone on values that are between -0.2 and 0.2
		if (xPos >= -0.2 & xPos <= 0.2) xPos = 0;
		if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

		return xFinal;
	}

	public static double dampenWithCurveY() {
		double yPos = joystick.getY();

		double yFinal;

		//Put the y value (fwd, back) through a curved graph (quadratic function)
		yFinal = yPos * yPos;

		//If y is negative, reverse the direction of the graph
		if (yPos < 0) yFinal *= -1;

		//Put a nullzone on values that are between -0.2 and 0.2
		if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

		return yFinal;
	}

	//Inputs: what to print out
	//Outputs: nothing
	//Wrapper method for putNumber()
	public static void outputToSFX(String label, double value) {
		SmartDashboard.putNumber(label, value);
	}
	
	public static void createAutonomousUI() {
		NetworkTable.initialize();
		autoSequence = "null";
		
		//Create defense chooser
		defenseChooser = new SendableChooser();
		defenseChooser.addDefault("Low Bar", Integer.valueOf(0));
		defenseChooser.addObject("Portcullis", Integer.valueOf(1));
		defenseChooser.addObject("Chival de Frise", Integer.valueOf(2));
		defenseChooser.addObject("Moat", Integer.valueOf(3));
		defenseChooser.addObject("Ramparts", Integer.valueOf(4));
		defenseChooser.addObject("Drawbridge", Integer.valueOf(5));
		defenseChooser.addObject("Sallyport", Integer.valueOf(6));
		defenseChooser.addObject("Rock Wall", Integer.valueOf(7));
		defenseChooser.addObject("Rough Terrain", Integer.valueOf(8));
		defenseChooser.addObject("Do nothing in Auto.", Integer.valueOf(-1));
		SmartDashboard.putData("Defense to cross chooser", defenseChooser);
		
		//Create shoot chooser
		shootChooser = new SendableChooser();
		shootChooser.addDefault("Shoot High Left Goal", "SHL");
		shootChooser.addObject("Shoot High Center Goal", "SHC");
		shootChooser.addObject("Shoot High Right Goal", "SHR");
		shootChooser.addObject("Shoot Low Left Goal", "SLL");
		shootChooser.addObject("Shoot Low Right Goal", "SLR");
		shootChooser.addObject("Stay in place.", "NNN");
		SmartDashboard.putData("Shoot chooser", shootChooser);
		
		//Create last move chooser
		endChooser = new SendableChooser();
		endChooser.addDefault("Go to teleop starting position.", "TTT");
		endChooser.addObject("Stay in place.", "NNN");
		SmartDashboard.putData("End chooser", endChooser);
		
		//Defense at position 0 is always low bar (ID 0)
		pos[0] = 0;
		
		//Add other defense position text boxes
		SmartDashboard.putNumber("Defense at Position 1", -1);
		SmartDashboard.putNumber("Defense at Position 2", -1);
		SmartDashboard.putNumber("Defense at Position 3", -1);
		SmartDashboard.putNumber("Defense at Position 4", -1);
	}
	
	public static void getInputFromAutoUI() {
		System.out.println("[DSIO] I got the selected one." + "   " + defenseChooser.getSelected().toString());
		
		pos[1] = (int) SmartDashboard.getNumber("Defense at Position 1", -1);
		pos[2] = (int) SmartDashboard.getNumber("Defense at Position 2", -1);
		pos[3] = (int) SmartDashboard.getNumber("Defense at Position 3", -1);
		pos[4] = (int) SmartDashboard.getNumber("Defense at Position 4", -1);
		
		//Parse defense chosen
		if (defenseChooser.getSelected() != Integer.valueOf(-1)) {
			autoSequence = "C" + defenseChooser.getSelected().toString() + ",";
		}
		else {
			autoSequence = "NNN,";
		}
		
		//Parse shoot strategy chosen
		if (shootChooser.getSelected() != "NNN") {
			autoSequence = autoSequence + shootChooser.getSelected().toString() + ",";
		}
		else {
			autoSequence = autoSequence + "NNN,";
		}
		
		//Parse end move chosen
		if (endChooser.getSelected() != "NNN") {
			autoSequence = autoSequence + endChooser.getSelected().toString();
		}
		else {
			autoSequence = autoSequence + "NNN";
		}
		
		System.out.println(autoSequence);
		
		numMoves = Character.getNumericValue(autoSequence.charAt(0));
	}
}
