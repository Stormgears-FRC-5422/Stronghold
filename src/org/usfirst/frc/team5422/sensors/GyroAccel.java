package org.usfirst.frc.team5422.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.ADXL345_SPI.Axes;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class GyroAccel 
{
	ADXRS450_Gyro gyro;
	ADXL345_SPI accel;
    boolean gyroStatus; //true = free, false = busy
	boolean accelStatus;  //true = free, false = busy 
	public GyroAccel()
	{
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    	gyro.calibrate();
    	gyro.reset();
    	accel = new ADXL345_SPI(SPI.Port.kOnboardCS1, Accelerometer.Range.k8G);
	}
	private double rounding(double x)
    {
    	return (int)(100 * x)/100.0;
    }
    
    private double positive(double x)
    {
    	if(x < 0) return (x + 360);
    	
    	return x;
    }
    
    public double getAngle()
    {
    	return (positive(rounding(gyro.getAngle())) / 360.0) * (2 *Math.PI);
    }
    
    public double getRate()
    {
    	return rounding(gyro.getRate());
    }
    
	public ArrayList<Double> getGyroList()
	{
		gyroStatus = false;
		ArrayList<Double> gyroStuff = new ArrayList<Double>();
		gyroStuff.add(getRate());
		gyroStuff.add(getAngle());	
		gyroStatus = true;
		return gyroStuff;
	}
	
	public void reset()
	{
		gyro.reset();
	}
	
	public ArrayList<Double> getAccelerationList()
	{
		accelStatus = false;
		ArrayList<Double> accelList = new ArrayList<Double>();
		accelList.add(accel.getAcceleration(Axes.kX));
		accelList.add(accel.getAcceleration(Axes.kY));
		accelList.add(accel.getAcceleration(Axes.kZ));
		accelStatus = true;
		return accelList;
	}
	public boolean getAccelStatus()
	{
		return accelStatus;
	}
	
	public boolean getGyroStatus()
	{
		return gyroStatus;
	}
}
