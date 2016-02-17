package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author Mayank
 */

public class GlobalMapping implements Runnable{
	
	private static double x = 0;
	private static double y = 0;
	private static double theta = Math.PI/2;
	
	private static double sigmaD=0;
	
	private static double oldTickR;
	private static double oldTickL;
	
	/*
	 * returns an equivalent theta between 0 and 2*Math.PI 
	 */
	public static double reduceRadiansUtil(double theta){
		return ((theta % 2*Math.PI) + 2*Math.PI) % 2*Math.PI;
	}
	
	public static void resetValues(double xField, double yField, double thetaField){
		x=xField;
		y=yField;
		theta = reduceRadiansUtil(thetaField);
		
		
		
		System.out.format("[GP] Robot reset values to (%.3g, %.3g) @ %.3g\n", x, y, theta);
	}
	
	public static void addTotalDistance(double dSigmaD) {
		sigmaD += dSigmaD;
		
	}

	public static void addTotalRotation(double dTheta) {
		theta = reduceRadiansUtil(theta + dTheta);
	}

	public static void addCurrentPosition(double dX, double dY) {
		x+=dX;
		y+=dY;
		
	}
	
	//this is called in another thread, so we need synchronized so other threads cannot call it concurrently
	synchronized private static void updateGP(double newTickR, double newTickL){
		SmartDashboard.putNumber("[GP] Motor (R) Encoder Ticks", newTickR);
		SmartDashboard.putNumber("[GP] Motor (L) Encoder Ticks", newTickL);
		
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

	@Override
	public void run() {
		
		updateGP(0,0);
		
		while((Driver.talon[0]!=null) && (Driver.talon[1]!=null)){
		
			updateGP(-Driver.talon[0].getEncPosition(), Driver.talon[1].getEncPosition());
		
		}
		
	}
	
	public static double getX(){
		return x;
	}
	
	public static double getY(){
		return y;
	}
	
	public static double getTheta(){
		return reduceRadiansUtil(theta);
	}
	
	public static double getSigmaD(){
		return sigmaD;
	}
	
	

}
