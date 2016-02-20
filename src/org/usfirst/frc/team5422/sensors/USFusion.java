package org.usfirst.frc.team5422.sensors;

import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class USFusion 
{
	//	UltrasonicSensor us;
	//	GyroAccel ga;
	//	GlobalMapping gp = new GlobalMapping();
	
	GlobalMapping gp = new GlobalMapping();
	NetworkTable us = NetworkTable.getTable(StrongholdConstants.uVal);
	NetworkTable gyro = NetworkTable.getTable(StrongholdConstants.gVal);
	NetworkTable accel = NetworkTable.getTable(StrongholdConstants.aVal);
	
	public USFusion()
	{
	//	us = new UltrasonicSensor();
	//	ga = new GyroAccel();
	}
	
	public double getGPX()
	{
		return GlobalMapping.getInstance().getX();
	}
	
	public double getGPY()
	{
		return GlobalMapping.getInstance().getY();
	}
	
	public void readUltrasonic()
	{
		us.getValue(StrongholdConstants.r, -1);
		us.getValue(StrongholdConstants.b, -1);
		us.getValue(StrongholdConstants.l, -1);

//		checkFront();
	}
	
	public double updateX()
	{
		double frontRange = (Double)us.getValue(StrongholdConstants.f, -1);
		double rightRange = (Double)us.getValue(StrongholdConstants.r, -1);
		double backRange = (Double)us.getValue(StrongholdConstants.b, -1);
		double leftRange = (Double)us.getValue(StrongholdConstants.l, -1);
		
		double newX = 0;
		
		// Left wall
		if(getGPX() <= 30)
		{
			// Facing forward
			if(GlobalMapping.getInstance().getTheta() > Math.toRadians(10) 
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(350))
			{
				newX = leftRange;
			}
			
			// Facing right	
			else if(GlobalMapping.getInstance().getTheta() > Math.toRadians(80) 
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(100))
			{
				newX = leftRange;
			}
			
			// Facing back
			else if(GlobalMapping.getInstance().getTheta() > Math.toRadians(170) 
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(190))
			{
				newX = backRange;
			}
			
			// Facing left
			else if(GlobalMapping.getInstance().getTheta() > Math.toRadians(260)
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(280))
			{
				newX = frontRange;
			}			
		
		// Left wall
		if(getGPX() <= 30)
		{
		// Facing forward
			if(GlobalMapping.getInstance().getTheta() > Math.toRadians(10) 
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(350))
			{
				newX = leftRange;
			}
				
			// Facing right	
			else if(GlobalMapping.getInstance().getTheta() > Math.toRadians(80) 
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(100))
			{
				newX = leftRange;
			}
				
			// Facing back
			else if(GlobalMapping.getInstance().getTheta() > Math.toRadians(170) 
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(190))
			{
				newX = backRange;
			}
				
			// Facing left
			else if(GlobalMapping.getInstance().getTheta() > Math.toRadians(260)
					&& GlobalMapping.getInstance().getTheta() < Math.toRadians(280))
			{
				newX = frontRange;
			}			
		}
		}
		return newX;
	}
	
	private double getDistance(double x1, double y1, double x2, double y2)
	{
		double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		return distance;
	}
	
//	public double compareGyroAngles()
//	{
//		double gTheta = 0;
//
//		if(ga.getGyroStatus())
//	    	{	    
//				gTheta = ga.getAngle();
//    		}
//		double gpTheta = GlobalMapping.getTheta();
//		
//		while(gTheta == 0)
//		{
//			gTheta = ga.getAngle();
//		}
//		
//		while(Math.abs(gpTheta - gTheta) > 5 && gTheta > 0)
//		{
//			gpTheta = gTheta;
//		}
//			
//		return gpTheta;
//	}
//	
//	public void checkDistances()
//	{
//		double frontRange = us.getFrontDistance();
//		double rightRange = us.getRightDistance();
//		double backRange = us.getBackDistance();
//		double leftRange = us.getLeftDistance();
//	
//		double objectX = 0;
//		double objectY = 0; 
//		
//		if(frontRange <= 12) 
//		{
//			GlobalMapping.getX();
//			GlobalMapping.getY();
//		}		
//	}
}
