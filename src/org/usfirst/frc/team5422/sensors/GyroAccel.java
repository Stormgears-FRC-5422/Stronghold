package org.usfirst.frc.team5422.sensors;

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
    
	public double getRotationRate()
	{
		gyroStatus = false;
		double x = gyro.getRate();
		gyroStatus = true;
		return rounding(x);
	}
	
	public double getAngle()
	{
		gyroStatus = false;
		double x = positive(rounding(gyro.getAngle()));
		gyroStatus = true;
		return x;
	}
	
	public double getAccelerationX()
	{
		accelStatus = false;
		double x = accel.getAcceleration(Axes.kX);
		accelStatus = true;
		return x;
	}
	
	public double getAccelerationY()
	{
		accelStatus = false;
		double x = accel.getAcceleration(Axes.kY);
		accelStatus = true;
		return x;
	}
	
	public double getAccelerationZ()
	{
		accelStatus = false;
		double x = accel.getAcceleration(Axes.kZ);
		accelStatus = true;
		return x;
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
