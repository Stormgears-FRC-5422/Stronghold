package org.usfirst.frc.team5422.robot;

import org.usfirst.frc.team5422.DSIO.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDTuner {
	static CANTalon talon1, talon2, talon3, talon4;

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
		F = 1.705;
		P = 0.000185;
		I = 0;
		D = 0;
	}

	public static void tunePIDVelocity() {
		talon1.setPID(P, I, D);

		//Configure talons some more
		talon1.setEncPosition(0); 
		talon1.changeControlMode(TalonControlMode.Speed);

		talon1.set(0.4 * 20.48);

		//Output to SmartDashboard
		DSIO.outputToSFX("Talon 1 Velocity", talon1.getSpeed());


		talon2.setPID(P, I, D);

		//Configure talons some more
		talon2.setEncPosition(0); 
		talon2.changeControlMode(TalonControlMode.Speed);

		talon2.set(0.4 * 20.48);

		//Output to SmartDashboard
		DSIO.outputToSFX("Talon 2 Velocity", talon2.getSpeed());


		talon3.setPID(P, I, D);

		//Configure talons some more
		talon3.setEncPosition(0); 
		talon3.changeControlMode(TalonControlMode.Speed);

		talon3.set(0.4 * 20.48);

		//Output to SmartDashboard
		DSIO.outputToSFX("Talon 3 Velocity", talon3.getSpeed());


		talon4.setPID(P, I, D);

		//Configure talons some more
		talon4.setEncPosition(0); 
		talon4.changeControlMode(TalonControlMode.Speed);

		talon4.set(0.4 * 20.48);

		//Output to SmartDashboard
		DSIO.outputToSFX("Talon 4 Velocity", talon4.getSpeed());
	}

	public static void tunePIDPosition() {
		talon1.setPID(P, I, D);

		//Configure talons some more 
		talon1.setEncPosition(0); 
		talon1.changeControlMode(TalonControlMode.Position);

		for (int counter = 0; counter <= 300; counter++) {
			talon1.set(100);
			//Output to SmartDashboard
			DSIO.outputToSFX("Talon 1 Position", talon1.getPosition());
		}

		talon2.setPID(P, I, D);

		//Configure talons some more
		talon2.setEncPosition(0); 
		talon2.changeControlMode(TalonControlMode.Position);

		for (int counter = 0; counter <= 300; counter++) {
			talon2.set(100);
			//Output to SmartDashboard
			DSIO.outputToSFX("Talon 2 Position", talon2.getPosition());
		}
		talon3.setPID(P, I, D);

		//Configure talons some more
		talon3.setEncPosition(0); 
		talon3.changeControlMode(TalonControlMode.Position);

		for (int counter = 0; counter <= 300; counter++) {
			talon3.set(100);
			//Output to SmartDashboard
			DSIO.outputToSFX("Talon 3 Position", talon3.getPosition());
		}
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