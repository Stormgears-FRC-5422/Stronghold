package org.usfirst.frc.team5422.utils;

/*
 *@author Suren Karavettil
 *@author Mayank
 */


public class StrongholdConstants {
	public static final int TALON_DRIVE_LEFT_MASTER = 3;
	public static final int TALON_DRIVE_RIGHT_MASTER  = 0;
	
	//physical attributes
	public static final int ENCODER_TICKS_RESOLUTION = 8192;//ticks
	public static final int ENCODER_TICKS_CPR = ENCODER_TICKS_RESOLUTION / 4;

// Rabbot Drive specifications
	public static final double WHEEL_BASE = 22.0 + 5.0/16;//inches	
	public static final double GEAR_RATIO = 1;
//	public static final double WHEEL_DIAMETER = 6;//inches
	public static final double ROBOT_MIDDLE_TO_FRONT = 17;//inches

// Rhino Drive Specifications
//	public static final double WHEEL_BASE = 25;//inches or 24.75 inches
//	public static final double GEAR_RATIO = 1; //10.71 on motor gives 1 rotation of the wheel 
	public static final double WHEEL_DIAMETER = 2.8;//inches
//	public static final double ROBOT_MIDDLE_TO_FRONT = 17;//inches

	
	public static final double INCHES_PER_TICK = Math.PI * WHEEL_DIAMETER / (GEAR_RATIO * ENCODER_TICKS_RESOLUTION);
	
	public static final int JOYSTICK_CHANNEL = 0;
	public static final int BUTTONBOARD_CHANNEL = 0;
	
	//DigitalOutput 1 for the echo pulses
	public static final int ULTRASONIC_ECHO_PULSE_OUTPUT = 1;
	//DigitalInput 1 for the trigger pulse	
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 1;
	
	public static final int ANALOG_GYRO_INPUT_CHANNEL = 1;

	public static final int[] POSITION_DEFENSE_0_REACH = {25, 110};
	public static final int[] POSITION_DEFENSE_1_REACH = {25 + 1 * 50, 110};
	public static final int[] POSITION_DEFENSE_2_REACH = {25 + 2 * 50, 110};
	public static final int[] POSITION_DEFENSE_3_REACH = {25 + 3 * 50, 110};
	public static final int[] POSITION_DEFENSE_4_REACH = {25 + 4 * 50, 110};
	public static final int CROSS_DEFENSE_LENGTH_Y = 96;

	//H = high; L = low
	public static final double[] POSITION_HLEFT_GOAL = {151, 312};
	public static final double[] POSITION_HCENTER_GOAL = {171.5, 300};
	public static final double[] POSITION_HRIGHT_GOAL = {193.5, 312};
	public static final double[] POSITION_LLEFT_GOAL = {151, 312};
	public static final double[] POSITION_LRIGHT_GOAL = {193.5, 312};

	public static final double[] POSITION_BALL_1 = {45.5, 0};
	public static final double[] POSITION_BALL_2 = {91, 0};
	public static final double[] POSITION_BALL_3 = {130.5, 0};
	public static final double[] POSITION_BALL_4 = {182, 0};
	public static final double[] POSITION_BALL_5 = {227.5, 0};
	public static final double[] POSITION_BALL_6 = {273, 0};
	
	public static final int TALON_LEFT_SHOOTER = 1;
	public static final int TALON_RIGHT_SHOOTER = 8;
	public static final int	TALON_ACTUATOR = 0;
	public static final int SOLENOID_SHOOTER = 0;
	
	public static final double FULL_THROTTLE = 1;
	public static final double NO_THROTTLE = 0;
	public static final double VEL_PER_100MS = 81.92;

	public static final int BIG_BLUE_BUTTON_ID = 10;
	public static final int RED_BUTTON_ID = 15;
	public static final int YELLOW_BUTTON_ID = 14;
	public static final int GREEN_BUTTON_ID = 13;
	public static final int BLUE_BUTTON_ID = 12;
	public static final int BLACK_BUTTON_ID = 9;
	public static final int WHITE_BUTTON_ID = 8;
	public static final int GREEN_SWITCH_ID = 5;
	
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
		TEST_MOTION_PROFILE, 
		TEST_GLOBAL_POSITIONING,
		TEST_NONE
	}

	public enum shootHeightOptions {
		HIGH,
		LOW,
		NONE
	}

	public static final double SHOOT_DELAY = 1;
	public static final double SHOOTER_HEIGHT = 10.5;
	public static final double HEIGHT_TO_HIGH_GOAL = 97 - SHOOTER_HEIGHT;
	public static final double HEIGHT_TO_LOW_GOAL = 18 - SHOOTER_HEIGHT;
	public static final int POT_TURNS = 100;
	public static final double ACTUATOR_ANGLE_RANGE = 90;
	
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
