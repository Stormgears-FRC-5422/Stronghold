package org.usfirst.frc.team5422.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;

public class UltrasonicSensor {
	DigitalOutput d1;
	DigitalOutput selector1;
	DigitalOutput selector2;
	
	SerialPort sp;
	public int DPORT = 1;
	public int selectionPort1 = 2;  // What do these equal???
	public int selectionPort2 = 3;  // What do these equal???
	public final SerialPort.Port SPORT = SerialPort.Port.kMXP;
	public final SerialPort.Parity SPARITY = SerialPort.Parity.kNone;
	public final SerialPort.StopBits SBITS = SerialPort.StopBits.kOne;
	
	boolean status; // if its false, then the u.s. is busy, otherwise its free
	
	public UltrasonicSensor() 
	{
		d1 = new DigitalOutput(DPORT);
		selector1 = new DigitalOutput(selectionPort1);
		selector2 = new DigitalOutput(selectionPort2);
		
		d1.set(false);
				
		sp = new SerialPort(9600, SPORT, 8, SPARITY, SBITS);
		sp.setReadBufferSize(4);
		sp.enableTermination('\r');
		
		status = true;
	}
	
	public void turnOff() {
		d1.set(false);
		System.out.println("I am turned off");
	}

	public int getFrontDistance() {
		status = false; // Busy for reading
		System.out.println("Started EZ READ");
		String s;
		int range = 0;
		
		d1.set(true); // Turns the ultrasonic on
	
		selector1.set(false); // WAS FALSE HERE 
		selector2.set(false); 
		
		try 
		{
			if (sp.getBytesReceived() == 5) 
			{
				status = true;
				s = sp.readString(sp.getBytesReceived());
				String substring = s.substring(1,4);
				String fixedSubstring = substring.replaceFirst("^0+(?!$)", "");
//				System.out.println(substring);
				range = Integer.parseInt(fixedSubstring); 
    		}
			
			else 
			{
				range = -1;
				System.out.println(sp.getBytesReceived() + " BYTES RECEIVED");
				status = false;
			}
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		status = true; // The ultra-sonic is ready to read		
		
		return range;
	}
	
	public int getRightDistance() {
		status = false; // Busy for reading
		System.out.println("Started EZ READ");
		String s;
		int range = 0;
		
		d1.set(true); // Turns the ultrasonic on
	
		// Second socket
		selector1.set(false); 
		selector2.set(true); 
		
		try 
		{
			if (sp.getBytesReceived() > 0) 
			{
				status = true;
				s = sp.readString(sp.getBytesReceived());
				String substring = s.substring(1,4);
				String fixedSubstring = substring.replaceFirst("^0+(?!$)", "");
//				System.out.println(substring);
//				System.out.println(fixedSubstring);
				range = Integer.parseInt(fixedSubstring); 
    		}
			
			else 
			{
				range = -1;
				status = false;
			}
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		status = true; // The ultra-sonic is ready to read		
		
		return range;
	}

	public int getBackDistance() {
		status = false; // Busy for reading
		System.out.println("Started EZ READ");
		String s;
		int range = 0;
		
		d1.set(true); // Turns the ultrasonic on
	
		// Third Socket
		selector1.set(true); 
		selector2.set(false); 
		
		try 
		{
			if (sp.getBytesReceived() > 0) 
			{
				status = true;
				s = sp.readString(sp.getBytesReceived());
				String substring = s.substring(1,4);
				String fixedSubstring = substring.replaceFirst("^0+(?!$)", "");
//				System.out.println(substring);
//				System.out.println(fixedSubstring);
				range = Integer.parseInt(fixedSubstring); 
    		}
			
			else 
			{
				range = -1;
				status = false;
			}
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		status = true; // The ultra-sonic is ready to read		
		
		return range;
	}

	public int getLeftDistance() {
		status = false; // Busy for reading
		System.out.println("Started EZ READ");
		String s;
		int range = 0;
		
		d1.set(true); // Turns the ultrasonic on
	
		selector1.set(true);  // Fourth socket selector
		selector2.set(true); 
		
		try 
		{
			if (sp.getBytesReceived() > 0) 
			{
				status = true;
				s = sp.readString(sp.getBytesReceived());
				String substring = s.substring(1,4);
				String fixedSubstring = substring.replaceFirst("^0+(?!$)", "");
//				System.out.println(substring);
//				System.out.println(fixedSubstring);
				range = Integer.parseInt(fixedSubstring); 
    		}
			
			else 
			{
				range = -1;
				status = false;
			}
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		status = true; // The ultra-sonic is ready to read		
		
		return range;
	}
	
	public ArrayList<Integer> getDistanceList()
	{
		// The order of the array list is the front, right, back and left
		
		ArrayList<Integer> distances = new ArrayList<Integer>();
		distances.add(getFrontDistance());
		distances.add(getRightDistance());
		distances.add(getBackDistance());
		distances.add(getLeftDistance());
		
		return distances;
	}

}
