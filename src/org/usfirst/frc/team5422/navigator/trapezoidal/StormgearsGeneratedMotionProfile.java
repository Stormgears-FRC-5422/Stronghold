package org.usfirst.frc.team5422.navigator.trapezoidal;

public class StormgearsGeneratedMotionProfile {

	public static int kNumPoints = 0;
	
	public static double [][] points;
	
	public static void setPoints(int targetTicks) {
		int totalTimeMs = determineTime(80000, 100, 400);
		
		points = new double [(int)(Math.round(totalTimeMs)) + 1][3];
		kNumPoints = points.length;
	
		double currentTimeS = 0;
		
		for(int i = 0; i < points.length; i ++) {
			points[i] = velocityProfileArray(80000, currentTimeS, 100, 400);
			currentTimeS += 0.02;
		}
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
		
		double [] outputArray = new double[3];
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
		outputArray[2] = 20; //default all times to 20 ms
		
		return outputArray;
		
		//return (int)Math.round(outputTicks); //round and cast to int, since encoder ticks must be integer values
	}
}
