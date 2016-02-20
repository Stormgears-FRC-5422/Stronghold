package org.usfirst.frc.team5422.shooter;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdUtils;

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
		talonL.changeControlMode(TalonControlMode.PercentVbus);
		//Reverse output may be needed
		talonL.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonL.configNominalOutputVoltage(+0.0f, -0.0f);
		talonL.setPID(StrongholdConstants.SHOOTER_P, StrongholdConstants.SHOOTER_I, StrongholdConstants.SHOOTER_D);
		talonL.setF(StrongholdConstants.SHOOTER_F);
		
		talonR = new CANTalon(StrongholdConstants.TALON_RIGHT_SHOOTER);
		talonR.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talonR.changeControlMode(TalonControlMode.PercentVbus);
		//Reverse output may be needed
		talonR.configEncoderCodesPerRev(StrongholdConstants.ENCODER_TICKS_CPR);	
		talonR.configNominalOutputVoltage(+0.0f, -0.0f);
		talonR.setPID(StrongholdConstants.SHOOTER_P, StrongholdConstants.SHOOTER_I, StrongholdConstants.SHOOTER_D);
		talonR.setF(StrongholdConstants.SHOOTER_F);
		
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
	public void shoot(StrongholdConstants.shootOptions goal) {
		Thread shooterThread = new Thread(StrongholdRobot.shooterSubsystem);
		shooterThread.start();
	}
	
	//Distance given in inches
	private double calculateAngle(StrongholdConstants.shootOptions goal) {
		double angle;
		if (goal == StrongholdConstants.shootOptions.HIGH_CENTER ||
				goal == StrongholdConstants.shootOptions.HIGH_RIGHT ||
				goal == StrongholdConstants.shootOptions.HIGH_LEFT) angle = Math.atan((StrongholdConstants.HEIGHT_TO_HIGH_GOAL / getDistancetoGoal(goal)));
		else angle = Math.atan(StrongholdConstants.HEIGHT_TO_LOW_GOAL/getDistancetoGoal(goal));
		return angle;
	}
	
	private double calculateSpeed(double angle, StrongholdConstants.shootOptions goal){
		double speed;
		//Theta is assumed to be 45 degrees
		speed = Math.pow(Math.sqrt(2 * Math.pow(getDistancetoGoal(goal), 2) - 5536), -4);
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
		talonL.set(StrongholdConstants.NO_THROTTLE);
	}
	
	public double getDistancetoGoal(StrongholdConstants.shootOptions option) {
		shootOptions bestGoal = option;
		double distanceFromGoal;
		
		if (bestGoal == shootOptions.HIGH_CENTER) {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_HCENTER_GOAL[0], StrongholdConstants.POSITION_HCENTER_GOAL[1]);
		}
		else if (bestGoal == shootOptions.HIGH_LEFT) {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_HLEFT_GOAL[0], StrongholdConstants.POSITION_HLEFT_GOAL[1]);
		}
		else if (bestGoal == shootOptions.HIGH_RIGHT){
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_HRIGHT_GOAL[0], StrongholdConstants.POSITION_HRIGHT_GOAL[1]);
		}
		else if (bestGoal == shootOptions.LOW_LEFT) {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_LLEFT_GOAL[0], StrongholdConstants.POSITION_LLEFT_GOAL[1]);
		}
		else {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_LRIGHT_GOAL[0], StrongholdConstants.POSITION_LRIGHT_GOAL[1]);
		}
		
		return distanceFromGoal;
	}

	public void run() {
		
//		changeAngle(calculateAngle(distance, goal)); 
		
		double speed = 1; //calculateSpeed();

		//Direction of motor to be found out
		talonR.set(speed); //* StrongholdConstants.VEL_PER_100MS
		talonL.set(-1*speed);
		
		Timer.delay(StrongholdConstants.SHOOT_DELAY);

		relay.set(Relay.Value.kForward);
		
		Timer.delay(StrongholdConstants.SHOOT_DELAY);
		stop();
		relay.set(Relay.Value.kOff);
		DSIO.shooterRunning = false;
		
	}
	
		
}