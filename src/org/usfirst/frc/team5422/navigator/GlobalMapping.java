package org.usfirst.frc.team5422.navigator;

/*
 * @author Mayank
 */

public class GlobalMapping {
	
	public static double x = 0;
	public static double y = 0;
	public static double theta = Math.PI/2;
	
	public static double sigmaD=0;
	
	public static void resetValues(){
		x=0;
		y=0;
		theta=Math.PI/2;
	}
	
	public static void addTotalDistance(double dSigmaD) {
		sigmaD += dSigmaD;
		
	}

	public static void addTotalRotation(double dTheta) {
		theta += dTheta;
		theta %=2*Math.PI;
		if(theta < 0){
			theta += 2*Math.PI;
		}
		
	}

	public static void addCurrentPosition(double dX, double dY) {
		x+=dX;
		y+=dY;
		
	}
	
	
	

}
