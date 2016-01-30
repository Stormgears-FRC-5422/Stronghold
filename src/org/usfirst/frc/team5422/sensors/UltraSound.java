package org.usfirst.frc.team5422.sensors;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;

public class UltraSound {
	DigitalOutput d;
	SerialPort sp;

	public final SerialPort.Port SPORT = SerialPort.Port.kMXP;
	public final SerialPort.Parity SPARITY = SerialPort.Parity.kNone;
	public final SerialPort.StopBits SBITS = SerialPort.StopBits.kOne;
	public final int INTERVALTIME = 20000;
	public final int DPORT = 0;
	boolean stringComplete = false;

	public UltraSound(boolean sComplete) {
		d = new DigitalOutput(DPORT);
		d.set(false);
		sp = new SerialPort(9600, SPORT, 8, SPARITY, SBITS);
		stringComplete = sComplete;
		sp.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushOnAccess);
		sp.setReadBufferSize(4);
		sp.setWriteBufferSize(4);
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
		String s;
		int range = 0;
		d.set(true);
		sp.reset(); // Clear cache ready for next reading
		while (stringComplete == false) {
			System.out.println("Entered while loop");
			System.out.println(sp.writeString("R123"));
			sp.flush();
			System.out.println(sp.getBytesReceived());
			s = sp.readString();
			System.out.println(s + "STRING PRINT");
			if (sp.getBytesReceived() > 0) {
				char rByte = sp.readString(1).charAt(0);
				if (rByte == 'R') {
					range = Integer.valueOf(s.substring(1));
					System.out.println(s.substring(1));
					System.out.println("R is there");
				}
				else {
					System.out.println("No r");
					range = 0;
				}
				rByte = 0; // reset the rByte ready for next reading
				s = "";
				stringComplete = true; // Set completion of read to true
			}
			
			else {
				System.out.println("No bytes entered ");
			}
			System.out.println("left if statement");
		}
		System.out.println("Left loop");
		return range;
	}
}
