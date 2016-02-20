package org.usfirst.frc.team5422.utils;

/*
 *@author Suren Karavettil
 */

public class ConfigUpdateManager {

	public ConfigUpdateManager() {
		// TODO Auto-generated constructor stub
	}

	public void configureRhinoRobot() {
		StrongholdConstants.WHEEL_BASE = 22.0 + 5.0/16;//inches	
		
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

		StrongholdConstants.WHEEL_DIAMETER = 2.8;//inches
	}
	
	public void configureStrongholdRobot() {
		StrongholdConstants.WHEEL_BASE = 23.0;
		
		StrongholdConstants.TALON_ACTUATOR = 5;
		StrongholdConstants.TALON_LEFT_SHOOTER = 7;
		StrongholdConstants.TALON_RIGHT_SHOOTER = 4;
		
		StrongholdConstants.TALON_DRIVE_LEFT_MASTER = 2;
		StrongholdConstants.TALON_DRIVE_RIGHT_MASTER  = 1;
		StrongholdConstants.TALON_DRIVE_LEFT_SLAVE = 0;
		StrongholdConstants.TALON_DRIVE_RIGHT_SLAVE  = 3;
		
		StrongholdConstants.TRAP_F = 0;
		StrongholdConstants.TRAP_P = 1;
		StrongholdConstants.TRAP_I = 0;
		StrongholdConstants.TRAP_D = 0;

		StrongholdConstants.OPEN_DRIVE_F = 1.705;
		StrongholdConstants.OPEN_DRIVE_P = 0.000185;
		StrongholdConstants.OPEN_DRIVE_I = 0;
		StrongholdConstants.OPEN_DRIVE_D = 0;

		StrongholdConstants.WHEEL_DIAMETER = 6.34;//inches

	}
	
}
