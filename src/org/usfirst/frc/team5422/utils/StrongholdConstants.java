package org.usfirst.frc.team5422.utils;

/*
 *@author Mayank
 */


public class StrongholdConstants {
	public static final int TALON_DRIVE_LEFT_MASTER = 1;
	public static final int TALON_DRIVE_RIGHT_MASTER  = 3;
	public static final int TALON_DRIVE_LEFT_SLAVE = 8;
	public static final int TALON_DRIVE_RIGHT_SLAVE= 0;
	
	public static final int ENCODER_TICKS_RESOLUTION = 2048;
	
	public static final int JOYSTICK_CHANNEL = 0;
	public static final int BUTTONBOARD_CHANNEL = 0;
	
	//DigitalOutput 1 for the echo pulse 
	public static final int ULTRASONIC_ECHO_PULSE_OUTPUT = 1;
	//DigitalInput 1 for the trigger pulse	
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 1;
	
	public static final int ANALOG_GYRO_INPUT_CHANNEL = 1;
	
	public static final int GYRO = 0;
	public static final int ULTRASONIC = 1;
	public static final int IR = 2;
	public static final int TALON_LEFT_MASTER = 3;
	public static final int TALON_RIGHT_MASTER = 4;
	
	public static enum shootOptions{
		HIGH_LEFT,
		HIGH_RIGHT,
		HIGH_CENTER,
		LOW_LEFT,
		LOW_RIGHT,
		NONE
	}
	
	public static enum defensePositionOptions{
		POSITION_1_LOW_BAR,
		POSITION_2,
		POSITION_3,
		POSITION_4,
		POSITION_5,
	}
	
	public static enum defenseTypeOptions{
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
	
	public static enum endOptions{
		TELEOP_STARTING_POSITION,
		NONE
	}
	
	


}
