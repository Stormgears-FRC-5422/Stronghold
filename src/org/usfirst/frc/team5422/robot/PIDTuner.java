package org.usfirst.frc.team5422.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDTuner {
	static CANTalon talon[] = new CANTalon[4];

	static double[] talonID = new double[4];
	static double F, P, I, D, maxVel, targetPos, targetPosPrev;

	//Constructor 
	public PIDTuner() {
		//Initialize variables
		F = 1.705;
		P = 0.001;
		I = 0;
		D = 0;
		talonID[0] = -1;
		talonID[1] = -1;
		talonID[2] = -1;
		talonID[3] = -1;
		maxVel = 0;
		targetPos = 0;
		targetPosPrev = 0;

		SmartDashboard.putNumber("P", P);		
		SmartDashboard.putNumber("I", I);
		SmartDashboard.putNumber("D", D);

		for (int i = 0; i < 3; i++) {
			SmartDashboard.putNumber("Talon ID " + (i + 1), talonID[i]);
		}
		
		SmartDashboard.putNumber("Target Encoder Position", targetPos);
	}

	//Wrapper method
	public static void tunePIDPosition() {
		createUI();
	}

	public static void createUI() {
		targetPos = SmartDashboard.getNumber("Target Encoder Position", targetPos);
		while (targetPos == 0) {
			targetPos = SmartDashboard.getNumber("Target Encoder Position", targetPos);
		}
		if (targetPos != 0) {		
			P = SmartDashboard.getNumber("P", P);
			I = SmartDashboard.getNumber("I", I);
			D = SmartDashboard.getNumber("D", D);

			for (int i = 0; i < 3; i++) {
				talonID[i] = SmartDashboard.getNumber("Talon ID " + (i + 1), talonID[i]);
			}

			initializeTalonsAndGo(talonID[0], talonID[1], talonID[2], talonID[3]);
			printValues();
		}
	}

	public static void initializeTalonsAndGo(double talonID1,  double talonID2, double talonID3, double talonID4) {
		talonID[0] = talonID1;
		talonID[1] = talonID2;
		talonID[2] = talonID3;
		talonID[3] = talonID4;

		for (int i = 0; i < 3; i++) {
			if (talonID[i] != -1) {
				talon[i] = new CANTalon((int) talonID[i]);
				talon[i].changeControlMode(TalonControlMode.Position);
				talon[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
				talon[i].setPID(P, I, D);
				talon[i].set(targetPos);
			}//End if
		}//End for
	}//End initializeTalonsAndGo()

	public static void printValues() {
		//Every 10ms, display the values 
		while (checkSpeed() != 0) {
			for (int i = 0; i < 3; i++) {
				if (talon[i] != null) {
					SmartDashboard.putNumber("Talon " + (i + 1) + " Position", talon[i].getPosition());
					SmartDashboard.putNumber("Talon " + (i + 1) + " Position Graph", talon[i].getPosition());
				}//End if
			}//End for

			//Slow down the loop
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//If they decide to enter a new value, go to that value
			targetPosPrev = targetPos;
			targetPos = SmartDashboard.getNumber("Target Encoder Position", targetPos);
			if (targetPos != targetPosPrev) {
				//Stop the motors and break the loop, then start over
				break;
			}
		}//End while and start over
		stopMotors();
		createUI();
	}//End printValues()
	
	public static void stopMotors() {
		//Stop the motors
		for (int i = 0; i < 3; i++) {
			if (talon[i] != null) {
				talon[i].changeControlMode(TalonControlMode.Disabled);
				talon[i].changeControlMode(TalonControlMode.Position);
			}//End if
		}//End for
	}//End stopMotors()
	
	public static double checkSpeed() {
		double speed = 0;
		
		//Check the speed of the 4 talons and output the first one that isn't 0
		//This method should only be used to check if any of the talons are spinning a motor
		
		if (talon[0] != null) {
			speed = talon[0].getSpeed();
		}
		else if (talon[1] != null) {
			speed = talon[1].getSpeed();
		}
		else if (talon[2] != null) {
			speed = talon[2].getSpeed();
		}
		else if (talon[3] != null) {
			speed = talon[3].getSpeed();
		}
		else {
			speed = 0;
		}
		return speed;
	}
}//End class