package org.usfirst.frc.team5422.navigator;


/*
 * @author Mayank
 */

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.*;


public class Navigator extends Subsystem{
	
	private CANTalon motor1;
	private CANTalon motor2;
	private CANTalon motor3;
	private CANTalon motor4;
	
	private Joystick joystick; 

	
	private boolean isFinished=false;
	
	
	public Navigator(){
		motor1 = new CANTalon(1);
		motor2 = new CANTalon(2);
		motor3 = new CANTalon(3);
		motor4 = new CANTalon(4);
		
		joystick = new Joystick(0);
		
		motor1.changeControlMode(TalonControlMode.PercentVbus);
		motor3.changeControlMode(TalonControlMode.PercentVbus);
		
		motor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		motor2.changeControlMode(TalonControlMode.Follower);
		motor4.changeControlMode(TalonControlMode.Follower);
		
		motor2.set(1);
		motor4.set(3);
	}
	
	public double powerFromSpeed(Speed speed){
		double power = 0.0;
		
		switch(speed){
			case FAST:
				power = 0.5;
				break;
			case MEDIUM:
				power = 0.3;
				break;
			case SLOW:
				power = 0.1;
				break;
			default:
				power = 0.3;
				break;
		}
		
		return power;
	}
	
	public void MoveRobotStraight(double inches, Speed speed){
		double power = powerFromSpeed(speed);
		
		double destinationTicks = inches/StrongholdConstants.INCHES_PER_TICK;
		
		double newTickR;
		double newTickL;
		
		double dTickR;
		double dTickL;
		
		double oldTickR = motor2.getEncPosition();
		double oldTickL = motor3.getEncPosition();
		
		motor2.setEncPosition(0);
		motor3.setEncPosition(0);
		
		double dSigmaD;
		double dTheta;
		
		double dX;
		double dY;
		
		this.isFinished = false;
		
		while(!isFinished()){
			
			motor1.set(power);
			motor3.set(-power);
			
			newTickR = -motor2.getEncPosition();
			newTickL = motor3.getEncPosition();
			
			
			
			dTickR = newTickR - oldTickR;
			dTickL = newTickL - oldTickL;
			
			oldTickR = newTickR;
			oldTickL = newTickL;
			
			SmartDashboard.putNumber("dTickR", dTickR);
			SmartDashboard.putNumber("dTickL", dTickL);
			
			
			dSigmaD = (dTickR+dTickL)/2;
			
			GlobalMapping.addTotalDistance(dSigmaD);
			
			
			dTheta = (dTickR - dTickL)/(StrongholdConstants.WHEEL_BASE/StrongholdConstants.INCHES_PER_TICK);
			SmartDashboard.putNumber("dTheta", dTheta);
			GlobalMapping.addTotalRotation(dTheta);
			
			dX = Math.cos(GlobalMapping.theta)*dSigmaD;
			dY = Math.sin(GlobalMapping.theta)*dSigmaD;
			
			GlobalMapping.addCurrentPosition(dX, dY);
			
			SmartDashboard.putNumber("Motor 2", newTickR);
			SmartDashboard.putNumber("Motor 3", newTickL);
			
			SmartDashboard.putNumber("Total Distance", GlobalMapping.sigmaD);
			SmartDashboard.putNumber("Theta", GlobalMapping.theta);
			
			SmartDashboard.putNumber("dSigmaD", dSigmaD);
			SmartDashboard.putNumber("dTicksR", dTickR);
			SmartDashboard.putNumber("dTicksR", dTickL);
			SmartDashboard.putNumber("x-position", GlobalMapping.x);
			SmartDashboard.putNumber("y-position", GlobalMapping.y);
			
			if(GlobalMapping.sigmaD > destinationTicks){
				this.isFinished = true;
			}
		}
		
		
		
		
	}
	
	public void MoveRobotTurnInPlace(double theta, Speed speed){
		
		//reduce theta to theta (mod Math.PI)
		theta = GlobalMapping.reduceAngleRad(theta);
		
		double power = powerFromSpeed(speed);
		
		double newTickR;
		double newTickL;
		
		double dTickR;
		double dTickL;
		
		double oldTickR = motor2.getEncPosition();
		double oldTickL = motor3.getEncPosition();
		
		motor2.setEncPosition(0);
		motor3.setEncPosition(0);
		
		double dSigmaD;
		double dTheta;
		
		double dX;
		double dY;
		
		this.isFinished = false;
		
		while(!isFinished()){
			if(theta>=0){
				motor1.set(power);
				motor3.set(power);
			}else{
				motor1.set(-power);
				motor3.set(-power);
			}
			
			newTickR = -motor2.getEncPosition();
			newTickL = motor3.getEncPosition();
			
			
			
			dTickR = newTickR - oldTickR;
			dTickL = newTickL - oldTickL;
			
			oldTickR = newTickR;
			oldTickL = newTickL;
			
			SmartDashboard.putNumber("dTickR", dTickR);
			SmartDashboard.putNumber("dTickL", dTickL);
			
			
			dSigmaD = (dTickR+dTickL)/2;
			
			GlobalMapping.addTotalDistance(dSigmaD);
			
			
			dTheta = (dTickR - dTickL)/(StrongholdConstants.WHEEL_BASE/StrongholdConstants.INCHES_PER_TICK);
			SmartDashboard.putNumber("dTheta", dTheta);
			GlobalMapping.addTotalRotation(dTheta);
			
			dX = Math.cos(GlobalMapping.theta)*dSigmaD;
			dY = Math.sin(GlobalMapping.theta)*dSigmaD;
			
			GlobalMapping.addCurrentPosition(dX, dY);
			
			SmartDashboard.putNumber("Motor 2", newTickR);
			SmartDashboard.putNumber("Motor 3", newTickL);
			
			SmartDashboard.putNumber("Total Distance", GlobalMapping.sigmaD);
			SmartDashboard.putNumber("Theta", GlobalMapping.theta);
			
			SmartDashboard.putNumber("dSigmaD", dSigmaD);
			SmartDashboard.putNumber("dTicksR", dTickR);
			SmartDashboard.putNumber("dTicksR", dTickL);
			SmartDashboard.putNumber("x-position", GlobalMapping.x);
			SmartDashboard.putNumber("y-position", GlobalMapping.y);
			
			if(Math.abs(GlobalMapping.theta - theta) < Math.PI/32  ){
				this.isFinished = true;
			}
		}
	}
	
	public boolean isFinished(){
		return this.isFinished;
	}
	
	public void DiagnosticTestJoystick(){
		
		double newTickR;
		double newTickL;
		
		double dTickR;
		double dTickL;
		
		double oldTickR = motor2.getEncPosition();
		double oldTickL = motor3.getEncPosition();
		
		motor2.setEncPosition(0);
		motor3.setEncPosition(0);
		
		double dSigmaD;
		double dTheta;
		
		double dX;
		double dY;
		
		double joystickval;
		
		while(true){
			
			 joystickval = joystick.getY();
			
			motor1.set(joystickval);
			motor3.set(-joystickval);
			
			newTickR = -motor2.getEncPosition();
			newTickL = motor3.getEncPosition();
			
			
			
			dTickR = newTickR - oldTickR;
			dTickL = newTickL - oldTickL;
			
			oldTickR = newTickR;
			oldTickL = newTickL;
			
			SmartDashboard.putNumber("dTickR", dTickR);
			SmartDashboard.putNumber("dTickL", dTickL);
			
			
			dSigmaD = (dTickR+dTickL)/2;
			
			GlobalMapping.addTotalDistance(dSigmaD);
			
			
			dTheta = (dTickR - dTickL)/(StrongholdConstants.WHEEL_BASE/StrongholdConstants.INCHES_PER_TICK);
			SmartDashboard.putNumber("dTheta", dTheta);
			GlobalMapping.addTotalRotation(dTheta);
			
			dX = Math.cos(GlobalMapping.theta)*dSigmaD;
			dY = Math.sin(GlobalMapping.theta)*dSigmaD;
			
			GlobalMapping.addCurrentPosition(dX, dY);
			
			SmartDashboard.putNumber("Motor 2", newTickR);
			SmartDashboard.putNumber("Motor 3", newTickL);
			
			SmartDashboard.putNumber("Total Distance", GlobalMapping.sigmaD);
			SmartDashboard.putNumber("Theta", GlobalMapping.theta);
			
			SmartDashboard.putNumber("dSigmaD", dSigmaD);
			SmartDashboard.putNumber("dTicksR", dTickR);
			SmartDashboard.putNumber("dTicksR", dTickL);
			SmartDashboard.putNumber("x-position", GlobalMapping.x);
			SmartDashboard.putNumber("y-position", GlobalMapping.y);
		}
	
	}
	
	public void DiagnosticTestAuto(Speed speed){
	
		double power = powerFromSpeed(speed);
		
		double newTickR;
		double newTickL;
		
		double dTickR;
		double dTickL;
		
		double oldTickR = motor2.getEncPosition();
		double oldTickL = motor3.getEncPosition();
		
		motor2.setEncPosition(0);
		motor3.setEncPosition(0);
		
		double dSigmaD;
		double dTheta;
		
		double dX;
		double dY;
		
		while(true){
			
			
			motor1.set(power);
			motor3.set(-power);
			
			newTickR = -motor2.getEncPosition();
			newTickL = motor3.getEncPosition();
			
			
			
			dTickR = newTickR - oldTickR;
			dTickL = newTickL - oldTickL;
			
			oldTickR = newTickR;
			oldTickL = newTickL;
			
			SmartDashboard.putNumber("dTickR", dTickR);
			SmartDashboard.putNumber("dTickL", dTickL);
			
			
			dSigmaD = (dTickR+dTickL)/2;
			
			GlobalMapping.addTotalDistance(dSigmaD);
			
			
			dTheta = (dTickR - dTickL)/(StrongholdConstants.WHEEL_BASE/StrongholdConstants.INCHES_PER_TICK);
			SmartDashboard.putNumber("dTheta", dTheta);
			GlobalMapping.addTotalRotation(dTheta);
			
			dX = Math.cos(GlobalMapping.theta)*dSigmaD;
			dY = Math.sin(GlobalMapping.theta)*dSigmaD;
			
			GlobalMapping.addCurrentPosition(dX, dY);
			
			SmartDashboard.putNumber("Motor 2", newTickR);
			SmartDashboard.putNumber("Motor 3", newTickL);
			
			SmartDashboard.putNumber("Total Distance", GlobalMapping.sigmaD);
			SmartDashboard.putNumber("Theta", GlobalMapping.theta);
			
			SmartDashboard.putNumber("dSigmaD", dSigmaD);
			SmartDashboard.putNumber("dTicksR", dTickR);
			SmartDashboard.putNumber("dTicksR", dTickL);
			SmartDashboard.putNumber("x-position", GlobalMapping.x);
			SmartDashboard.putNumber("y-position", GlobalMapping.y);
		}
	
	}
	
	/**
	 * This function helps drive the robot to precise coordinates on the field 
	 */
	//TODO: Modularization
	public void driveTo(double xOnField, double yOnField, double theta) {
		double turnTheta = Math.atan2(GlobalMapping.y - yOnField, GlobalMapping.x - xOnField);
		this.MoveRobotTurnInPlace(turnTheta - GlobalMapping.theta, Speed.MEDIUM);
		this.MoveRobotStraight(Math.sqrt(Math.pow((GlobalMapping.y-yOnField), 2) + Math.pow((GlobalMapping.x-xOnField), 2)), Speed.MEDIUM);
		this.MoveRobotTurnInPlace(theta - GlobalMapping.theta, Speed.MEDIUM);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	

}//end of class
