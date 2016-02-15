package org.usfirst.frc.team5422.shooter;

import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.navigator.Navigator;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootHeightOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdUtils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class helps develop the methods for a boulder/ball Shooter mechanism 
 */

/*
 *@author Aditya Naik
 */


public class BallShooter extends Subsystem {
	/**
	 * This function helps shoot the ball/boulder into the low goal
	 */
	
	CANTalon talonL;
	CANTalon talonR;
	CANTalon actuator;
	Relay relay;
	
	public void shootLow(StrongholdConstants.shootHeightOptions goal) {
		System.out.println("Shooting low...");
		shoot(goal);
		System.out.println("shot ball low");
	}	

	/**
	 * This function helps shoot the ball/boulder into the high goal
	 */
	public void shootHigh(StrongholdConstants.shootHeightOptions goal) {
		System.out.println("Shooting high...");
		shoot(goal);
		System.out.println("shot ball high");
	}
	
	public BallShooter() {
		talonL = new CANTalon(StrongholdConstants.TALON_LEFT_SHOOTER);
		talonL.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonL.changeControlMode(TalonControlMode.PercentVbus);
		//Reverse output may be needed
		talonL.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonL.configNominalOutputVoltage(+0.0f, -0.0f);
		talonL.setPID(0, 0, 0);
		talonL.setF(0);
		
		talonR = new CANTalon(StrongholdConstants.TALON_RIGHT_SHOOTER);
		talonR.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonR.changeControlMode(TalonControlMode.PercentVbus);
		//Reverse output may be needed
		talonR.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonR.configNominalOutputVoltage(+0.0f, -0.0f);
		talonR.setPID(0, 0, 0);
		talonR.setF(0);
		
//		actuator = new CANTalon(StrongholdConstants.TALON_ACTUATOR);
//		actuator.setFeedbackDevice(FeedbackDevice.AnalogPot);
//		actuator.changeControlMode(TalonControlMode.PercentVbus);
//		//Reverse output may be needed
//		actuator.configPotentiometerTurns(POT_TURNS);	
//		actuator.configNominalOutputVoltage(+0.0f, -0.0f);
//		actuator.setPID(0, 0, 0);
//		actuator.setF(0);
	
		relay = new Relay(StrongholdConstants.SOLENOID_SHOOTER);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	//Shoots the ball
	//Inputs: distance, low or high goal
	public void shoot(StrongholdConstants.shootHeightOptions goal) {
		
//		changeAngle(calculateAngle(distance, goal)); 
		
		double speed = 1; //calculateSpeed(distance, 45);

		//Direction of motor to be found out
		talonR.set(speed); //* StrongholdConstants.VEL_PER_100MS
		talonL.set(-1*speed);
		
		Timer.delay(StrongholdConstants.SHOOT_DELAY);

		relay.set(Relay.Value.kForward);
		
		Timer.delay(StrongholdConstants.SHOOT_DELAY);
		talonR.set(StrongholdConstants.NO_THROTTLE);
		talonL.set(StrongholdConstants.NO_THROTTLE);
		relay.set(Relay.Value.kOff);
	}
	
	//Distance given in inches
	private double calculateAngle(StrongholdConstants.shootHeightOptions goal) {
		double angle;
		if (goal == StrongholdConstants.shootHeightOptions.HIGH) angle = Math.atan((StrongholdConstants.HEIGHT_TO_HIGH_GOAL / getDistancetoGoal()));
		else angle = Math.atan(StrongholdConstants.HEIGHT_TO_LOW_GOAL/getDistancetoGoal());
		return angle;
	}
	
	private double calculateSpeed(double angle){
		double speed;
		//Theta is assumed to be 45 degrees
		speed = Math.pow(Math.sqrt(2 * Math.pow(getDistancetoGoal(), 2) - 5536), -4);
		if (speed > 1) speed = 1;
		if (speed < 0.5) speed = 0.5;
		return speed;
	}
	
	//Changes the angle of the actuator
	private void changeAngle(double angle) {
		actuator.set(-0.5);
		SmartDashboard.putNumber("Pot Pos: ", actuator.getPosition());
		Timer.delay(1);
		actuator.set(0);
	}
	
	public void intake() {
		talonR.changeControlMode(TalonControlMode.PercentVbus);
		talonL.changeControlMode(TalonControlMode.PercentVbus);
		talonR.set(-StrongholdConstants.FULL_THROTTLE);
		talonL.set(StrongholdConstants.FULL_THROTTLE);
		
	}
	public void stop() {
		talonR.set(StrongholdConstants.NO_THROTTLE);
		talonL.set(-StrongholdConstants.NO_THROTTLE);
	}
	
	public double getDistancetoGoal() {
		shootOptions bestGoal = StrongholdUtils.findBestGoal(shootHeightOptions.HIGH);
		double distanceFromGoal;
		double thetaX;
		double thetaY;
		
		if (bestGoal == shootOptions.HIGH_CENTER) {
			thetaX = Math.abs(GlobalMapping.getX() - StrongholdConstants.POSITION_HCENTER_GOAL[0]);
			thetaY = Math.abs(GlobalMapping.getY() - StrongholdConstants.POSITION_HCENTER_GOAL[1]);
		}
		else if (bestGoal == shootOptions.HIGH_LEFT) {
			thetaX = Math.abs(GlobalMapping.getX() - StrongholdConstants.POSITION_HLEFT_GOAL[0]);
			thetaY = Math.abs(GlobalMapping.getY() - StrongholdConstants.POSITION_HLEFT_GOAL[1]);
		}
		else {
			thetaX = Math.abs(GlobalMapping.getX() - StrongholdConstants.POSITION_HRIGHT_GOAL[0]);
			thetaY = Math.abs(GlobalMapping.getY() - StrongholdConstants.POSITION_HRIGHT_GOAL[1]);
		}
		
		distanceFromGoal =  Math.pow(Math.pow(thetaX, 2) + Math.pow(thetaY, 2), -2);
		
		return distanceFromGoal;
	}
	
		
}