package org.usfirst.frc.team5422.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDTuner {
	static CANTalon talon1 = null, talon2 = null, talon3 = null, talon4 = null;

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

		SmartDashboard.putNumber("Talon ID 1", talonID[0]);
		SmartDashboard.putNumber("Talon ID 2", talonID[1]);
		SmartDashboard.putNumber("Talon ID 3", talonID[2]);
		SmartDashboard.putNumber("Talon ID 4", talonID[3]);

		//		SmartDashboard.putNumber("Max Velocity", maxVel);
		SmartDashboard.putNumber("Target Encoder Position", targetPos);


	}

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

			talonID[0] = SmartDashboard.getNumber("Talon ID 1", talonID[0]);
			talonID[1] = SmartDashboard.getNumber("Talon ID 2", talonID[1]);
			talonID[2] = SmartDashboard.getNumber("Talon ID 3", talonID[2]);
			talonID[3] = SmartDashboard.getNumber("Talon ID 4", talonID[3]);

			initializeTalonsAndGo(talonID[0], talonID[1], talonID[2], talonID[3]);
			printValues();
		}
	}

	public static void initializeTalonsAndGo(double talonID1,  double talonID2, double talonID3, double talonID4) {
		talonID[0] = talonID1;
		talonID[1] = talonID2;
		talonID[2] = talonID3;
		talonID[3] = talonID4;
		
		if (talonID[0] != -1) { 
			talon1 = new CANTalon((int) talonID[0]);
			talon1.changeControlMode(TalonControlMode.Position);
			talon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon1.setPID(P, I, D);
			talon1.set(targetPos);
		}
		if (talonID[1] != -1) {
			talon2 = new CANTalon((int) talonID[1]);
			talon2.changeControlMode(TalonControlMode.Position);
			talon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon2.setPID(P, I, D);
			talon2.set(targetPos);
		}
		if (talonID[2] != -1) {
			talon3 = new CANTalon((int) talonID[2]);
			talon3.changeControlMode(TalonControlMode.Position);
			talon3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon3.setPID(P, I, D);
			talon3.set(targetPos);
		}
		if (talonID[3] != -1) {
			talon4 = new CANTalon((int) talonID[3]);
			talon4.changeControlMode(TalonControlMode.Position);
			talon4.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon4.setPID(P, I, D);
			talon4.set(targetPos);
		}
	}

	public static void printValues() {
		//Every 10ms, display the values 
		while (checkSpeed() != 0) {
			if (!(talon1 == null)) {
				SmartDashboard.putNumber("Talon 1 Position", talon1.getPosition());
				SmartDashboard.putNumber("Talon 1 Position Graph", talon1.getPosition());
			}
			if (!(talon2 == null)) {
				SmartDashboard.putNumber("Talon 2 Position", talon2.getPosition());
				SmartDashboard.putNumber("Talon 2 Position Graph", talon2.getPosition());
			}
			if (!(talon3 == null)) {
				SmartDashboard.putNumber("Talon 3 Position", talon3.getPosition());
				SmartDashboard.putNumber("Talon 3 Position Graph", talon3.getPosition());
			}
			if (!(talon4 == null)) {
				SmartDashboard.putNumber("Talon 4 Position", talon4.getPosition());
				SmartDashboard.putNumber("Talon 4 Position Graph", talon4.getPosition());
			}
			
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
		}//End while
		stopMotors();
		createUI();
	}//End printValues()
	
	public static void stopMotors() {
		//Stop the motors
		if (talon1 != null) {
			talon1.changeControlMode(TalonControlMode.Disabled);
			talon1.changeControlMode(TalonControlMode.Position);
		}
		if (talon2 != null) {
			talon1.changeControlMode(TalonControlMode.Disabled);
			talon1.changeControlMode(TalonControlMode.Position);
		}
		if (talon3 != null) {
			talon1.changeControlMode(TalonControlMode.Disabled);
			talon1.changeControlMode(TalonControlMode.Position);
		}
		if (talon4 != null) {
			talon1.changeControlMode(TalonControlMode.Disabled);
			talon1.changeControlMode(TalonControlMode.Position);
		}
	}
	
	public static double checkSpeed() {
		double speed = 0;
		
		//Check the speed of the 4 talons and output the first one that isn't 0
		//This method should only be used to check if any of the talons are spinning a motor
		
		if (talon1 != null) {
			speed = talon1.getSpeed();
		}
		else if (talon2 != null) {
			speed = talon2.getSpeed();
		}
		else if (talon3 != null) {
			speed = talon3.getSpeed();
		}
		else if (talon4 != null) {
			speed = talon4.getSpeed();
		}
		else {
			speed = 0;
		}
		return speed;
	}
}//End class