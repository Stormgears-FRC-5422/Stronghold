package org.usfirst.frc.team5422.sensors;

import java.util.ArrayList;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class UltrasonicThread implements Runnable{

	private UltrasonicSensor us;
	
	public NetworkTable usTable;
	
	public UltrasonicThread() 
	{		
		us = new UltrasonicSensor();
		usTable = NetworkTable.getTable(StrongholdConstants.uVal);
	}
	
	public synchronized void run() 
	{
		
		ArrayList<Integer> distanceList = us.getDistanceList();
					
		usTable.putValue(StrongholdConstants.f, us.getDistanceList().get(0));
		usTable.putValue(StrongholdConstants.r, us.getDistanceList().get(1));
		usTable.putValue(StrongholdConstants.b, us.getDistanceList().get(2));
		usTable.putValue(StrongholdConstants.l, us.getDistanceList().get(3));
		
	}
}
	
