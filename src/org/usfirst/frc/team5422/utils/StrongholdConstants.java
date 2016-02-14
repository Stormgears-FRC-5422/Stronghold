package org.usfirst.frc.team5422.utils;

/*
 *@author Mayank
 */


public class StrongholdConstants {
	public static final int TALON_DRIVE_LEFT_MASTER = 3;
	public static final int TALON_DRIVE_RIGHT_MASTER  = 0;
	
	//physical attributes
	public static final double ENCODER_TICKS_RESOLUTION = 8192;//ticks
	public static final double WHEEL_BASE = 22.0 + 5.0/16;//inches
	public static final double GEAR_RATIO = 1;
	public static final double WHEEL_DIAMETER = 6;//inches
	public static final double ROBOT_MIDDLE_TO_FRONT = 17;//inches
	
	public static final double INCHES_PER_TICK = Math.PI * WHEEL_DIAMETER / (GEAR_RATIO * ENCODER_TICKS_RESOLUTION);
	
	public static final int JOYSTICK_CHANNEL = 0;
	public static final int BUTTONBOARD_CHANNEL = 0;
	
	//DigitalOutput 1 for the echo pulse 
	public static final int ULTRASONIC_ECHO_PULSE_OUTPUT = 1;
	//DigitalInput 1 for the trigger pulse	
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 1;
	
	public static final int ANALOG_GYRO_INPUT_CHANNEL = 1;

	public static final int[] POSITION_DEFENSE_0_REACH = {24, 36};
	public static final int[] POSITION_DEFENSE_1_REACH = {24 + 1 * 48, 36};
	public static final int[] POSITION_DEFENSE_2_REACH = {24 + 2 * 48, 36};
	public static final int[] POSITION_DEFENSE_3_REACH = {24 + 3 * 48, 36};
	public static final int[] POSITION_DEFENSE_4_REACH = {24 + 4 * 48, 36};
	public static final int CROSS_DEFENSE_LENGTH_Y = 96;
	
	public static final int TALON_LEFT_SHOOTER = 0;
	public static final int TALON_RIGHT_SHOOTER = 3;
	public static final int	TALON_ACTUATOR = 0;
	public static final int SOLENOID_SHOOTER = 0;
	
	public static final double FULL_THROTTLE = 1;
	public static final double NO_THROTTLE = 0;
	public static final double VEL_PER_100MS = 81.92;
	
	public enum diagnosticPOSTOptions {
		TEST_GYRO, 
		TEST_ULTRASONIC, 
		TEST_IR,
		TEST_TALON_LEFT_MASTER,
		TEST_TALON_RIGHT_MASTER,
		TEST_CHASSIS_DRIVE,
		TEST_SHOOTER,
		TEST_GRAPPLER,
		TEST_ALIGN_TO_CASTLE,
		TEST_LIFTER,
		TEST_MOTION_PROFILE
	}
		
	public enum shootOptions {
		HIGH_LEFT,
		HIGH_RIGHT,
		HIGH_CENTER,
		LOW_LEFT,
		LOW_RIGHT,
		NONE
	}
	
	public enum defenseTypeOptions {
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
	
	public enum endOptions {
		TELEOP_STARTING_POSITION,
		NONE
	}
	
	public enum Speed {
		SLOW, MEDIUM, FAST
	}

	public enum alliance {
		RED, BLUE
	}
	

}
