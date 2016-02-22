package org.usfirst.frc.team5422.shooter;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class helps develop the methods for a boulder/ball Shooter mechanism 
 */

/*
 *@author Aditya Naik
 */


public class BallShooter extends Subsystem implements Runnable {
	/**
	 * This function helps shoot the ball/boulder into the low goal
	 */
	
	CANTalon talonL;
	CANTalon talonR;
	CANTalon actuator;
	Relay relay;
	
	public BallShooter() {
		talonL = new CANTalon(StrongholdConstants.TALON_LEFT_SHOOTER);
		talonL.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonL.changeControlMode(TalonControlMode.Speed);
		//Reverse output may be needed
		talonL.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonL.configNominalOutputVoltage(+0.0f, -0.0f);
		talonL.setPID(StrongholdConstants.SHOOTER_P, StrongholdConstants.SHOOTER_I, StrongholdConstants.SHOOTER_D);
		talonL.setF(StrongholdConstants.SHOOTER_F);
		talonL.setCloseLoopRampRate(0.5);
		
		talonR = new CANTalon(StrongholdConstants.TALON_RIGHT_SHOOTER);
		talonR.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonR.changeControlMode(TalonControlMode.Speed);
		//Reverse output may be needed
		talonR.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonR.configNominalOutputVoltage(+0.0f, -0.0f);
		talonR.setPID(StrongholdConstants.SHOOTER_P, StrongholdConstants.SHOOTER_I, StrongholdConstants.SHOOTER_D);
		talonR.setF(StrongholdConstants.SHOOTER_F);
		talonR.setCloseLoopRampRate(0.5);
		
		actuator = new CANTalon(StrongholdConstants.TALON_ACTUATOR);
		actuator.setFeedbackDevice(FeedbackDevice.AnalogPot);
		actuator.changeControlMode(TalonControlMode.Position);
		actuator.configNominalOutputVoltage(+0.0f, -0.0f);
		
		actuator.setPID(StrongholdConstants.ANGLE_MOTOR_DOWN_P, StrongholdConstants.ANGLE_MOTOR_DOWN_I, StrongholdConstants.ANGLE_MOTOR_DOWN_D
				, StrongholdConstants.ANGLE_MOTOR_DOWN_F, StrongholdConstants.ANGLE_MOTOR_DOWN_IZONE, StrongholdConstants.ANGLE_MOTOR_DOWN_RAMP_RATE, 
				StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE);
		
		actuator.setPID(StrongholdConstants.ANGLE_MOTOR_UP_P, StrongholdConstants.ANGLE_MOTOR_UP_I, StrongholdConstants.ANGLE_MOTOR_UP_D
				, StrongholdConstants.ANGLE_MOTOR_UP_F, StrongholdConstants.ANGLE_MOTOR_UP_IZONE, StrongholdConstants.ANGLE_MOTOR_UP_RAMP_RATE, 
				StrongholdConstants.ANGLE_MOTOR_UP_PROFILE);
	
		relay = new Relay(StrongholdConstants.SOLENOID_SHOOTER);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	//Shoots the ball
	//Inputs: distance, low or high goal
	
	public void shoot(StrongholdConstants.shootOptions goal) {
		actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_UP_PROFILE);
		Thread shooterThread = new Thread(StrongholdRobot.shooterSubsystem);
		shooterThread.start();
	}
	
	//Distance given in inches
	private double calculateAngle(StrongholdConstants.shootOptions goal) {
		double angle;
		if (goal == StrongholdConstants.shootOptions.HIGH_CENTER ||
				goal == StrongholdConstants.shootOptions.HIGH_RIGHT ||
				goal == StrongholdConstants.shootOptions.HIGH_LEFT) angle = Math.atan((StrongholdConstants.HEIGHT_TO_HIGH_GOAL / ShooterHelper.getDistanceToGoal(goal)));
		else angle = Math.atan(StrongholdConstants.HEIGHT_TO_LOW_GOAL/ShooterHelper.getDistanceToGoal(goal));
		return angle;
	}
	
	private double calculateSpeed(double angle, StrongholdConstants.shootOptions goal){
		double speed;
		//Theta is assumed to be 45 degrees
		speed = Math.pow(Math.sqrt(2 * Math.pow(ShooterHelper.getDistanceToGoal(goal), 2) - 5536), -4);
		if (speed > 1) speed = 1;
		else if (speed < 0.5) speed = 0.5;
		return speed;
	}
	
	//Changes the angle of the actuator
	private void changeAngle(double angle) {
		//524 ticks = 0 degrees
		double angleToTicks = 524 - angle * 414.0 / 95.0;
		if (angleToTicks > actuator.getPosition()) actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE);
		else actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE);
		actuator.set(angleToTicks);
	}
	public void intake() {
		actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE);
		actuator.set(StrongholdConstants.ANGLE_MOTOR_INTAKE_POS);
		talonR.changeControlMode(TalonControlMode.PercentVbus);
		talonL.changeControlMode(TalonControlMode.PercentVbus);
		talonR.set(0.5);
		talonL.set(-0.5);
	}
	
	public void stop() {
		talonR.set(StrongholdConstants.NO_THROTTLE);
		talonL.set(StrongholdConstants.NO_THROTTLE);
	}

	public void run() {
		
		//Potentiometer starts at 533
		//620 = min    -22 degrees
		//206 = max     73 degrees
//		changeAngle(calculateAngle(distance, goal)); 
		
		double speed = 1; //calculateSpeed();

		//Direction of motor to be found out
		//P, I, D, F--->  0.02, 0, 1.65, 0
		talonR.set(-speed); //* StrongholdConstants.VEL_PER_100MS
		talonL.set(speed);

		Timer.delay(StrongholdConstants.SHOOT_DELAY);
		
		relay.set(Relay.Value.kForward);
		
		Timer.delay(StrongholdConstants.SHOOT_DELAY);
		stop();
		relay.set(Relay.Value.kOff);
		DSIO.shooterRunning = false;
		
	}
	
		
}