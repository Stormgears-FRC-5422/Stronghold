package org.usfirst.frc.team5422.navigator.trapezoidal;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

public class TrapezoidControl {
	

	

	
	public static double [][] motionProfileUtility(double rotations, double maxVel, int startingTicks) {
		int targetTicks = (int)(rotations * StrongholdConstants.ENCODER_TICKS_RESOLUTION);
		double velRPM = maxVel * StrongholdConstants.ENCODER_TICKS_RESOLUTION;
		double accel = velRPM;
		
		
		return ultimateProfileDoubleArray(targetTicks, startingTicks, accel, velRPM);
		
	}
	
	
	public static double [][] ultimateProfileDoubleArray(int targetTicks, int startingTicks, double maxAccel, double maxVel) {
		

		double[][] trajectoryPointArray = new double[(int)(Math.round(determineTime(Math.abs(targetTicks), maxAccel, maxVel)/10.0)) + 1][3];
		
		double currentTime = 0;
		
		for(int i = 0; i < trajectoryPointArray.length; i ++) {
			double [] pointValues = velocityProfileArray(Math.abs(targetTicks), currentTime, maxAccel, maxVel);
			
			trajectoryPointArray[i][0] = pointValues[0]/(double)(StrongholdConstants.ENCODER_TICKS_RESOLUTION) + Math.abs(startingTicks/(double)(StrongholdConstants.ENCODER_TICKS_RESOLUTION)); //in rotations now
			trajectoryPointArray[i][1] = pointValues[1]/(double)(StrongholdConstants.ENCODER_TICKS_RESOLUTION) * 60; //RPM now
			trajectoryPointArray[i][2] = 10; //time dur in ms
			
			currentTime += 0.01;
		}
		
		return trajectoryPointArray;
		
	}
	
	


	
	//returns total time needed for move in milliseconds
	public static int determineTime(int targetTicks, double maxAccel, double maxVel) {
	
		double startConstantVelPos = maxVel * maxVel / (2 * maxAccel);
		double startConstantVelTime = maxVel/maxAccel;
		
		double timeTotal = 0, startDecelPos = 0, startDecelTime = 0;
		
		if(2 * startConstantVelPos > targetTicks) {
			startDecelPos = targetTicks/2;
			startDecelTime = Math.sqrt(2 * startDecelPos/maxAccel);
			timeTotal = 2 * startDecelTime;
		}
		
		else {
			startDecelPos = targetTicks - startConstantVelPos;
			startDecelTime = startConstantVelTime + (startDecelPos - startConstantVelPos)/maxVel;
			timeTotal = startConstantVelTime + startDecelTime;
		}
		
		return (int)Math.round(timeTotal * 1000);
	}


public static double[] velocityProfileArray(int targetTicks, double time, double maxAccel, double maxVel) {
		
		double [] outputArray = new double[2];
		double outputTicks = 0, velocity = 0;
		
		double startConstantVelPos = maxVel * maxVel / (2 * maxAccel);
		double startConstantVelTime = maxVel/maxAccel;
		
		double timeTotal = 0, startDecelPos = 0, startDecelTime = 0;
		
		if(2 * startConstantVelPos > targetTicks) {
			startDecelPos = targetTicks/2;
			startDecelTime = Math.sqrt(2 * startDecelPos/maxAccel);
			timeTotal = 2 * startDecelTime;
		}
		
		else {
			startDecelPos = targetTicks - startConstantVelPos;
			startDecelTime = startConstantVelTime + (startDecelPos - startConstantVelPos)/maxVel;
			timeTotal = startConstantVelTime + startDecelTime;
		}
		
		
		if(time < startConstantVelTime && time < startDecelTime) {
			outputTicks = 0.5 * maxAccel * time * time;
			velocity = maxAccel * time;
		}
		
		else if(time >= startConstantVelTime && time < startDecelTime) {
			outputTicks = 0.5 * maxAccel * startConstantVelTime * startConstantVelTime + maxVel *(time - startConstantVelTime);
			velocity = maxVel;
		}
		
		else if(time >= startDecelTime && time < timeTotal) {
			outputTicks = targetTicks - 0.5 * maxAccel * (timeTotal - time) * (timeTotal - time);
			velocity = maxAccel * (timeTotal - time);
		}
		
		else if(time >= timeTotal) {
			outputTicks = targetTicks;
		}
		
		outputArray[0] = outputTicks;
		outputArray[1] = velocity;
		
		return outputArray;
		
		//return (int)Math.round(outputTicks); //round and cast to int, since encoder ticks must be integer values
	}
	
	
	
	

	
}

