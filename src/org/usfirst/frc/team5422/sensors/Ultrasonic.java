package org.usfirst.frc.team5422.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;

public class Ultrasonic 
{
	DigitalOutput d;
	SerialPort sp;
	
	public final SerialPort.Port SPORT = SerialPort.Port.kMXP;
	public final SerialPort.Parity SPARITY = SerialPort.Parity.kNone;
	public final SerialPort.StopBits SBITS = SerialPort.StopBits.kOne;
	public final int INTERVALTIME = 20;
	public final int DPORT = 2;
	
	
	public Ultrasonic()
	{
		d = new DigitalOutput(DPORT);
		d.set(false);
		sp = new SerialPort(9600,SPORT, 8,SPARITY,SBITS);
	}
	
	public void isOn()
	{
		long startTime = System.currentTimeMillis();
		
		long endTime = startTime;
		long elapsedTime = endTime - startTime;
		
		while(elapsedTime < INTERVALTIME)
		{
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			d.set(true);
			readBytes();
		}
		d.set(false);
	}
	
	public void turnOff()
	{
		d.set(false);
	}
	
	public byte[] readBytes()
	{
		byte[] b = new byte[24];
		b = sp.read(24);
		return b;
		
		//intConversion(b);	
	}
	
	public void getRangeInches()
	{
		
	}
	
	public ArrayList<Integer> intConversion(byte[] b)
	{
		char[] c = new char[24];
		String s = new String(b);
		c = s.toCharArray();
		ArrayList<Integer> asciiArray = new ArrayList<Integer>();
		
		for(char a : c)
		{
			asciiArray.add((int)a);
		}
		
		return asciiArray;
	}
	
	
}
