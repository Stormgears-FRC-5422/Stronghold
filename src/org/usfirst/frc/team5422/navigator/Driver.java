package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/*
 * @author Michael
 */

public class Driver {
	public static CANTalon talon[] = new CANTalon[4];

	//Constructor
	public Driver(CANTalon.TalonControlMode controlMode) {
		//Declare talons
		if (controlMode == CANTalon.TalonControlMode.Speed | controlMode == CANTalon.TalonControlMode.PercentVbus) {
			talon[0] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_MASTER);
			talon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon[0].reverseOutput(true);
			talon[0].configEncoderCodesPerRev(2048);	
			talon[0].configNominalOutputVoltage(+0.0f, -0.0f);

			talon[1] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_SLAVE);
			talon[1].reverseOutput(true);
			talon[1].configNominalOutputVoltage(+0.0f, -0.0f);

			talon[2] = new CANTalon(StrongholdConstants.TALON_DRIVE_RIGHT_MASTER);
			talon[2].setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talon[2].reverseOutput(true);
			talon[2].configEncoderCodesPerRev(2048);	
			talon[2].configNominalOutputVoltage(+0.0f, -0.0f);

			talon[3] = new CANTalon(StrongholdConstants.TALON_DRIVE_RIGHT_SLAVE);
			talon[3].reverseOutput(true);
			talon[3].configNominalOutputVoltage(+0.0f, -0.0f);

			//Configure talons some more
			talon[0].changeControlMode(controlMode);
			talon[1].changeControlMode(TalonControlMode.Follower);
			talon[2].changeControlMode(controlMode);
			talon[3].changeControlMode(TalonControlMode.Follower);
		}
		else {
			System.out.println("Invalid Talon Control Mode, set the talon in Speed mode or PercentVbus mode");
		}

		//Set PID closed loop gains
		double F, P, I, D;

		//Change the PID values here. Keep F as it is .
		F = 1.705;
		P = 0.000185;
		I = 0;
		D = 0;

		for (int i = 0; i < 4; i++) {
			talon[i].setPID(P, I, D);
			talon[i].setF(F);
		}
	}

	/**
	 * This function drives the robot around the carpet. It is not precise. 
	 */
	public static void openDrive(double yJoystick, double xJoystick, CANTalon.TalonControlMode controlMode) {
		//Declare variables
		double velocityLeft = 0, velocityRight = 0;

		//Calculate velocities
		ArcadeDrive.arcadeDrive(yJoystick, xJoystick);
		velocityLeft = ArcadeDrive.arcadeDriveLeft() * 0.5;
		velocityRight = ArcadeDrive.arcadeDriveRight() * 0.5;

		//Set the velocity of the talons
		if (controlMode == CANTalon.TalonControlMode.Speed | controlMode == CANTalon.TalonControlMode.PercentVbus) {
			if (controlMode == CANTalon.TalonControlMode.Speed) {
				talon[0].set(velocityLeft * 81.92);
			}
			else talon[0].set(velocityLeft);
			talon[1].set(1);

			if (controlMode == CANTalon.TalonControlMode.Speed) {
				talon[2].set(velocityRight * 81.92);
			}
			else talon[2].set(velocityRight);
			talon[3].set(3);
		}
		else {
			System.out.println("Invalid Talon Control Mode, set the talon in Speed mode or PercentVbus mode");
		}


		//Output to SmartDashboard for diagnostics

		DSIO.outputToSFX("velocityLeft", velocityLeft);
		DSIO.outputToSFX("velocityRight", velocityRight);

		//Current being put through the talons
		DSIO.outputToSFX("Talon ID 1 Velocity (Left)", talon[0].getOutputCurrent());
		DSIO.outputToSFX("Talon ID 8 Velocity (Left)", talon[1].getOutputCurrent());
		DSIO.outputToSFX("Talon ID 3 Velocity (Right)", talon[2].getOutputCurrent());
		DSIO.outputToSFX("Talon ID 0 Velocity (Right)", talon[3].getOutputCurrent());

		//Talon speeds
		DSIO.outputToSFX("Talon ID 1 Velocity (Left)", talon[0].getSpeed());
		DSIO.outputToSFX("Talon ID 8 Velocity (Left)", talon[0].getSpeed());
		DSIO.outputToSFX("Talon ID 3 Velocity (Right)", talon[2].getSpeed());
		DSIO.outputToSFX("Talon ID 0 Velocity (Right)", talon[2].getSpeed());
	}
}