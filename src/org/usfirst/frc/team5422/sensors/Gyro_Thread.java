package org.usfirst.frc.team5422.sensors;

import java.util.ArrayList;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Gyro_Thread implements Runnable 
{
	private GyroAccel ga;	
	int[] gyroValues = new int[2];
	private Notifier gyroLoop;
	
	public NetworkTable gyroTable;
	public NetworkTable accelTable;
		
	double[] values = {0,0};
	
	public Gyro_Thread()
	{
		ga = new GyroAccel();
		gyroLoop = new Notifier(this);
		gyroLoop.startPeriodic(0.01);
		gyroTable = NetworkTable.getTable(StrongholdConstants.gVal);
		accelTable = NetworkTable.getTable(StrongholdConstants.aVal);
	}
	
	public void reset()
	{
		values[0] += ga.getAngle();
		ga.reset();
		values[1] = ga.getAngle();
		values[0] += values[1];
	}
	
	public synchronized void run()
	{
		int counter = 100;
		counter--;
	
		ArrayList<Double> gyro = ga.getGyroList();
		
		gyroTable.putValue(StrongholdConstants.gAngle, ga.getGyroList().get(0));
		gyroTable.putValue(StrongholdConstants.gRotation, ga.getGyroList().get(1));
		
		ArrayList<Double> accel = ga.getAccelerationList();
		accelTable.putValue(StrongholdConstants.aX, ga.getAccelerationList().get(0));
		accelTable.putValue(StrongholdConstants.aY, ga.getAccelerationList().get(1));
		accelTable.putValue(StrongholdConstants.aZ, ga.getAccelerationList().get(2));
		
		if(counter == 0)
		{
			reset();
		}
	}

}
