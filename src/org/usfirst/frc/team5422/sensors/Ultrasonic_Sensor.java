package org.usfirst.frc.team5422.sensors;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;

public class Ultrasonic_Sensor {
	DigitalOutput d;
	SerialPort sp;
	public int DPORT;
	public final SerialPort.Port SPORT = SerialPort.Port.kMXP;
	public final SerialPort.Parity SPARITY = SerialPort.Parity.kNone;
	public final SerialPort.StopBits SBITS = SerialPort.StopBits.kOne;
	boolean status; // if its false, then the u.s. is busy, otherwise its free

	public Ultrasonic_Sensor(int port) {
		DPORT = port;
		d = new DigitalOutput(DPORT);
		d.set(false);
		sp = new SerialPort(9600, SPORT, 8, SPARITY, SBITS);
		sp.setReadBufferSize(4);
		sp.enableTermination('\r');
		status = true;
	}
	
	public void setDigitalOutputs()
	{
	}
	
	public void turnOff() {
		d.set(false);
		System.out.println("I am turned off");
	}

	public int ezread() {
		status = false; // Busy for reading
		System.out.println("Started EZ READ");
		String s;
		int range = 0;
		d.set(true); // Turns the ultrasonic on
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
			System.out.println("error");
			e.printStackTrace();
		}
		status = true; // The ultra-sonic is ready to read		
		return range;
	}
}
