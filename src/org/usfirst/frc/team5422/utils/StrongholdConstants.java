package org.usfirst.frc.team5422.utils;

/*
 *@author Mayank
 */


public class StrongholdConstants {
	public static final int TALON_DRIVE_LEFT_MASTER = 3;
	public static final int TALON_DRIVE_RIGHT_MASTER  = 0;
	
	//physical attributes
	public static final double ENCODER_TICKS_RESOLUTION = 8192;//ticks
	public static final double WHEEL_BASE = 23.5;//inches
	public static final double GEAR_RATIO = 8.46;
	public static final double WHEEL_DIAMETER = 5;//inches
	public static final double ROBOT_MIDDLE_TO_FRONT = 17;//inches
	
	public static final double INCHES_PER_TICK = Math.PI*WHEEL_BASE/(GEAR_RATIO*ENCODER_TICKS_RESOLUTION);
	
	public static final int JOYSTICK_CHANNEL = 0;
	public static final int BUTTONBOARD_CHANNEL = 0;
	
	//DigitalOutput 1 for the echo pulse 
	public static final int ULTRASONIC_ECHO_PULSE_OUTPUT = 1;
	//DigitalInput 1 for the trigger pulse	
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 1;
	
	public static final int ANALOG_GYRO_INPUT_CHANNEL = 1;
	
	public enum diagnosticPOSTOptions{
		TEST_GYRO, 
		TEST_ULTRASONIC, 
		TEST_IR,
		TEST_TALON_LEFT_MASTER,
		TEST_TALON_RIGHT_MASTER,
		TEST_CHASSIS_DRIVE,
		TEST_SHOOTER
	}
	
//	public static enum ShootPositions {
//		LEFT,
//		CENTER,
//		RIGHT
//	};
//	
//	public static enum AimPositions {
//		HIGH,
//		LOW
//	};	
		
	public enum shootOptions{
		HIGH_LEFT,
		HIGH_RIGHT,
		HIGH_CENTER,
		LOW_LEFT,
		LOW_RIGHT,
		NONE
	}
	
	public static int
			LOW_BAR_POS,
			PORTCULLIS_POS,
			CHIVAL_DE_FRISE_POS,
			MOAT_POS,
			RAMPARTS_POS,
			DRAWBRIDGE_POS,
			SALLYPORT_POS,
			ROCK_WALL_POS,
			ROUGH_TERRAIN_POS;
	
	public enum defenseTypeOptions{
		LOW_BAR,
		PORTCULLIS,
		CHIVAL_DE_FRISE,
		MOAT,
		RAMPARTS,
		DRAWBRIDGE,
		SALLYPORT,
		ROCK_WALL,
		ROUGH_TERRAIN,
		NONE
	}
	
	public enum endOptions{
		TELEOP_STARTING_POSITION,
		NONE
	}
	
	public enum Speed{
		SLOW, MEDIUM, FAST
	}
	

}
