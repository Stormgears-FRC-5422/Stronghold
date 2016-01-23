package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Driver {
	public static void openDrive(double y, double theta) {
		//Set values
		double yValue = y;
		double thetaValue = theta;
		
		double velocityLeft = 0, velocityRight = 0;
		
		//Calculate velocities
		velocityLeft = 1.25 * (yValue * 10 + 0.09 * thetaValue);
		velocityRight = 1.25 * (yValue * 10 - 0.09 * thetaValue);
		
		//Declare talons
		CANTalon talon = new CANTalon(1);
		
		//Configure talons
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.reverseOutput(true);
		talon.configEncoderCodesPerRev(2048);	
    	talon.configNominalOutputVoltage(+0.0f, -0.0f);
    	talon.setEncPosition(0); 
    	talon.changeControlMode(TalonControlMode.Speed);
    	
    	//Set PID closed loop gains
    	talon.setF(1.705);
    	talon.setP(0.00025);
    	talon.setI(0);
    	talon.setD(0);
    	
    	//Set the velocity of the talons
    	talon.set(velocityLeft * 20.48);
		
    	/*
    	 * Broken!!!
    	 */
    	//Output to SFX for diagnostics
//    	DSIO.outputToSFX(false, false, false, false, false, false);
	}
}
