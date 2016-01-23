package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Driver {
	public static CANTalon talon1, talon2, talon3, talon4;

	//Constructor
	public Driver() {
		//Declare talons
		talon1 = new CANTalon(1);
		talon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon1.reverseOutput(true);
		talon1.configEncoderCodesPerRev(2048);	
		talon1.configNominalOutputVoltage(+0.0f, -0.0f);
		
		talon2 = new CANTalon(1);
		talon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon2.reverseOutput(true);
		talon2.configEncoderCodesPerRev(2048);	
		talon2.configNominalOutputVoltage(+0.0f, -0.0f);
		
		talon3 = new CANTalon(1);
		talon3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon3.reverseOutput(true);
		talon3.configEncoderCodesPerRev(2048);	
		talon3.configNominalOutputVoltage(+0.0f, -0.0f);
		
		talon4 = new CANTalon(1);
		talon4.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon4.reverseOutput(true);
		talon4.configEncoderCodesPerRev(2048);	
		talon4.configNominalOutputVoltage(+0.0f, -0.0f);
		
		//Set PID closed loop gains
		double F, P, I, D;
		
		//Change the PID values here. Keep F as it is.
		F = 1.705;
		P = 0.00025;
		I = 0;
		D = 0;
		
		talon1.setF(F);
		talon1.setP(P);
		talon1.setI(I);
		talon1.setD(D);
		
		talon2.setF(F);
		talon2.setP(P);
		talon2.setI(I);
		talon2.setD(D);

		talon3.setF(F);
		talon3.setP(P);
		talon3.setI(I);
		talon3.setD(D);
		
		talon4.setF(F);
		talon4.setP(P);
		talon4.setI(I);
		talon4.setD(D);
	}
	
	/**
	 * This function drives the robot around the carpet. It is not precise. 
	 */
	public static void openDrive(double y, double theta) {
		//Set values
		double yValue = y;
		double thetaValue = theta;

		double velocityLeft = 0, velocityRight = 0;

		//Calculate velocities
		velocityLeft = 1.25 * (yValue * 5 + 0.2 * thetaValue);
		velocityRight = 1.25 * (yValue * 5 - 0.2 * thetaValue);



		//Configure talons some more
		talon1.setEncPosition(0); 
		talon1.changeControlMode(TalonControlMode.Speed);
		
		talon2.setEncPosition(0); 
		talon2.changeControlMode(TalonControlMode.Speed);
		
		talon2.setEncPosition(0); 
		talon2.changeControlMode(TalonControlMode.Speed);
		
		talon3.setEncPosition(0); 
		talon3.changeControlMode(TalonControlMode.Speed);

		//Set the velocity of the talons
		talon1.set(velocityLeft * 20.48);
		talon2.set(velocityLeft * 20.48);
		talon3.set(velocityRight * 20.48);
		talon4.set(velocityRight * 20.48);

		//Output to SmartDashboard for diagnostics
		DSIO.outputToSFX("Left Velocity", talon1.getSpeed());
		DSIO.outputToSFX("Right Velocity", talon3.getSpeed());
		
		DSIO.outputToSFX("Joystick Theta", DSIO.getLinearTheta());
	}
}