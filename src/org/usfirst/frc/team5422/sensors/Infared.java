package org.usfirst.frc.team5422.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

/*
 *@author Niall
 */


public class Infared {

	AnalogInput IR;
	AnalogInput IR2;
	double[] buffer;
	
	public Infared(int port, int port1) {
		IR = new AnalogInput(0);
		buffer = new double[100];
	}
	
	public double getVoltage() {
		return IR.getVoltage(); 
	}
	
	public void setBuffer() {
		for (int i = 0; i <= 99; i ++) {
			buffer[i] = getVoltage();
		}
		if (buffer.length == 100) {
			for (int i = 99; i >= 0; i --) {
				if (i > 1) {
					buffer[i] = buffer[i - 1];
				}
				else buffer[i] = getVoltage();
			}
		}
	}
	
	public double[] getBuffer() {
		return buffer;
	}
	
	public String getMaterials() {
		String material = "";
		if (getVoltage() > 2.5) material = "tape";
		else if (getVoltage() < 2.5) material = "wood";
		return material;
	}
	
}

