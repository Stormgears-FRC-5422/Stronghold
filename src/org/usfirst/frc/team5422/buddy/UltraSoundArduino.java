package org.usfirst.frc.team5422.buddy;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;

public class UltraSoundArduino {
	DigitalOutput d;
	SerialPort sp;

	public final SerialPort.Port SPORT = SerialPort.Port.kMXP;
	public final SerialPort.Parity SPARITY = SerialPort.Parity.kNone;
	public final SerialPort.StopBits SBITS = SerialPort.StopBits.kOne;
	public final int INTERVALTIME = 20000;
	public final int DPORT = 0;
	boolean stringComplete = false;

	public UltraSoundArduino() {
		d = new DigitalOutput(DPORT);
		d.set(false);
		sp = new SerialPort(9600, SPORT, 8, SPARITY, SBITS);
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
		String s;
		int range = 0;

		sp.reset(); // Clear cache ready for next reading

		while (stringComplete == false) {
			if (sp.getBytesReceived() > 0) {
				s = sp.readString(4);
				char rByte = sp.readString(1).charAt(0);
				if (rByte == 'R') {
					if (sp.getBytesReceived() > 0) 
					{
						range = Integer.valueOf(s.substring(1));
					}
				}
				rByte = 0; // reset the rByte ready for next reading
				s = "";
				stringComplete = true; // Set completion of read to true
			}
		}

		return range;
	}
}
