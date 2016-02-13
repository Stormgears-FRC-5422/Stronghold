package org.usfirst.frc.team5422.shooter;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

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
	
	CANTalon talon1;
	CANTalon talon2;
	Joystick joy;
	
	public BallShooter(int port1, int port2, Joystick stick) {
		talon1 = new CANTalon(port1);
		talon2 = new CANTalon(port2);
		joy = stick;
	}
	
	public void intakeAndShoot() {
		
		talon1.changeControlMode(TalonControlMode.PercentVbus);
		talon2.changeControlMode(TalonControlMode.PercentVbus);
		
		double YVal2 = joy.getAxis(AxisType.kY);
		
		//Shooting
		if (YVal2 < 0) {
		
			talon1.set(YVal2 * .55);
			talon2.set(-YVal2 * .55);
		}
		
		//Intake
		else {
			talon1.set(-YVal2 * .55);
			talon2.set(YVal2 * .55);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This function helps shoot the ball/boulder into the low goal on the Right
	 */
	public void shootLowRight() {
		
	}	

	/**
	 * This function helps shoot the ball/boulder into the low goal on the Left
	 */
	public void shootLowLeft() {
		
	}	

	/**
	 * This function helps shoot the ball/boulder into the high goal on the Right
	 */
	public void shootHighRight() {
		
	}	
	
	/**
	 * This function helps shoot the ball/boulder into the high goal on the Left
	 */
	public void shootHighLeft() {
		
	}	

	/**
	 * This function helps shoot the ball/boulder into the high goal on the Center
	 */
	public void shootHighCenter() {
		
	}	
	
		
}
