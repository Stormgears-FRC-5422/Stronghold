package org.usfirst.frc.team5422.sensors;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
	private DigitalOutput lights;
	public static NetworkTable visionTable = NetworkTable.getTable("GRIP/myContoursReport");
	
	public Vision(){
		System.out.println("Vision system constructed");
		lights = new DigitalOutput(StrongholdConstants.RINGLIGHT_PORT);
		lights.set(false);
		System.out.println("lights turned on");
	}
	
	public void turnOffLights() {
		lights.set(false);
	}
	
	public void turnOnLights() {
		lights.set(true);
	}
	
	public static double getCenterX() {
		double [] defaultArray = new double[0];
		double [] visionArray = visionTable.getNumberArray("centerX", defaultArray);
		
		if(visionArray.length > 0)
			return visionArray[0];
		
		else 
			return 0;
	}
	
	public static double getCenterY() {
		double [] defaultArray = new double[0];
		double [] visionArray = visionTable.getNumberArray("centerY", defaultArray);
		
		if(visionArray.length > 0)
			return visionArray[0];                                 
		
		else 
			return 0;
	}
	
	public static double getRadialDistanceVision() {
		double centerY = getCenterY();
		//22.0/48.0
		double viewAngle = Math.toDegrees(Math.atan(.4848) * 2);//convert to degrees | .4848 --> retake measurements of px height at different distances
		
		double difAngle = centerY * viewAngle/600.0;			// 240 --> 600 (vertical px)
		double theta = viewAngle/2.0 + 30.0 - difAngle;			// 21.5 --> new camera angle
		
		double radialDistance = 77.5/Math.tan(Math.toRadians(theta));	// 65.5 --> 90" - camera height
		double power = .3/14 * radialDistance - 3.4;
		System.out.println("original radial distance: " + radialDistance);
		radialDistance = 0.97 * radialDistance + 11;
		//there may be some other modifications that must be made here --> such as changing units of centerY.
	//	double radialDistance = Math.atan(centerY);
	
		return radialDistance;
		
	}
}
