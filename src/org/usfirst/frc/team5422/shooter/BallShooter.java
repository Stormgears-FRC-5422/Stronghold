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


public class BallShooter extends Subsystem {
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
		talonL.changeControlMode(TalonControlMode.PercentVbus);
		//Reverse output may be needed
//		talonL.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonL.configNominalOutputVoltage(+0.0f, -0.0f);
		talonL.setPID(StrongholdConstants.SHOOTER_P, StrongholdConstants.SHOOTER_I, StrongholdConstants.SHOOTER_D);
		talonL.setF(StrongholdConstants.SHOOTER_F);
//		talonL.setCloseLoopRampRate(1.0);
		
		talonR = new CANTalon(StrongholdConstants.TALON_RIGHT_SHOOTER);
		talonR.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonR.changeControlMode(TalonControlMode.PercentVbus);
		//Reverse output may be needed
//		talonR.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonR.configNominalOutputVoltage(+0.0f, -0.0f);
		talonR.setPID(StrongholdConstants.SHOOTER_P, StrongholdConstants.SHOOTER_I, StrongholdConstants.SHOOTER_D);
		talonR.setF(StrongholdConstants.SHOOTER_F);
//		talonR.setCloseLoopRampRate(1.0);
		
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
	
	//Shoots the ball
	//Inputs: distance, low or high goal
	
	public void shoot(StrongholdConstants.shootOptions goal) {
		talonR.changeControlMode(TalonControlMode.Speed);
		talonL.changeControlMode(TalonControlMode.Speed);
		actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_UP_PROFILE);
	}

//	public double getAngle(StrongholdConstants.shootOptions goal) {
//		return ShooterHelper.calculateAngle(goal);
//	}

//	public void fineTune(double sliderValue) {
//		sliderValue -= 0.008;
//
//		double adjustment = sliderValue * StrongholdConstants.TUNER_MULTIPLIER;
//
//		//Adjust actuator
//		changeAngle(ShooterHelper.calculateAngle(StrongholdRobot.shootOptionSelected));
//	}
	
//	public double fineTuneSpeed(double multiplier) {
//
//		double speed = (multiplier + 1)/2;
//		
//		return speed;
//	}
	
//	private double calculateSpeed(double angle, StrongholdConstants.shootOptions goal){
//		double speed;
//		//Theta is assumed to be 45 degrees.
//		speed = Math.pow(Math.sqrt(2 * Math.pow(ShooterHelper.getDistanceToGoal(goal), 2) - 5536), -4);
//		if (speed > 1) speed = 1;
//		else if (speed < 0.5) speed = 0.5;
//		return speed;
//	}
	
	//Changes the angle of the actuator
	public void changeAngle(double sliderVal) {

		double potTicks = StrongholdConstants.ACTUATOR_ARM_SLIDER_TO_POT_CONVERSION_FACTOR * 
								(sliderVal - StrongholdConstants.ACTUATOR_ARM_SLIDER_MIN) + 
								StrongholdConstants.ACTUATOR_ARM_UP_POT_FULLRANGE; 

//		if (encoderTicks < StrongholdConstants.ACTUATOR_ARM_POT_OPT_UP) {
//			encoderTicks = StrongholdConstants.ACTUATOR_ARM_POT_OPT_UP;
//		} else if (encoderTicks > StrongholdConstants.ACTUATOR_ARM_POT_OPT_DOWN) {
//			encoderTicks = StrongholdConstants.ACTUATOR_ARM_POT_OPT_DOWN;
//		}		
		
		if (potTicks > actuator.getPosition()) actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE);
		else actuator.setProfile(StrongholdConstants.ANGLE_MOTOR_UP_PROFILE);
		
		actuator.set(potTicks);

		SmartDashboard.putNumber("pot value: ", actuator.getPosition());

	}
	
	private void changeAngleAssisted(double angle) {
		//524 ticks = 0 degrees (real robot)
//		double angleToTicks = 524 - angle * 414.0 / 95.0;
		
//		819 ticks = 0 degrees (real robot)
		double angleToTicks = 819 - angle * 337.0 / 77.0;
		
		//Used for real robot
//		if (angleToTicks > 620) angleToTicks = 620;
//		else if (angleToTicks < 206) angleToTicks = 206;
		
		//Used in replica robot
		if (angleToTicks > StrongholdConstants.ACTUATOR_ARM_DOWN_POT_FULLRANGE) angleToTicks = StrongholdConstants.ACTUATOR_ARM_DOWN_POT_FULLRANGE;
		else if (angleToTicks < StrongholdConstants.ACTUATOR_ARM_UP_POT_FULLRANGE) angleToTicks = StrongholdConstants.ACTUATOR_ARM_UP_POT_FULLRANGE;
		//907 = -20degrees
		//570 = 57degrees
		
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

	//Used by autonomous/assisted mode
	public void shoot(double speedMultiplier) {
		_shoot(speedMultiplier);
	}
	
	public void shoot(double degrees, double speedMultiplier) {
		changeAngleAssisted(degrees);
		_shoot(speedMultiplier);
	}
	
	//Used by manual mode
	private void _shoot(double speedMultiplier) {
		
		//Potentiometer starts at 533
		//620 = min    -22 degrees
		//206 = max     73 degrees
//		changeAngle(calculateAngle(distance, goal)); 
		
		boolean full_speed = false;
		
		//Direction of motor to be found out
		//P, I, D, F--->  0.02, 0, 1.65, 0
//		talonR.set(-speed); //* StrongholdConstants.VEL_PER_100MS
//		talonL.set(speed);
//		talonR.set(-6248); //* StrongholdConstants.VEL_PER_10MS
//		talonL.set(6248); //0.762745

		talonR.set(-1 * speedMultiplier); //* StrongholdConstants.VEL_PER_10MS
		talonL.set(1 * speedMultiplier); //8465
		
//		while (full_speed == false) {
//			
//			if (Math.abs(talonR.getEncVelocity()) >= 50000 && Math.abs(talonL.getEncVelocity()) >= 50000) {
//				full_speed = true;
//			}
//		}
		

		Timer.delay(StrongholdConstants.SHOOT_DELAY1);
		
		relay.set(Relay.Value.kForward);
		
		Timer.delay(StrongholdConstants.SHOOT_DELAY2);
		stop();
		relay.set(Relay.Value.kOff);
		DSIO.shooterRunning = false;
		
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
		
}