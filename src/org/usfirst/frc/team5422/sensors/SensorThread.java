package org.usfirst.frc.team5422.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SensorThread implements Runnable{

	private UltrasonicSensor us;
	private GyroAccel ga;
	private Notifier sensorLoop;
	
	public NetworkTable usTable;
	public NetworkTable gyroTable;
	public NetworkTable accelTable;
		
	public SensorThread() 
	{
		
		us = new UltrasonicSensor();
		ga = new GyroAccel();
		sensorLoop = new Notifier(this);
		sensorLoop.startPeriodic(0.01);
		usTable = NetworkTable.getTable("Ultrasonic Value");
		gyroTable = NetworkTable.getTable("Gyro Value");
		accelTable = NetworkTable.getTable("Acceleration Value");

	}
	
	public synchronized void run() 
	{
		ArrayList<Integer> distanceList = us.getDistanceList();
		usTable.putValue("Front Distance", us.getDistanceList().get(0));
		usTable.putValue("Right Distance", us.getDistanceList().get(1));
		usTable.putValue("Back Distance", us.getDistanceList().get(2));
		usTable.putValue("Left Distance", us.getDistanceList().get(3));
		
		ga.reset();
		ArrayList<Double> gyro = ga.getGyroList();
		gyroTable.putValue("Gyro Angle", ga.getGyroList().get(0));
		gyroTable.putValue("Gyro Rotation Rate", ga.getGyroList().get(1));
		
		ArrayList<Double> accel = ga.getAccelerationList();
		usTable.putValue("X-Accel", ga.getAccelerationList().get(0));
		usTable.putValue("Y-Accel", ga.getAccelerationList().get(1));
		usTable.putValue("Z-Accel", ga.getAccelerationList().get(2));
		
	}
}
	
