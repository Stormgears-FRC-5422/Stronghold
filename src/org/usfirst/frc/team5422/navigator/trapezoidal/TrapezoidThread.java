package org.usfirst.frc.team5422.navigator.trapezoidal;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

public class TrapezoidThread implements Runnable{

	private CANTalon leftTalon;
	private CANTalon rightTalon;

	private int leftTicks = 0;
	private int rightTicks = 0;
	private double leftVel = 0;
	private double rightVel = 0;
	private double leftRotations = 0;
	private double rightRotations = 0;
	
	
	private boolean hasTrapTask = false;
	private Notifier trapLoop;
	
	private MotionProfileExample leftExample;
	private MotionProfileExample rightExample;
	
	public TrapezoidThread(CANTalon leftTalon, CANTalon rightTalon) {
		this.leftTalon = leftTalon;
		this.rightTalon = rightTalon;
		
		leftExample = new MotionProfileExample(this.leftTalon);
		rightExample = new MotionProfileExample(this.rightTalon);
		
		leftExample.setType("Left");
		rightExample.setType("Right");
		
		trapLoop = new Notifier(this);
		trapLoop.startPeriodic(0.01);
	}
	
	public void run() {
		
		if(hasTrapTask) {
				//start the motion profile(Teleop periodic)
				System.out.println("In the loop");
			
				if(leftExample.getSetValue() != CANTalon.SetValueMotionProfile.Hold) 
					leftExample.control();
				
				if(rightExample.getSetValue() != CANTalon.SetValueMotionProfile.Hold) 
					rightExample.control();
					
				if(leftExample.getSetValue() != CANTalon.SetValueMotionProfile.Hold) 
					leftExample.startMotionProfile();
				
				if(rightExample.getSetValue() != CANTalon.SetValueMotionProfile.Hold) 
					rightExample.startMotionProfile();
			
				CANTalon.SetValueMotionProfile setOutputLeft = leftExample.getSetValue();
				CANTalon.SetValueMotionProfile setOutputRight = rightExample.getSetValue();
			
				int currentLeftTicks = leftTalon.getEncPosition();
				int currentRightTicks = rightTalon.getEncPosition();
				
				leftTalon.set(setOutputLeft.value);
				rightTalon.set(setOutputRight.value);
			
				if(leftExample.getSetValue() != CANTalon.SetValueMotionProfile.Enable)
					leftTalon.setEncPosition(currentLeftTicks);
			
				if(rightExample.getSetValue() != CANTalon.SetValueMotionProfile.Enable)
					rightTalon.setEncPosition(currentRightTicks);
				
			if((leftExample.getSetValue() == CANTalon.SetValueMotionProfile.Hold) && (rightExample.getSetValue() == CANTalon.SetValueMotionProfile.Hold)) {
					hasTrapTask = false;
					leftExample.stopMotionProfile();
					rightExample.stopMotionProfile();
					resetTrapezoid();
				}
		}
	}
	
	public void activateTrap(int leftTicks, int rightTicks, double leftVel, double rightVel) {
		this.leftTicks = leftTicks;
		this.rightTicks = rightTicks;
		this.leftVel = leftVel;
		this.rightVel = rightVel;
		
		resetTrapezoid();
		initializeTalons();
		primeTalons();
		calcRotations();
		generateProfiles();
	
		System.out.println(Timer.getFPGATimestamp());		
		hasTrapTask = true;
	}
	
	
	private void generateProfiles() {
		leftExample.setProfile(TrapezoidControl.motionProfileUtility(leftRotations, leftVel, leftTalon.getEncPosition()));
		rightExample.setProfile(TrapezoidControl.motionProfileUtility(rightRotations, rightVel, rightTalon.getEncPosition()));
	}
	
	private void calcRotations() {
		leftRotations = leftTicks/8192.0;
		rightRotations = rightTicks/8192.0;
	}
	
	private void initializeTalons() {
		
		leftTalon.setPID(StrongholdConstants.TRAP_P, StrongholdConstants.TRAP_I, StrongholdConstants.TRAP_D);
		leftTalon.setF(StrongholdConstants.TRAP_F);

		rightTalon.setPID(StrongholdConstants.TRAP_P, StrongholdConstants.TRAP_I, StrongholdConstants.TRAP_D);
		rightTalon.setF(StrongholdConstants.TRAP_F);
		
	 	leftTalon.changeControlMode(TalonControlMode.MotionProfile);
	 	rightTalon.changeControlMode(TalonControlMode.MotionProfile);
	}
	
	private void resetTrapezoid() {
			
		leftTalon.clearMotionProfileTrajectories();
	//	leftTalon.changeControlMode(TalonControlMode.PercentVbus);
	//	leftTalon.set(0);
//		leftTalon.setEncPosition(0); //will need to be commented out 
		
		rightTalon.clearMotionProfileTrajectories();
	//	rightTalon.changeControlMode(TalonControlMode.PercentVbus);
	//	rightTalon.set(0);
//		rightTalon.setEncPosition(0);
	
	}
	
	private void primeTalons() {
		if(leftTicks < 0) {
			leftTalon.reverseOutput(true); //may need to be changed
			leftTalon.reverseSensor(false); //may need to be changed
			//talon[0].reverseSensor(true); //rabbot		
		}
		
		else {
			leftTalon.reverseOutput(false); //may need to be changed
			leftTalon.reverseSensor(true); //may need to be changed
		//	talon[0].reverseSensor(false); //rabbot
		}
		

		
		if(rightTicks < 0) {
			rightTalon.reverseOutput(false); //may need to be changed
			rightTalon.reverseSensor(true); //may need to be changed
			//talon[1].reverseSensor(false); //rabbot
		}
		
		else {
			rightTalon.reverseOutput(true); //may need to be changed
			rightTalon.reverseSensor(false); //may need to be changed
		//	talon[1].reverseSensor(true); //rabbot
		}
	}
	

}
