package org.usfirst.frc.team5422.navigator.trapezoidal;

public class TrapezoidControl {
	
	//time = targetTicks * 0.00125
	//maxAccel = targetTicks * 0.01;
	//maxVel = targetTicks * 0.05
	

	
	public static double [][] motionProfileUtility(double rotations, double maxVel, double startingRotations) {
		rotations = Math.abs(rotations); //this is key, because it basically allows this: neg rotations = backwards
		int targetTicks = (int)(rotations * 8192);
		int startingTicks = (int)(startingRotations * 8192);
		double velRPM = maxVel * 8192;
		double accel = velRPM;
		
		System.out.println("Target ticks: " + targetTicks + "Max Accel: " + accel + "Max Vel: " + velRPM);
		return ultimateProfileDoubleArray(targetTicks, startingTicks, accel, velRPM);
		
	}
	
	
	public static double [][] ultimateProfileDoubleArray(int targetTicks, int startingTicks, double maxAccel, double maxVel) {
		
		double[][] trajectoryPointArray = new double[(int)(Math.round(determineTime(targetTicks, maxAccel, maxVel)/10.0)) + 1][3];
		
		double currentTime = 0;
		
		for(int i = 0; i < trajectoryPointArray.length; i ++) {
			double [] pointValues = velocityProfileArray(targetTicks, currentTime, maxAccel, maxVel);
			
			trajectoryPointArray[i][0] = pointValues[0]/8192.0;// + startingTicks/8192.0; //in rotations now
			trajectoryPointArray[i][1] = pointValues[1]/8192.0 * 60; //RPM now
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

