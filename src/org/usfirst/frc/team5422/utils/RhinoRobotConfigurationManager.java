package org.usfirst.frc.team5422.utils;

public class RhinoRobotConfigurationManager extends RobotConfigurationManager {

	public RhinoRobotConfigurationManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void configure() {
		StrongholdConstants.WHEEL_BASE = 25.0;//22.0 + 5.0/16;//inches
		StrongholdConstants.WHEEL_DIAMETER = 3.75;//inches
		StrongholdConstants.INCHES_PER_TICK = 0.001225;
		
		StrongholdConstants.TALON_LEFT_SHOOTER = 1;
		StrongholdConstants.TALON_RIGHT_SHOOTER = 8;
		
		StrongholdConstants.TALON_DRIVE_LEFT_MASTER = 3;
		StrongholdConstants.TALON_DRIVE_RIGHT_MASTER  = 0;

		StrongholdConstants.TRAP_F = 0;
		StrongholdConstants.TRAP_P = 1;
		StrongholdConstants.TRAP_I = 0;
		StrongholdConstants.TRAP_D = 0;

		StrongholdConstants.OPEN_DRIVE_F = 1.705;
		StrongholdConstants.OPEN_DRIVE_P = 0.000185;
		StrongholdConstants.OPEN_DRIVE_I = 0;
		StrongholdConstants.OPEN_DRIVE_D = 0;
		
		StrongholdConstants.SHOOTER_F = 10;
		StrongholdConstants.SHOOTER_P = 0;
		StrongholdConstants.SHOOTER_I = 0;
		StrongholdConstants.SHOOTER_D = 0;

		StrongholdConstants.ANGLE_MOTOR_UP_F = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_P = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_I = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_D = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_IZONE = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_RAMP_RATE = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_PROFILE = 0;
		
		StrongholdConstants.ANGLE_MOTOR_DOWN_F = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_P = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_I = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_D = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_IZONE = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_RAMP_RATE = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE = 0;
		
		StrongholdConstants.ANGLE_MOTOR_INTAKE_POS = 0;
	}
	
}
