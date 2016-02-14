package org.usfirst.frc.team5422.shooter;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class helps develop the methods for a boulder/ball Shooter mechanism 
 */

/*
 *@author Aditya Naik
 */


public class BallShooter extends Shooter {
	/**
	 * This function helps shoot the ball/boulder into the low goal
	 */
	
	CANTalon talonL;
	CANTalon talonR;
//	CANTalon actuator;
	Relay relay;
	Solenoid sol;
	
	public void shootLow() {
		
	}	

	/**
	 * This function helps shoot the ball/boulder into the high goal
	 */
	public void shootHigh(double distance, StrongholdConstants.shootHeightOptions goal) {
		shoot(distance, goal);
	}
	
	public BallShooter() {
		talonL = new CANTalon(StrongholdConstants.TALON_LEFT_SHOOTER);
		talonL.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonL.changeControlMode(TalonControlMode.Speed);
		//Reverse output may be needed
		talonL.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonL.configNominalOutputVoltage(+0.0f, -0.0f);
		talonL.setPID(0, 0, 0);
		talonL.setF(0);
		
		talonR = new CANTalon(StrongholdConstants.TALON_RIGHT_SHOOTER);
		talonR.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonR.changeControlMode(TalonControlMode.Speed);
		//Reverse output may be needed
		talonR.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonR.configNominalOutputVoltage(+0.0f, -0.0f);
		talonR.setPID(0, 0, 0);
		talonR.setF(0);
		
//		actuator = new CANTalon(StrongholdConstants.TALON_ACTUATOR);
//		actuator.setFeedbackDevice(FeedbackDevice.AnalogPot);
//		actuator.changeControlMode(TalonControlMode.Position);
//		//Reverse output may be needed
//		actuator.configPotentiometerTurns(POT_TURNS);	
//		actuator.configNominalOutputVoltage(+0.0f, -0.0f);
//		actuator.setPID(0, 0, 0);
//		actuator.setF(0);
	
//		relay = new Relay(StrongholdConstants.SOLENOID_SHOOTER);
	}

	/**
	 * This function helps shoot the ball/boulder into the high goal
	 */

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	//Shoots the ball
	//Inputs: distance, low or high goal
	public void shoot(double distance, StrongholdConstants.shootHeightOptions goal) {
		
//		changeAngle(calculateAngle(distance, goal)); 
		
		double speed = calculateSpeed(distance, calculateAngle(distance, goal));//calculateSpeed(distance, 45);

		//Direction of motor to be found out
		talonR.set(speed * StrongholdConstants.VEL_PER_100MS );
		talonL.set(speed * StrongholdConstants.VEL_PER_100MS);

		relay.set(Relay.Value.kForward);
		Timer.delay(3);
		talonR.set(StrongholdConstants.NO_THROTTLE);
		talonL.set(StrongholdConstants.NO_THROTTLE);
		relay.set(Relay.Value.kReverse);
	}
	
	//Distance given in inches
	private double calculateAngle(double distance, StrongholdConstants.shootHeightOptions goal) {
		double angle;
		if (goal == StrongholdConstants.shootHeightOptions.HIGH) {
			angle = Math.atan((StrongholdConstants.HEIGHT_TO_HIGH_GOAL / distance));
		} else {
			angle = Math.atan(StrongholdConstants.HEIGHT_TO_LOW_GOAL/distance);
		}
		return angle;
	}
	
	private double calculateSpeed(double distance, double angle){
		double speed;
		//Theta is assumed to be 45 degrees
		speed = Math.pow(Math.sqrt(2 * Math.pow(distance, 2) - 66432), -4);
		if (speed > 1) speed = 1;
		if (speed < 0.5) speed = 0.5;
		return speed;
	}
	
	//Changes the angle of the actuator
	private void changeAngle(double angle) {
//		actuator.set(POT_TURNS / ACTUATOR_ANGLE_RANGE * angle);
	}
			
}
