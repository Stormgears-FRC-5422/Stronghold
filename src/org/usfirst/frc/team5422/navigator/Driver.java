package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.navigator.trapezoidal.GeneratedMotionProfile;
import org.usfirst.frc.team5422.navigator.trapezoidal.MotionProfileExample;
import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidControl;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

import static org.usfirst.frc.team5422.utils.StrongholdConstants.TALON_DRIVE_RIGHT_MASTER;

/*
 * @author Michael
 */

public class Driver {
	public static CANTalon talon[] = new CANTalon[2];

	//Constructor
	public Driver(CANTalon.TalonControlMode controlMode) {
		//Set PID closed loop gains
		double F, P, I, D;

		//Change the PID values here. Keep F as it is .
		F = 1.705;
		P = 0.000185;
		I = 0;
		D = 0;

		//Declare talons
		if (controlMode == CANTalon.TalonControlMode.Speed | controlMode == CANTalon.TalonControlMode.PercentVbus) {
			talon[0] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_MASTER);
			talon[1] = new CANTalon(TALON_DRIVE_RIGHT_MASTER);
			talon[0].reverseOutput(true);

			for (int i = 0; i < 2; i++) {
				talon[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
				talon[i].configEncoderCodesPerRev(2048);
				talon[i].configNominalOutputVoltage(+0.0f, -0.0f);
				talon[i].changeControlMode(controlMode);
				talon[i].setPID(P, I, D);
				talon[i].setF(F);
			}
		}
		else {
			System.out.println("Invalid Talon Control Mode, set the talon in Speed mode or PercentVbus mode");
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

			if (controlMode == CANTalon.TalonControlMode.Speed) {
				talon[1].set(velocityRight * 81.92);
			}
			else talon[1].set(velocityRight);
		}
		else {
			System.out.println("Invalid Talon Control Mode, set the talon in Speed mode or PercentVbus mode");
		}


		//Output to SmartDashboard for diagnostics

		DSIO.outputToSFX("velocityLeft", velocityLeft);
		DSIO.outputToSFX("velocityRight", velocityRight);

		//Current being put through the talons
		DSIO.outputToSFX("Talon ID 3 Current (Right)", talon[0].getOutputCurrent());
		DSIO.outputToSFX("Talon ID 0 Current (Right)", talon[1].getOutputCurrent());

		//Talon speeds
		DSIO.outputToSFX("Talon ID 3 Velocity (Right)", talon[0].getSpeed());
		DSIO.outputToSFX("Talon ID 0 Velocity (Right)", talon[1].getSpeed());
	}
	
	
	public static void moveTrapezoid(int leftTicks, int rightTicks, double leftVelocity, double rightVelocity) {
		
		
		talon[0] = new CANTalon(0);
		talon[2] = new CANTalon(3);
		talon[0].setPID(1, 0, 0);
		talon[2].setPID(1, 0, 0);
		talon[0].setF(0);
		talon[1].setF(0);
		
		double leftRotations = leftTicks/8192.0;
		double rightRotations = rightTicks/8192.0;
		
		MotionProfileExample leftProfile = new MotionProfileExample(talon[0]);
		MotionProfileExample rightProfile = new MotionProfileExample(talon[2]);
		
		//config the talons as appropriate
		talon[0].setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		if(leftTicks > 0) {
			talon[0].reverseOutput(true); //may need to be changed
			talon[0].reverseSensor(false); //may need to be changed
		}
		
		else {
			talon[0].reverseOutput(false); //may need to be changed
			talon[0].reverseSensor(true); //may need to be changed
		}
		
		talon[2].setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		if(rightTicks > 0) {
			talon[2].reverseOutput(false); //may need to be changed
			talon[2].reverseSensor(true); //may need to be changed
		}
		
		else {
			talon[2].reverseOutput(true); //may need to be changed
			talon[2].reverseSensor(false); //may need to be changed
		}
	
		//create both of the motion profiles
		GeneratedMotionProfile.leftPoints = TrapezoidControl.motionProfileUtility(leftRotations, leftVelocity);
		GeneratedMotionProfile.kNumLeftPoints = GeneratedMotionProfile.leftPoints.length;
		
		GeneratedMotionProfile.rightPoints = TrapezoidControl.motionProfileUtility(rightRotations, rightVelocity);
		GeneratedMotionProfile.kNumRightPoints = GeneratedMotionProfile.rightPoints.length;
		
		//set up all the "formalities" so that the motion profiles can actually be run
		leftProfile.control();
    	rightProfile.control();
		talon[0].changeControlMode(TalonControlMode.MotionProfile);
		talon[2].changeControlMode(TalonControlMode.MotionProfile);
		CANTalon.SetValueMotionProfile setOutputLeft = leftProfile.getSetValue();
		CANTalon.SetValueMotionProfile setOutputRight = rightProfile.getSetValue();
		talon[0].set(setOutputLeft.value);
		talon[2].set(setOutputRight.value);
		
		//start the motion profile
		leftProfile.startMotionProfile();
		rightProfile.startMotionProfile();		
	}
	
	public static void resetTrapezoid() {
		talon[0].changeControlMode(TalonControlMode.PercentVbus);
		talon[0].set(0);
		talon[0].setEncPosition(0);
		
		talon[2].changeControlMode(TalonControlMode.PercentVbus);
		talon[2].set(0);
		talon[2].setEncPosition(0);
	}
	
}