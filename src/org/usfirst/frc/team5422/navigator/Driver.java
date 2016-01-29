package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Driver {
	public static CANTalon talon[] = new CANTalon[4];

	//Constructor
	public Driver() {
		//Declare talons
		talon[0] = new CANTalon(1);
		talon[0].reverseOutput(true);	
		talon[0].configNominalOutputVoltage(+0.0f, -0.0f);

		talon[1] = new CANTalon(2);
		talon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon[1].reverseOutput(true);
		talon[1].configEncoderCodesPerRev(2048);	
		talon[1].configNominalOutputVoltage(+0.0f, -0.0f);

		talon[2] = new CANTalon(3);
		talon[2].setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon[2].reverseOutput(false);
		talon[2].configEncoderCodesPerRev(2048);	
		talon[2].configNominalOutputVoltage(+0.0f, -0.0f);

		talon[3] = new CANTalon(4);
		talon[3].reverseOutput(false);	
		talon[3].configNominalOutputVoltage(+0.0f, -0.0f);

		//Set PID closed loop gains
		double F, P, I, D;

		//Change the PID values here. Keep F as it is .
		F = 1.705;
		P = 0.000185;
		I = 0;
		D = 0;

		for (int i = 0; i < 3; i++) {
			talon[i].setPID(P, I, D);
			talon[i].setF(F);
		}
	}

	/**
	 * This function drives the robot around the carpet. It is not precise. 
	 */
	public static void openDrive(double yJoystick, double xJoystick) {
		//Declare variables
		double velocityLeft = 0, velocityRight = 0;
		
		//Calculate velocities
		ArcadeDrive.arcadeDrive(yJoystick, xJoystick);
		velocityLeft = ArcadeDrive.arcadeDriveLeft();
		velocityRight = ArcadeDrive.arcadeDriveRight();

		//Configure talons some more 
		talon[0].setEncPosition(0); 
		talon[0].changeControlMode(TalonControlMode.Follower);

		talon[1].setEncPosition(0); 
		talon[1].changeControlMode(TalonControlMode.Speed);

		talon[2].setEncPosition(0); 
		talon[2].changeControlMode(TalonControlMode.Speed);

		talon[3].setEncPosition(0); 
		talon[3].changeControlMode(TalonControlMode.Follower);

		//Set the velocity of the talons
		talon[1].set(velocityLeft * 20.48);
		talon[0].set(2);
		talon[2].set(velocityRight * 20.48);
		talon[3].set(3);

		//Output to SmartDashboard for diagnostics
		DSIO.outputToSFX("Left Velocity", talon[1].getSpeed());
		DSIO.outputToSFX("Right Velocity", talon[2].getSpeed());
	}
}