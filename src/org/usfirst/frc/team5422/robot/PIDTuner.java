package org.usfirst.frc.team5422.robot;

import org.usfirst.frc.team5422.DSIO.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDTuner {
	static CANTalon talon1 = null, talon2 = null, talon3 = null, talon4 = null;

	static double[] talonID = new double[4];
	static double F, P, I, D, maxVel, targetPos;
	
	static double go = 0;

	//Constructor 
	public PIDTuner() {
		//Initialize variables
		F = 1.705;
		P = 0.000185;
		I = 0;
		D = 0;
		talonID[0] = -1;
		talonID[1] = -1;
		talonID[2] = -1;
		talonID[3] = -1;
		maxVel = 0;
		targetPos = 0;

		SmartDashboard.putNumber("P", P);		
		SmartDashboard.putNumber("I", I);
		SmartDashboard.putNumber("D", D);

		SmartDashboard.putNumber("Talon ID 1", talonID[0]);
		SmartDashboard.putNumber("Talon ID 2", talonID[1]);
		SmartDashboard.putNumber("Talon ID 3", talonID[2]);
		SmartDashboard.putNumber("Talon ID 4", talonID[3]);

		//		SmartDashboard.putNumber("Max Velocity", maxVel);
		SmartDashboard.putNumber("Target Encoder Position", targetPos);
		
		SmartDashboard.putNumber("Go? Enter 1", go);
		
		
	}

	public static void tunePIDPosition() {
		createUI();
	}

	public static void createUI() {
		go = SmartDashboard.getNumber("Go? Enter 1", go);
		while (!(go == 1)) {
			go = SmartDashboard.getNumber("Go? Enter 1", go);
		}
		if (go == 1) {		
			P = SmartDashboard.getNumber("P", P);
			I = SmartDashboard.getNumber("I", I);
			D = SmartDashboard.getNumber("D", D);

			talonID[0] = SmartDashboard.getNumber("Talon ID 1", talonID[0]);
			talonID[1] = SmartDashboard.getNumber("Talon ID 2", talonID[1]);
			talonID[2] = SmartDashboard.getNumber("Talon ID 3", talonID[2]);
			talonID[3] = SmartDashboard.getNumber("Talon ID 4", talonID[3]);

			//			maxVel = SmartDashboard.getNumber("Max Velocity", maxVel);
			targetPos = SmartDashboard.getNumber("Target Encoder Position", targetPos);
			
			go = SmartDashboard.getNumber("Go? Enter 1", go);

			initializeTalonsAndGo(talonID[0], talonID[1], talonID[2], talonID[3]);
			printValues();
		}
	}

	public static void initializeTalonsAndGo(double talonID2,  double talonID3, double talonID4, double talonID5) {
		if (talonID[0] != -1) {
			talon1 = new CANTalon((int) talonID[0]);
			talon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon1.configEncoderCodesPerRev(2048);
			talon1.configNominalOutputVoltage(+0.0f, -0.0f);
			talon1.setEncPosition(0);
			talon1.setPID(P, I, D);
			talon1.set(targetPos);
		}
		if (talonID[1] != -1) {
			talon2 = new CANTalon((int) talonID[1]);
			talon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon2.configEncoderCodesPerRev(2048);
			talon2.configNominalOutputVoltage(+0.0f, -0.0f);
			talon2.setEncPosition(0);
			talon2.setPID(P, I, D);
			talon2.set(targetPos);
		}
		if (talonID[2] != -1) {
			talon3 = new CANTalon((int) talonID[2]);
			talon3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon3.configEncoderCodesPerRev(2048);
			talon3.configNominalOutputVoltage(+0.0f, -0.0f);
			talon3.setEncPosition(0);
			talon3.setPID(P, I, D);
			talon3.set(targetPos);
		}
		if (talonID[3] != -1) {
			talon4 = new CANTalon((int) talonID[3]);
			talon4.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon4.configEncoderCodesPerRev(2048);
			talon4.configNominalOutputVoltage(+0.0f, -0.0f);
			talon4.setEncPosition(0);
			talon4.setPID(P, I, D);
			talon4.set(targetPos);
		}
	}

	public static void printValues() {
		//Every 20 ticks, display the values
		for (int counter = 10; counter <= targetPos; counter += 10) {
			if (!(talon1 == null)) {
				DSIO.outputToSFX("Talon 1 Position", talon1.getPosition());
				System.out.println(talon1.getPosition());
				System.out.println(counter);
			}
			if (!(talon2 == null)) {
				DSIO.outputToSFX("Talon 2 Position", talon2.getPosition());
			}
			if (!(talon3 == null)) {
				DSIO.outputToSFX("Talon 3 Position", talon3.getPosition());
			}
			if (!(talon4 == null)) {
				DSIO.outputToSFX("Talon 4 Position", talon4.getPosition());
			}
			
			//If they decide to stop, stop the loop and motors
			go = SmartDashboard.getNumber("Go? Enter 1", go);
			if (go == 0) {
				//Stop the motors and break the loop
				if (talon1 != null) talon1.set(0);
				if (talon2 != null) talon2.set(0);
				if (talon3 != null) talon3.set(0);
				if (talon4 != null) talon4.set(0);
				createUI();
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//End for
	}//End makeMotorsGo()
}//End class