package org.usfirst.frc.team5422.utils;

public class StrongholdRobotConfigurationManager extends RobotConfigurationManager {

	public StrongholdRobotConfigurationManager() {
		// TODO Auto-generated constructor stub
	}

	public void configure() {
		StrongholdConstants.WHEEL_BASE = 23.0;
		StrongholdConstants.WHEEL_DIAMETER = 6.34;
		StrongholdConstants.SIDE_ULTRA_SENSOR_TO_ROBOT_MIDDLE_Y = 11.5;
		StrongholdConstants.ROBOT_WIDTH = 25;
		
		StrongholdConstants.TALON_ACTUATOR = 5;
		StrongholdConstants.TALON_LEFT_SHOOTER = 7;
		StrongholdConstants.TALON_RIGHT_SHOOTER = 4;
		
		StrongholdConstants.TALON_DRIVE_LEFT_MASTER = 2;
		StrongholdConstants.TALON_DRIVE_RIGHT_MASTER  = 1;
		StrongholdConstants.TALON_DRIVE_LEFT_SLAVE = 0;
		StrongholdConstants.TALON_DRIVE_RIGHT_SLAVE  = 3;
		
		StrongholdConstants.TRAP_F = 0;
		StrongholdConstants.TRAP_P = 0.9;
		StrongholdConstants.TRAP_I = 0;
		StrongholdConstants.TRAP_D = 0.575;

		StrongholdConstants.OPEN_DRIVE_F = 1.705;
		StrongholdConstants.OPEN_DRIVE_P = 0.000185;
		StrongholdConstants.OPEN_DRIVE_I = 0;
		StrongholdConstants.OPEN_DRIVE_D = 0;

		StrongholdConstants.SHOOTER_F = 10;
		StrongholdConstants.SHOOTER_P = 0;
		StrongholdConstants.SHOOTER_I = 0;
		StrongholdConstants.SHOOTER_D = 0;
		
		StrongholdConstants.ANGLE_MOTOR_UP_F = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_P = 40;
		StrongholdConstants.ANGLE_MOTOR_UP_I = 0.005;
		StrongholdConstants.ANGLE_MOTOR_UP_D = 0.2;
		
		StrongholdConstants.ANGLE_MOTOR_DOWN_F = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_P = 6.3;
		StrongholdConstants.ANGLE_MOTOR_DOWN_I = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_D = 0.5;
		
		StrongholdConstants.ANGLE_MOTOR_UP_F = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_P = 40;
		StrongholdConstants.ANGLE_MOTOR_UP_I = 0.005;
		StrongholdConstants.ANGLE_MOTOR_UP_D = 0.2;
		StrongholdConstants.ANGLE_MOTOR_UP_IZONE = 0;
		StrongholdConstants.ANGLE_MOTOR_UP_RAMP_RATE = 1;
		StrongholdConstants.ANGLE_MOTOR_UP_PROFILE = 1;
		
		StrongholdConstants.ANGLE_MOTOR_DOWN_F = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_P = 6.3;
		StrongholdConstants.ANGLE_MOTOR_DOWN_I = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_D = 0.5;
		StrongholdConstants.ANGLE_MOTOR_DOWN_IZONE = 0;
		StrongholdConstants.ANGLE_MOTOR_DOWN_RAMP_RATE = 1;
		StrongholdConstants.ANGLE_MOTOR_DOWN_PROFILE = 0;
		
		StrongholdConstants.ANGLE_MOTOR_INTAKE_POS = 600;

	}

}
