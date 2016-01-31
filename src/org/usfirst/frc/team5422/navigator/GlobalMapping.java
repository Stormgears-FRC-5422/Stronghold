package org.usfirst.frc.team5422.navigator;

public class GlobalMapping {
	
	public static double x=0;
	public static double y=0;
	public static double theta=Math.PI/2;
	
	public static double sigmaD=0;
	
	public static double reduceAngleRad(double radians){
		//reduce theta to theta (mod Math.PI)
		double n = radians/(2*Math.PI);
		double remainder = n - (int)n;
		radians = remainder*2*Math.PI;
		
		if(radians > Math.PI){
			radians -= Math.PI;
		}else if(radians < -Math.PI){
			radians += Math.PI;
		}
		
		return radians;
	}
	
	public static void addTotalDistance(double dSigmaD) {
		sigmaD += dSigmaD;
		
	}

	public static void addTotalRotation(double dTheta) {
		theta += dTheta;
		theta = reduceAngleRad(theta);
		
	}

	public static void addCurrentPosition(double dX, double dY) {
		x+=dX;
		y+=dY;
		
	}
	
	
	

}
