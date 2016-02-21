package org.usfirst.frc.team5422.navigator;


import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/*
 * @author Mayank
 */

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.*;


public class Navigator extends Subsystem{

	
	Notifier thread; 
	
	private int currentProfileID = 0;
	
	public boolean moveTrapezoidal = true;
	
	private NetworkTable netTable = NetworkTable.getTable("Trapezoid");
	
	public Navigator(){

		//right is RhinoDriver.talon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder); [0]
		//left is RhinoDriver.talon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder); [1]
		
		//RhinoDriver.talons[0] is right
		//RhinoDriver.talon[1] is left
		
		
		thread = new Notifier(new GlobalMapping());
		thread.startPeriodic(0.001);

		GlobalMapping.resetValues(0,0,Math.PI/2);
		
		
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

	private void trapWheelTicks(double rTicks, double lTicks, double lVelRPM, double rVelRPM, int tableID){
		//dummy function (actually written elsewhere by aditya)
		
		StrongholdRobot.driver.moveTrapezoid((int)lTicks, (int)rTicks, lVelRPM, rVelRPM, tableID);
		
	}
	
	private void speedWheelTicks(double rTicks, double lTicks){
		
		RhinoDriver.talon[0].set(Math.signum(rTicks)*0.4);
		RhinoDriver.talon[1].set(-Math.signum(lTicks)*0.4);
		
	}
	
	private void rotateToTheta(double theta, double rpmR, double rpmL){
		System.out.println("rotateToTheta Entered " + Timer.getFPGATimestamp());
		theta = GlobalMapping.reduceRadiansUtil(theta);
		double relInitTheta = theta - GlobalMapping.getInstance().getTheta();
		
		if(relInitTheta > Math.PI){
			relInitTheta -= 2*Math.PI;
		}else if(relInitTheta < -Math.PI){
			relInitTheta += 2*Math.PI;
		}
		
		System.out.format("[GP][robot at] (%4.3g, %4.3g) @ %4.3g (in)\n", GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), GlobalMapping.getInstance().getTheta());
		System.out.format("[GP][rotate to] %4.3g [rotate by] %4.3g (rad)\n", theta, relInitTheta );
		
		
		double lTicksDest = -StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		double rTicksDest = StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		
		if(rTicksDest > 500){
			if(moveTrapezoidal){
				trapWheelTicks(rTicksDest, lTicksDest, rpmR, rpmL, currentProfileID);
				waitForTrapezoidalFinish();
			
				System.out.println("rotateToTheta Done " + Timer.getFPGATimestamp());
			}else{
				lTicksDest*=-1;
				rTicksDest*=-1;
				speedWheelTicks(rTicksDest, lTicksDest);
				waitForSpeedRotationalFinish(relInitTheta);
				speedWheelTicks(0, 0);
			}
		}else{
			System.out.println("[Nav] calculated ticks is too small, skipping trap instead");
		}
		
		System.out.println("rotateToTheta Done " + Timer.getFPGATimestamp());
	}
	
	private void waitForTrapezoidalFinish() {
		while(true){
			if(netTable.getString("Trap Status", "running").equals("finished") && (netTable.getNumber("Trap ID", -1) == currentProfileID)){
				currentProfileID++;
				break;
			}
		}
		
	}
	
	private void waitForSpeedLinearFinish(double distance){
		double start = GlobalMapping.getInstance().getSigmaD();
		while(GlobalMapping.getInstance().getSigmaD() - start < distance){
			if(Math.abs(GlobalMapping.getInstance().getSigmaD() - start) > Math.abs(distance)){
				System.out.println("relDist = " + (GlobalMapping.getInstance().getTheta() - start));
				System.out.println("targRelDist = " + Math.abs(distance));
			}
		}
	}
	
	private void waitForSpeedRotationalFinish(double relTheta){
		double start = GlobalMapping.getInstance().getTheta();
		
		//System.out.println("BEFORE loop Current Theta is " + GlobalMapping.getTheta());
		while(Math.abs(GlobalMapping.getInstance().getTheta() - start) < Math.abs(relTheta)){
			
			if(Math.abs(GlobalMapping.getInstance().getTheta() - start) > Math.abs(relTheta)){
				System.out.println("relTheta = " + (GlobalMapping.getInstance().getTheta() - start));
				System.out.println("targRelTheta = " + Math.abs(relTheta));
			}
			
			
			
		}
	}

	private void moveByDistance(double targDistance, double rps){
		System.out.println("moveByDistance Entered " + Timer.getFPGATimestamp());
		System.out.format("[GP][robot at] (%4.3g, %4.3g) @ %4.3g (in)\n", GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), GlobalMapping.getInstance().getTheta());
		System.out.format("[GP][translate by] %.3g (in)\n", targDistance );
		
		double tickDist = targDistance/StrongholdConstants.INCHES_PER_TICK;
		
		if(tickDist > 500){
			if(moveTrapezoidal){
				trapWheelTicks(tickDist, tickDist, rps, rps, currentProfileID);
				waitForTrapezoidalFinish();
			}else{
				speedWheelTicks(tickDist, tickDist);
				waitForSpeedLinearFinish(targDistance);
				speedWheelTicks(0, 0);
			}
		}else{
			System.out.println("[Nav] calculated ticks is too small, skipping trap instead");
		}
		
		System.out.println("moveByDistance Done " + Timer.getFPGATimestamp());
		
	}
	
	
	/**
	 * This function helps drive the robot to precise coordinates on the field 
	 */
	//TODO: Modularization
	public void driveTo(double xField, double yField, double thetaField){
		
		double xRel = xField - GlobalMapping.getInstance().getX();
		double yRel = yField - GlobalMapping.getInstance().getY();
		
		double targInitTheta = GlobalMapping.reduceRadiansUtil(Math.atan2(yRel, xRel));
		
		double rps = 3;
		
		rotateToTheta(targInitTheta, rps, rps);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		
		moveByDistance(targDistance, rps);
		
		thetaField = GlobalMapping.reduceRadiansUtil(thetaField);
		
		rotateToTheta(thetaField, rps, rps);
		
	}
	
	public void driveTo(double xField, double yField){
		
		double xRel = xField - GlobalMapping.getInstance().getX();
		double yRel = yField - GlobalMapping.getInstance().getY();
		
		double targInitTheta = Math.atan2(yRel, xRel);
		
		if(targInitTheta < 0){
			targInitTheta+=2*Math.PI;
		}
		
		double rps = 3;
		
		rotateToTheta(targInitTheta, rps, rps);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		
		moveByDistance(targDistance, rps);
		
	}
	
	public void turnTo(double thetaField){
		System.out.println("turnTo Entered " + Timer.getFPGATimestamp());
		
		double rps = 3;
		
		thetaField = GlobalMapping.reduceRadiansUtil(thetaField);
		
		rotateToTheta(thetaField, rps, rps);
		System.out.println("turnTo Done " + Timer.getFPGATimestamp());
		
	}
	
	public void turnTo(double xField, double yField){
		double xRel = xField - GlobalMapping.getInstance().getX();
		double yRel = yField - GlobalMapping.getInstance().getY();
		
		double targInitTheta = GlobalMapping.reduceRadiansUtil(Math.atan2(yRel, xRel));
		
		double rps = 3;
		
		rotateToTheta(targInitTheta, rps, rps);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	
}//end of class
