package org.usfirst.frc.team5422.buddy;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    RobotDrive myRobot;
    
   // ANALOG STUFF ON MAXBOTIX
    Ultrasonic a; 
    UltraSoundArduino newa;
    
	public void autonomousInit() {
			
	}
	
	public void autonomousPeriodic() {
	}
	
	
	public void teleopInit() {	
    	a = new Ultrasonic();
    	newa = new UltraSoundArduino();
	}
		
	public void teleopPeriodic() {
    	
    	newa.isOn();
//    	SmartDashboard.putNumber("Range in Inches", a.readBytes());	
    	System.out.println("Buddy");
    	SmartDashboard.putString("Testing", "Please work");
    	SmartDashboard.putNumber("Range: Inches", newa.isOn());
	}
}
