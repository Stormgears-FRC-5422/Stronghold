/**
 * This Java FRC robot application is meant to demonstrate an example using the Motion Profile control mode
 * in Talon SRX.  The CANTalon class gives us the ability to buffer up trajectory points and execute them
 * as the roboRIO streams them into the Talon SRX.
 * 
 * There are many valid ways to use this feature and this example does not sufficiently demonstrate every possible
 * method.  Motion Profile streaming can be as complex as the developer needs it to be for advanced applications,
 * or it can be used in a simple fashion for fire-and-forget actions that require precise timing.
 * 
 * This application is an IterativeRobot project to demonstrate a minimal implementation not requiring the command 
 * framework, however these code excerpts could be moved into a command-based project.
 * 
 * The project also includes instrumentation.java which simply has debug printfs, and a MotionProfile.java which is generated
 * in @link https://docs.google.com/spreadsheets/d/1PgT10EeQiR92LNXEOEe3VGn737P7WDP4t0CQxQgC8k0/edit#gid=1813770630&vpid=A1
 * 
 * Logitech Gamepad mapping, use left y axis to drive Talon normally.  
 * Press and hold top-left-shoulder-button5 to put Talon into motion profile control mode.
 * This will start sending Motion Profile to Talon while Talon is neutral. 
 * 
 * While holding top-left-shoulder-button5, tap top-right-shoulder-button6.
 * This will signal Talon to fire MP.  When MP is done, Talon will "hold" the last setpoint position
 * and wait for another button6 press to fire again.
 * 
 * Release button5 to allow OpenVoltage control with left y axis.
 */

package org.usfirst.frc.team5422.navigator.trapezoidal;

import edu.wpi.first.wpilibj.CANTalon;

import java.awt.Button;
import java.util.logging.*;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

	
	Logger globalLog = Logger.getGlobal();
	ConsoleHandler handler = new ConsoleHandler();
	
	/** The Talon we want to motion profile. */
	CANTalon _talon = new CANTalon(22);

	/** some example logic on how one can manage an MP */
	MotionProfileExample _example = new MotionProfileExample(_talon);
	
	/** joystick for testing */
	Joystick _joy= new Joystick(0);

	boolean runOnce = false; //default the flag to false

	/** cache last buttons so we can detect press events.  In a command-based project you can leverage the on-press event
	 * but for this simple example, lets just do quick compares to prev-btn-states */
	
	
	public Robot() { // could also use RobotInit()
		_talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_talon.reverseSensor(false);
		_talon.reverseOutput(false);
		//_talon.setF(0.04808); --> FIX IF NECESSARY
		
		
		GeneratedMotionProfile.Points = new double [0][0];
		GeneratedMotionProfile.kNumPoints = GeneratedMotionProfile.Points.length;
	}
	

	
	
	
	public void teleopInit() {
		
		//output the fields to smart dashboard to enable user input of distance, velocity
		SmartDashboard.putNumber("Total Distance: ", 0);	
		SmartDashboard.putNumber("Max Velocity: ", 0);

	}
	/**  function is called periodically during operator control */
	public void teleopPeriodic() {
		
		System.out.println("enter tele-op");
		
		if(_joy.getRawButton(1)) {
			System.out.println("Button Pressed.");
			double distance = SmartDashboard.getNumber("Total Distance: ");
			double velocity = SmartDashboard.getNumber("Max Velocity: ");
			GeneratedMotionProfile.Points = TrapezoidControl.motionProfileUtility(distance, velocity);
			GeneratedMotionProfile.kNumPoints = GeneratedMotionProfile.Points.length;
			runOnce = true; //after the profile is loaded, set the flag to true
		}
		
			
		
		/* call this periodically, and catch the output.  Only apply it if user wants to run MP. */
    	_example.control();
		_talon.changeControlMode(TalonControlMode.MotionProfile);
		CANTalon.SetValueMotionProfile setOutput = _example.getSetValue();
		_talon.set(setOutput.value);

	
			
		System.out.println("runOnce " + runOnce);
		if(runOnce) {
			_example.startMotionProfile();
			runOnce = false; //set the flag to false after profile is completed
		}
		
	
	
		SmartDashboard.putNumber("V: ", _talon.getEncVelocity()/8192.0 * 600);
		SmartDashboard.putNumber("P: ", _talon.getEncPosition()/8192.0);
			

	}
    

	/**  function is called periodically during disable */
	public void disabledPeriodic() {
		//reset all necessary things
		_talon.changeControlMode(TalonControlMode.PercentVbus);
		_talon.set( 0 );
		_talon.setEncPosition(0);
		/* clear our buffer and put everything into a known state */
		_example.reset();		
		runOnce = false;
	}
}