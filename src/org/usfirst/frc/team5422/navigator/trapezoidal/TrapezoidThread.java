package org.usfirst.frc.team5422.navigator.trapezoidal;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

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
							
				leftTalon.set(setOutputLeft.value);
				rightTalon.set(setOutputRight.value);
		
				
			if((leftExample.getSetValue() == CANTalon.SetValueMotionProfile.Hold) && (rightExample.getSetValue() == CANTalon.SetValueMotionProfile.Hold)) {
					hasTrapTask = false;
					leftExample.stopMotionProfile();
					rightExample.stopMotionProfile();
					resetTrapezoid();
					//Add NetworkTable stuff
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
		leftTalon.setPID(1, 0, 0);
		leftTalon.setF(0);
		
		rightTalon.setPID(1, 0, 0);
		rightTalon.setF(0);
		
	 	leftTalon.changeControlMode(TalonControlMode.MotionProfile);
	 	rightTalon.changeControlMode(TalonControlMode.MotionProfile);
	}
	
	private void resetTrapezoid() {
		leftTalon.clearMotionProfileTrajectories();
		rightTalon.clearMotionProfileTrajectories();
	}
	
	private void primeTalons() {
		rightTalon.reverseSensor(true);
	}
	

}
