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
		String strReadFromPort;
		int range = 0;
		
		//DIGITAL IO port is set to HIGH (1)
		d.set(true);
		
		//resetting the serial port to known state
		sp.reset(); // Clear cache ready for next reading
		
		//initialized number of bytes received
		int numberOfBytesReceived = -1; 
		
		while (stringComplete == false) {
			try {
				System.out.println("Entered while loop");
				
				//Force the output buffer to be written to the port
				sp.flush();
			
				//gives the number of bytes received from serial port
				numberOfBytesReceived = sp.getBytesReceived();
				
				System.out.println("Number Of Bytes Received " + numberOfBytesReceived);
				
				if (numberOfBytesReceived > 0) {
					
					//reading the bytes from the serial port
					strReadFromPort = sp.readString(numberOfBytesReceived);
					
					System.out.println("Read string  from port: " + strReadFromPort);
					
					//read character, it should be "R" 
					char rByte = strReadFromPort.charAt(0);
					if (rByte == 'R') {
						range = Integer.valueOf(strReadFromPort.substring(1));
						System.out.println(strReadFromPort.substring(1));
						System.out.println("R is there");
					}
					else {
						System.out.println("No R");
						range = 0;
					}
					rByte = 0; // reset the rByte ready for next reading
					strReadFromPort = "";
					if ((numberOfBytesReceived > 0) && (numberOfBytesReceived < 5)) {
						stringComplete = true; // Set completion of read to true
					}
				} else {
					System.out.println("No bytes to read");
				}
			} catch(Exception e) {
				System.out.println("Error");
			}		
		}
		System.out.println("Left loop");
		return range;
	}
}
