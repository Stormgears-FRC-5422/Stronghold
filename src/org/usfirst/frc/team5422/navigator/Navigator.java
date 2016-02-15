package org.usfirst.frc.team5422.navigator;


import edu.wpi.first.wpilibj.Notifier;

/*
 * @author Mayank
 */

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.*;


public class Navigator extends Subsystem{

	private boolean isRunning;
	
	Notifier thread; 
	
	public Navigator(){

		//right is Driver.talon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder); [0]
		//left is Driver.talon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder); [1]
		
		//Driver.talons[0] is right
		//Driver.talon[1] is left
		
		
		thread = new Notifier(new GlobalMapping());
		thread.startPeriodic(0.001);
		
		System.out.println("Before resetting global positions : " + GlobalMapping.getX() + " and " + GlobalMapping.getY() + " and " + GlobalMapping.getTheta());
		
		GlobalMapping.resetValues();
		
		System.out.println("After resetting global positions : " + GlobalMapping.getX() + " and " + GlobalMapping.getY() + " and " + GlobalMapping.getTheta());
		
		
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
	
	
	
	private void StopRunning(){
		
		Driver.talon[0].set(0.0);
		Driver.talon[1].set(0.0);
		isRunning = false;
		
	}
	
	private boolean Running() {
		return isRunning;
	}

	public void trapWheelTicks(double rTicks, double lTicks, double lVelRPM, double rVelRPM){
		//dummy function (actually written elsewhere by aditya)
		
		Driver.talon[0].set(Math.signum(rTicks)*0.4);
		Driver.talon[1].set(-Math.signum(lTicks)*0.4);
		
	}
	
	private void rotateToTheta(double theta, double rpmR, double rpmL){
		
		System.out.format("[rotate to] %4.3g (rad)\n", theta);
		
		double relInitTheta = theta - GlobalMapping.getTheta();
		
		if(relInitTheta > Math.PI){
			relInitTheta -= 2*Math.PI;
		}else if(relInitTheta < -Math.PI){
			relInitTheta += 2*Math.PI;
		}
		
		System.out.format("[rotate by] %4.3g (rad)\n", relInitTheta );
		
		double lTicksDest = -StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		double rTicksDest = StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		
		trapWheelTicks(rTicksDest, lTicksDest, rpmR, rpmL);
		
		isRunning = true;
		
		while(Running()){
			
			if(Math.abs(GlobalMapping.getTheta() - theta) <= 0.1){
				System.out.println("stopped turning");
				StopRunning();
			}
			
		}
	}
	
	public void moveByDistance(double targDistance, double rpm){
		
		System.out.format("[translate by] %.3g (in)\n", targDistance );
		
		
		double startDistance = GlobalMapping.getSigmaD();
		
		double tickDist = targDistance/StrongholdConstants.INCHES_PER_TICK;
		
		trapWheelTicks(tickDist, tickDist, rpm, rpm);
		
		isRunning = true;
		
		while(Running()){
			
			if(GlobalMapping.getSigmaD() - startDistance >= targDistance){
				StopRunning();
			}
		
		}
		
	}
	
	
	/**
	 * This function helps drive the robot to precise coordinates on the field 
	 */
	//TODO: Modularization
	public void driveTo(double xField, double yField, double thetaField){
		
		System.out.println("In Drive To Current GP is : " + GlobalMapping.getX() + " and " + GlobalMapping.getY() + " and " + GlobalMapping.getTheta());

		double xRel = xField - GlobalMapping.getX();
		double yRel = yField - GlobalMapping.getY();
		
		double targInitTheta = Math.atan2(yRel, xRel);
		
		if(targInitTheta < 0){
			targInitTheta+=2*Math.PI;
		}
		
		double rpm = 0.2*60;

		System.out.println("In Drive To New GP values are : " + " and " + targInitTheta);
		
		rotateToTheta(targInitTheta, rpm, rpm);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		System.out.println("In Drive To New GP values are : " + " and " + targInitTheta);
		
		moveByDistance(targDistance, rpm);
		
		rotateToTheta(thetaField, rpm, rpm);
		
		System.out.println("Done.");
		
	}
	
	public void driveTo(double xField, double yField){
		
		double xRel = xField - GlobalMapping.getX();
		double yRel = yField - GlobalMapping.getY();
		
		double targInitTheta = Math.atan2(yRel, xRel);
		
		if(targInitTheta < 0){
			targInitTheta+=2*Math.PI;
		}
		
		double rpm = 0.2*60;
		
		rotateToTheta(targInitTheta, rpm, rpm);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		
		moveByDistance(targDistance, rpm);
		
		System.out.println("Done.");
		
	}
	
	public void driveTo(double thetaField){
		
		double rpm = 0.2*60;
		
		rotateToTheta(thetaField, rpm, rpm);
		
		System.out.println("Done.");
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	
}//end of class
