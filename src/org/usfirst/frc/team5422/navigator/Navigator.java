package org.usfirst.frc.team5422.navigator;


/*
 * @author Mayank
 */

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.*;


public class Navigator extends Subsystem{
	
	
	//this is all going to change
	private CANTalon motor1;
	private CANTalon motor2;
	private CANTalon motor3;
	private CANTalon motor4;

	private boolean isRunning;
	
	private double oldTickR;
	private double oldTickL;
	
	
	
	public Navigator(){
		motor1 = new CANTalon(1);
		motor2 = new CANTalon(2);
		motor3 = new CANTalon(3);
		motor4 = new CANTalon(4);
		
		motor2.changeControlMode(TalonControlMode.PercentVbus);
		motor3.changeControlMode(TalonControlMode.PercentVbus);
		
		motor2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		motor2.setEncPosition(0);
		motor3.setEncPosition(0);
		
		motor1.changeControlMode(TalonControlMode.Follower);
		motor4.changeControlMode(TalonControlMode.Follower);
		
		motor1.set(2);
		motor4.set(3);
		
		GlobalMapping.resetValues();
		
		updateGP(0,0);
		
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
	
	//needs to be called repeatedly
	public void updateGP(double newTickR, double newTickL){
		
		double dTickR = newTickR - oldTickR;
		double dTickL = newTickL - oldTickL;
		
		oldTickR = newTickR;
		oldTickL = newTickL;
		
		//inches
		double dSigmaD = (dTickR+dTickL)*StrongholdConstants.INCHES_PER_TICK/2;
		
		//inches
		double dTheta = ((dTickR - dTickL)*StrongholdConstants.INCHES_PER_TICK)/(StrongholdConstants.WHEEL_BASE);
		
		GlobalMapping.addTotalDistance(dSigmaD);
		
		GlobalMapping.addTotalRotation(dTheta);
		
		double dX = Math.cos(GlobalMapping.theta)*dSigmaD;
		double dY = Math.sin(GlobalMapping.theta)*dSigmaD;
		
		GlobalMapping.addCurrentPosition(dX, dY);
		
		SmartDashboard.putNumber("[GP] Total Distance(in)", GlobalMapping.sigmaD);
		SmartDashboard.putNumber("[GP] Theta(r)", GlobalMapping.theta);
		
		SmartDashboard.putNumber("[GP] x-position(in)", GlobalMapping.x);
		SmartDashboard.putNumber("[GP] y-position(in)", GlobalMapping.y);
	}
	
	
	private void StopRunning(){
		
		motor2.set(0.0);
		motor3.set(-0.0);
		isRunning = false;
		
	}
	
	private boolean Running() {
		return isRunning;
	}

	public void trapWheelTicks(double rTicks, double lTicks, double lVelRPM, double rVelRPM){
		//dummy function (actually written elsewhere by aditya)
		
		motor2.set(Math.signum(rTicks)*0.4);
		motor3.set(-Math.signum(lTicks)*0.4);
		
	}
	
	public void rotateToTheta(double theta, double rpmR, double rpmL){
		
		System.out.format("[rotate to] %4.3g (rad)", theta);
		
		double relInitTheta = theta - GlobalMapping.theta;
		
		if(relInitTheta > Math.PI){
			relInitTheta -= 2*Math.PI;
		}else if(relInitTheta < -Math.PI){
			relInitTheta += 2*Math.PI;
		}
		
		System.out.format("[rotate by] %4.3g (rad)", relInitTheta );
		
		double lTicksDest = StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		double rTicksDest = -StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		
		
		
		//substitute function
		trapWheelTicks(rTicksDest, lTicksDest, rpmR, rpmL);
		
		isRunning = true;
		
		while(Running()){
			
			updateGP(motor3.getEncPosition(), -motor2.getEncPosition());
			
			if(Math.abs(GlobalMapping.theta - theta) <= 0.01){
				StopRunning();
			}
			
		}
	}
	
	public void moveByDistance(double targDistance, double rpm){
		
		System.out.format("[translate by] %.3g (in)", targDistance );
		
		GlobalMapping.sigmaD = 0;
		
		double tickDist = targDistance/StrongholdConstants.INCHES_PER_TICK; 
		
		trapWheelTicks(tickDist, tickDist, rpm, rpm);
		
		isRunning = true;
		
		while(Running()){
			
			updateGP(motor3.getEncPosition(), -motor2.getEncPosition());
			
			if(GlobalMapping.sigmaD >= targDistance){
				StopRunning();
			}
		
		}
		
	}
	
	
	/**
	 * This function helps drive the robot to precise coordinates on the field 
	 */
	//TODO: Modularization
	public void driveTo(double xField, double yField, double thetaField){
		
		double xRel = xField - GlobalMapping.x;
		double yRel = yField - GlobalMapping.y;
		
		double targInitTheta = Math.atan2(yRel, xRel);
		
		if(targInitTheta < 0){
			targInitTheta+=2*Math.PI;
		}
		
		double rpm = 0.2*60;
		
		rotateToTheta(targInitTheta, rpm, rpm);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		
		moveByDistance(targDistance, rpm);
		
		rotateToTheta(thetaField, rpm, rpm);
		
		System.out.println("Done.");
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	
}//end of class
