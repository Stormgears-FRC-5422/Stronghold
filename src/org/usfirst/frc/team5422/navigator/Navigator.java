package org.usfirst.frc.team5422.navigator;


import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/**
 * The <code>Navigator</code> subsystem integrates 
 * sensory information and maneuvers of the <code>Stronghold Robot</code> 
 * 
 * @author Mayank Mali
 */

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.*;

public class Navigator extends Subsystem{
	
	private double rps = 2;
	
	/**Always updates <code>GlobalMapping</code> position through encoder ticks.*/
	static Notifier gpThread; 
	/**Updates <code>GlobalMapping</code> position as the robot passes through a defense.*/
	static Notifier defenseManeuverThread;
	
	private int currentProfileID = 0;
	
	public boolean moveTrapezoidal = true;
	
	private NetworkTable trapTable = NetworkTable.getTable("Trapezoid");
	
	public Navigator(){
		
		//DefenseManeuver.getInstance().updateDefenseManeuver();
		
		//right is RhinoDriver.talon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder); [0]
		//left is RhinoDriver.talon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder); [1]
		
		//RhinoDriver.talons[0] is right
		//RhinoDriver.talon[1] is left
		System.out.println("[Nav] Navigator created");
		
		gpThread = new Notifier(GlobalMapping.getInstance());
		gpThread.startPeriodic(0.001);
		
		//defenseManeuverThread = new Notifier(DefenseManeuver.getInstance());
		
		GlobalMapping.resetValues(0,0,Math.PI/2);
		
		
	}
	
	public synchronized static void startDefenseCrossManeuver(){
		
		defenseManeuverThread.startPeriodic(0.001);
		
		System.out.println("[Nav] started cross Maneuver");
	}
	
	public synchronized static void stopDefenseCrossManeuver(){
		
		defenseManeuverThread.stop();
		
		System.out.println("[Nav] stopped cross Maneuver");
	}
	/*
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
	*/
	private void trapWheelTicks(double rTicks, double lTicks, double lVelRPM, double rVelRPM, int tableID){
		//dummy function (actually written elsewhere by aditya)
		
		StrongholdRobot.driver.moveTrapezoid((int)lTicks, (int)rTicks, lVelRPM, rVelRPM, tableID);
		
	}
	
	private void speedWheelTicks(double rTicks, double lTicks){
		
		StrongholdRobot.driver.getDriveTalonRightMaster().set(Math.signum(rTicks)*0.4);
		StrongholdRobot.driver.getDriveTalonLeftMaster().set(-Math.signum(lTicks)*0.4);
		
	}
	
	private void rotateToTheta(double theta, double rpmR, double rpmL){
		//Timer.delay(2);
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
		
		if(Math.abs(rTicksDest) > 10){
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
			System.out.println("Encoder Ticks: " + "R: " + rTicksDest + "L: " + lTicksDest);
		}
		
		System.out.println("rotateToTheta Done " + Timer.getFPGATimestamp());
	}
	
	
	
	
	private void rotateToThetaRelative(double theta, double rpmR, double rpmL){
		//Timer.delay(2);
		System.out.println("rotateToTheta Entered " + Timer.getFPGATimestamp());
//		theta = GlobalMapping.reduceRadiansUtil(theta);
		double relInitTheta = theta;
		
//		if(relInitTheta > Math.PI){
//			relInitTheta -= 2*Math.PI;
//		}else if(relInitTheta < -Math.PI){
//			relInitTheta += 2*Math.PI;
//		}
		
		System.out.format("[GP][robot at] (%4.3g, %4.3g) @ %4.3g (in)\n", GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), GlobalMapping.getInstance().getTheta());
		System.out.format("[GP][rotate to] %4.3g [rotate by] %4.3g (rad)\n", theta, relInitTheta );
		
		
		double lTicksDest = -StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		double rTicksDest = StrongholdConstants.WHEEL_BASE/2*relInitTheta/StrongholdConstants.INCHES_PER_TICK;
		
		if(Math.abs(rTicksDest) > 10){
			if(moveTrapezoidal){
				currentProfileID++;
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
			System.out.println("Encoder Ticks: " + "R: " + rTicksDest + "L: " + lTicksDest);
		}
		
		System.out.println("rotateToTheta Done " + Timer.getFPGATimestamp());
	}
	
	private void waitForTrapezoidalFinish() {
		while(true){
			String tempString = StrongholdRobot.driver.getTrapStatus();
			int id = StrongholdRobot.driver.getTrapID();
		//	System.out.println("[Nav} navID "+ currentProfileID +":" + tempString+ " netID:" + id);
			if(tempString.equals("finished") && (id == currentProfileID)){
				//currentProfileID++;
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
		//Timer.delay(2);
		System.out.println("moveByDistance Entered " + Timer.getFPGATimestamp());
		System.out.format("[GP][robot at] (%4.3g, %4.3g) @ %4.3g (in)\n", GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), GlobalMapping.getInstance().getTheta());
		System.out.format("[GP][translate by] %.3g (in)\n", targDistance );
		
		double tickDist = targDistance/StrongholdConstants.INCHES_PER_TICK;
		
		if(Math.abs(tickDist) > 10){
			if(moveTrapezoidal){
				currentProfileID++;
				trapWheelTicks(tickDist, tickDist, rps, rps, currentProfileID);
				waitForTrapezoidalFinish();
			}else{
				speedWheelTicks(tickDist, tickDist);
				waitForSpeedLinearFinish(targDistance);
				speedWheelTicks(0, 0);
			}
		}else{
			System.out.println("[Nav] calculated ticks is too small, skipping trap instead");
			System.out.println("Encoder Ticks: " + "R: " + tickDist + "L: " + tickDist);
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
		
		
		
		rotateToTheta(targInitTheta, rps, rps);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		
		moveByDistance(targDistance, rps);
		
		thetaField = GlobalMapping.reduceRadiansUtil(thetaField);
		
		rotateToTheta(thetaField, rps, rps);
		
	}
	
	public void driveTo(double xField, double yField){
		
		System.out.println("navigator driving to..." + xField + ":" + yField);
		
		double xRel = xField - GlobalMapping.getInstance().getX();
		double yRel = yField - GlobalMapping.getInstance().getY();
		
		double targInitTheta = Math.atan2(yRel, xRel);
		
		if(targInitTheta < 0){
			targInitTheta+=2*Math.PI;
		}
		
		
		
		rotateToTheta(targInitTheta, rps, rps);
		
		double targDistance = Math.sqrt(xRel*xRel + yRel*yRel);
		
		moveByDistance(targDistance, rps);
		
	}
	
	public void turnTo(double thetaField){
		System.out.println("turnTo Entered " + Timer.getFPGATimestamp());
		
		
		
		thetaField = GlobalMapping.reduceRadiansUtil(thetaField);
		
		rotateToTheta(thetaField, rps, rps);
		System.out.println("turnTo Done " + Timer.getFPGATimestamp());
		
	}
	
	public void turnTo(double xField, double yField){
		double xRel = xField - GlobalMapping.getInstance().getX();
		double yRel = yField - GlobalMapping.getInstance().getY();
		
		double targInitTheta = GlobalMapping.reduceRadiansUtil(Math.atan2(yRel, xRel));
		
		
		
		rotateToTheta(targInitTheta, rps, rps);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public void setRPS(double rps) {
		this.rps = rps;
	}
	
	
	
	public void turnToRelative(double thetaField){
//		System.out.println("turnTo Entered " + Timer.getFPGATimestamp());
//		thetaField = GlobalMapping.reduceRadiansUtil(thetaField);
		
		SmartDashboard.putNumber("Theta", 1.07 * thetaField);
		rotateToThetaRelative(1.07 * thetaField, rps, rps);
		System.out.println("turnTo Done " + Timer.getFPGATimestamp());
		
	}
	
	
	
}//end of class
