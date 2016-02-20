package org.usfirst.frc.team5422.sensors;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GyroFusion 
{

	NetworkTable gyro = NetworkTable.getTable(StrongholdConstants.gVal);
	NetworkTable accel = NetworkTable.getTable(StrongholdConstants.aVal);
	
	public GyroFusion()
	{
	//	us = new UltrasonicSensor();
	//	ga = new GyroAccel();
	}
	
	public void readGyro()
	{
		double x = (double)gyro.getValue(StrongholdConstants.gAngle, 0);
		gyro.getValue(StrongholdConstants.gRotation, 0);
	}
	
	public void readAccel()
	{
		accel.getValue(StrongholdConstants.aX, 0);
		accel.getValue(StrongholdConstants.aY, 0);
		accel.getValue(StrongholdConstants.aZ, 0);

	}

}
