package org.usfirst.frc.team5422.sensors;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;

/*
 * @author Vatsal
 */

public class UltraSound {
	DigitalOutput d;
	SerialPort sp;

	public final SerialPort.Port SPORT = SerialPort.Port.kMXP;
	public final SerialPort.Parity SPARITY = SerialPort.Parity.kNone;
	public final SerialPort.StopBits SBITS = SerialPort.StopBits.kOne;
	public final int INTERVALTIME = 20000;
	public final int DPORT = 0;
	boolean stringComplete = false;

	public UltraSound() {
		d = new DigitalOutput(DPORT);
		d.set(false);
		sp = new SerialPort(9600, SPORT, 8, SPARITY, SBITS);
		sp.setReadBufferSize(4);
		sp.enableTermination('\r');
	}

	public int isOn() {
		long startTime = System.nanoTime();
 
		long endTime = startTime;
		long elapsedTime = endTime - startTime;

		while (elapsedTime < INTERVALTIME) {
			endTime = System.nanoTime();
			elapsedTime = endTime - startTime;
			d.set(true);
			return ezread();
		}
		d.set(false);
		return 0;
	}

	public void turnOff() {
		d.set(false);
	}

	public int ezread() {
		System.out.println("Started EZ READ");
		//String s;
		int range = 0;
		d.set(true);
		sp.reset(); // Clear cache ready for next reading
		while (stringComplete == false) {
		try {

			if (sp.getBytesReceived() > 0) {
//				s = sp.readString(sp.getBytesReceived());
//				System.out.println(s + "STRING PRINT");
//				char rByte = s.charAt(0);
//				if (rByte == s'R') {
//					range = Integer.valueOf(s.substring(1));
//					System.out.println(s.substring(1));
//					System.out.println("R is there");
//				}
//				else {
//					System.out.println("No r");
//					range = 0;
//				}
//				rByte = 0; // reset the rByte ready for next reading
//				s = "";
				System.out.println("Bytes received: "+ sp.getBytesReceived());
				//System.out.println("String of bytes" + sp.readString()); //+ sp.readString(sp.getBytesReceived()));
				sp.reset();
				stringComplete = true; // Set completion of read to true
			}
			
			else {
				//System.out.println("No bytes entered ");
			}
			//System.out.println("left if statement");
			}
		
		catch(Exception e)
		{
			System.out.println("error");
			System.out.println(e.getCause());
//			e.printStackTrace();
		}
	
		
	}
		System.out.println("Left loop");
		return range;
}
}
