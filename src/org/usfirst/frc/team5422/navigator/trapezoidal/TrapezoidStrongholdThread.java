package org.usfirst.frc.team5422.navigator.trapezoidal;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TrapezoidStrongholdThread implements Runnable{

	private CANTalon leftTalon;
	private CANTalon rightTalon;
	
	private CANTalon leftSlave;
	private CANTalon rightSlave;

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
	
	//network table stuff
	public NetworkTable trapTable = NetworkTable.getTable("Trapezoid");
	private int id;
	private String status = "";

	public TrapezoidStrongholdThread(CANTalon leftTalon, CANTalon rightTalon, CANTalon leftSlave, CANTalon rightSlave) {
		this.leftTalon = leftTalon;
		this.rightTalon = rightTalon;
		
		this.leftSlave = leftSlave;
		this.rightSlave = rightSlave;
		
		leftExample = new MotionProfileExample(this.leftTalon);
		rightExample = new MotionProfileExample(this.rightTalon);
		
		trapLoop = new Notifier(this);
		trapLoop.startPeriodic(0.01);
	}
	
	public void run() {
		
		if(hasTrapTask) {
				//start the motion profile(Teleop periodic)	
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
					
					status = "finished";
			}
			
			else {
				status = "running";
			}
			
			trapTable.putString("Trap Status", status);
			trapTable.putNumber("Trap ID", id);
		}
	}
	
	public void activateTrap(int leftTicks, int rightTicks, double leftVel, double rightVel, int id) {
		this.leftTicks = leftTicks;
		this.rightTicks = rightTicks;
		this.leftVel = leftVel;
		this.rightVel = rightVel;
		
		//use this for trap network table
		this.id = id;
		
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
	
	private void primeTalons() {

	}
	public void resetTrapezoid() {
		leftTalon.clearMotionProfileTrajectories();
		rightTalon.clearMotionProfileTrajectories();
		
		leftExample.reset();
		rightExample.reset();
	}

}
