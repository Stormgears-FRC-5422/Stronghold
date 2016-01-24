package org.usfirst.frc.team5422.robot;

import org.usfirst.frc.team5422.DSIO.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDTuner {
	static SendableChooser chooser;
	static double run, mode;

	static CANTalon talon1, talon2, talon3, talon4;

	static double runTalon1, runTalon2, runTalon3, runTalon4;
	static double F, P, I, D;

	//Constructor
	public PIDTuner() {
		//Initialize talons 
		talon1 = new CANTalon(1);
		talon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon1.reverseOutput(true);
		talon1.configEncoderCodesPerRev(2048);	
		talon1.configNominalOutputVoltage(+0.0f, -0.0f);

		talon2 = new CANTalon(2);
		talon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon2.reverseOutput(true);
		talon2.configEncoderCodesPerRev(2048);	
		talon2.configNominalOutputVoltage(+0.0f, -0.0f);

		talon3 = new CANTalon(3);
		talon3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon3.reverseOutput(false);
		talon3.configEncoderCodesPerRev(2048);	
		talon3.configNominalOutputVoltage(+0.0f, -0.0f);

		talon4 = new CANTalon(4);
		talon4.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon4.reverseOutput(false);
		talon4.configEncoderCodesPerRev(2048);	
		talon4.configNominalOutputVoltage(+0.0f, -0.0f);

		//Initialize variables
		run = 0;
		runTalon1 = 0;
		runTalon2 = 0;
		runTalon3 = 0;
		runTalon4 = 0;
		F = 1.705;
		P = 0.000185;
		I = 0;
		D = 0;
		mode = 1;
	}

	public static void tunePIDVelocity() {
		createUI();

		if (run == 1 & mode == 1) {
			if (runTalon1 == 1) {
				talon1.setPID(P, I, D);

				//Configure talons some more
				talon1.setEncPosition(0); 
				talon1.changeControlMode(TalonControlMode.Speed);

				talon1.set(0.4 * 20.48);

				//Output to SmartDashboard
				DSIO.outputToSFX("Talon 1 Velocity", talon1.getSpeed());
			}
			if (runTalon2 == 1) {
				talon2.setPID(P, I, D);

				//Configure talons some more
				talon2.setEncPosition(0); 
				talon2.changeControlMode(TalonControlMode.Speed);

				talon2.set(0.4 * 20.48);

				//Output to SmartDashboard
				DSIO.outputToSFX("Talon 2 Velocity", talon2.getSpeed());
			}
			if (runTalon3 == 1) {
				talon3.setPID(P, I, D);

				//Configure talons some more
				talon3.setEncPosition(0); 
				talon3.changeControlMode(TalonControlMode.Speed);

				talon3.set(0.4 * 20.48);

				//Output to SmartDashboard
				DSIO.outputToSFX("Talon 3 Velocity", talon3.getSpeed());
			}
			if (runTalon4 == 1) {
				talon4.setPID(P, I, D);

				//Configure talons some more
				talon4.setEncPosition(0); 
				talon4.changeControlMode(TalonControlMode.Speed);

				talon4.set(0.4 * 20.48);

				//Output to SmartDashboard
				DSIO.outputToSFX("Talon 4 Velocity", talon4.getSpeed());
			}
		}
	}

	public static void tunePIDPosition() {

		//Don't start until the correct modes are entered
		while (run != 1 & mode != 2) {
			createUI();
		}
		if (run == 1 & mode == 2) {
			if (runTalon1 == 1) {
				talon1.setPID(P, I, D);

				//Configure talons some more 
				talon1.setEncPosition(0); 
				talon1.changeControlMode(TalonControlMode.Position);

				for (int counter = 0; counter <= 300; counter++) {
					talon1.set(100);
					//Output to SmartDashboard
					DSIO.outputToSFX("Talon 1 Position", talon1.getPosition());
				}
			}
			if (runTalon2 == 1) {
				talon2.setPID(P, I, D);

				//Configure talons some more
				talon2.setEncPosition(0); 
				talon2.changeControlMode(TalonControlMode.Position);

				for (int counter = 0; counter <= 300; counter++) {
					talon2.set(100);
					//Output to SmartDashboard
					DSIO.outputToSFX("Talon 2 Position", talon2.getPosition());
				}
			}
			if (runTalon3 == 1) {
				talon3.setPID(P, I, D);

				//Configure talons some more
				talon3.setEncPosition(0); 
				talon3.changeControlMode(TalonControlMode.Position);

				for (int counter = 0; counter <= 300; counter++) {
					talon3.set(100);
					//Output to SmartDashboard
					DSIO.outputToSFX("Talon 3 Position", talon3.getPosition());
				}
			}
			if (runTalon4 == 1) {
				talon4.setPID(P, I, D);

				//Configure talons some more
				talon4.setEncPosition(0); 
				talon4.changeControlMode(TalonControlMode.Position);

				for (int counter = 0; counter <= 300; counter++) {
					talon4.set(100);
					//Output to SmartDashboard
					DSIO.outputToSFX("Talon 4 Position", talon4.getPosition());
				}
			}
		}
	}

	public static void createUI() {
		//PID controls
		SmartDashboard.putNumber("F", F);
		SmartDashboard.putNumber("P", P);
		SmartDashboard.putNumber("I", I);
		SmartDashboard.putNumber("D", D);
		F = SmartDashboard.getNumber("F", F);
		P = SmartDashboard.getNumber("P", P);
		I = SmartDashboard.getNumber("I", I);
		D = SmartDashboard.getNumber("D", D);

		//Add components to smart dash board
		SmartDashboard.putString("Run? 0 = Don't; 1 = Run", "");

		//Run all motors controls
		SmartDashboard.putNumber("Run Motors", run);
		run = SmartDashboard.getNumber("Run Motors", run);

		//Mode: velocity or position
		SmartDashboard.putNumber("Run Mode: 1/Velocity, 2/Position", mode);
		mode = SmartDashboard.getNumber("Run Mode: 1/Velocity, 2/Position", mode);

		//Individual talon controls
		SmartDashboard.putNumber("Talon 1", runTalon1);
		SmartDashboard.putNumber("Talon 2", runTalon2);
		SmartDashboard.putNumber("Talon 3", runTalon3);
		SmartDashboard.putNumber("Talon 4", runTalon4);
		runTalon1 = SmartDashboard.getNumber("Talon 1", runTalon1);
		runTalon2 = SmartDashboard.getNumber("Talon 2", runTalon2);
		runTalon3 = SmartDashboard.getNumber("Talon 3", runTalon3);
		runTalon4 = SmartDashboard.getNumber("Talon 4", runTalon4);
	}
}