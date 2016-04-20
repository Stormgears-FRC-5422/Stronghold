package org.usfirst.frc.team5422.DSIO;

import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.endOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.startPositionOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.autonomousModeOptions;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.utils.StrongholdRobotConfigurationManager;
/*
 * @author Michael
 * @author Suren Karavettil
 * 
 */


public class SmartDashboardChooser {
    public static SendableChooser defenseChooser, shootChooser, allianceChooser, autonomousModeChooser, startPositionChooser;
    public static SendableChooser testChooser, testMotorChooser;

    public SmartDashboardChooser() {
		//create choosers

    	//Create start position chooser
        startPositionChooser = new SendableChooser();
    	//create defense chooser
    	defenseChooser = new SendableChooser();
        //Create shoot chooser
        shootChooser = new SendableChooser();
        //Create alliance selector
        allianceChooser = new SendableChooser();
        //Create subsystem test selector
        testChooser = new SendableChooser();        
        //create autonomous mode chooser
        autonomousModeChooser = new SendableChooser();
        //create test motor chooser
        testMotorChooser = new SendableChooser();
	}
	
	public void initChoosers() {

		if (defenseChooser == null) System.out.println("say error in initializing choosers");
        defenseChooser.addObject("(0) Low Bar", defenseTypeOptions.LOW_BAR);
        defenseChooser.addObject("(1) Portcullis", defenseTypeOptions.PORTCULLIS);
        defenseChooser.addObject("(2) Chival de Frise", defenseTypeOptions.CHIVAL_DE_FRISE);
        defenseChooser.addDefault("(3) Moat", defenseTypeOptions.MOAT);
        defenseChooser.addObject("(4) Ramparts", defenseTypeOptions.RAMPARTS);
        defenseChooser.addObject("(5) Drawbridge", defenseTypeOptions.DRAWBRIDGE);
        defenseChooser.addObject("(6) Sallyport", defenseTypeOptions.SALLYPORT);
        defenseChooser.addObject("(7) Rock Wall", defenseTypeOptions.ROCK_WALL);
        defenseChooser.addObject("(8) Rough Terrain", defenseTypeOptions.ROUGH_TERRAIN);
        defenseChooser.addObject("(-1) Do nothing in Auto.", defenseTypeOptions.NONE);
        SmartDashboard.putData("Defense to cross chooser", defenseChooser);

        shootChooser.addDefault("Shoot High Left Goal", shootOptions.HIGH_LEFT);
        shootChooser.addObject("Shoot High Center Goal", shootOptions.HIGH_CENTER);
        shootChooser.addObject("Shoot High Right Goal", shootOptions.HIGH_RIGHT);
        shootChooser.addObject("Shoot Low Left Goal", shootOptions.LOW_LEFT);
        shootChooser.addObject("Shoot Low Right Goal", shootOptions.LOW_RIGHT);
        shootChooser.addObject("Stay in place.", shootOptions.NONE);
        SmartDashboard.putData("Shoot chooser", shootChooser);

        allianceChooser.addDefault("Red", StrongholdConstants.alliance.RED);
        allianceChooser.addObject("Blue", StrongholdConstants.alliance.BLUE);
        SmartDashboard.putData("Alliance Chooser", allianceChooser);

        autonomousModeChooser.addObject("Reach AND Cross Only", autonomousModeOptions.REACH_N_CROSS);
        autonomousModeChooser.addDefault("Reach, Cross AND Shoot", autonomousModeOptions.REACH_N_CROSS_N_SHOOT);
        autonomousModeChooser.addObject("Reach Only", autonomousModeOptions.REACH);
        autonomousModeChooser.addObject("Not Moving in Autonomous", autonomousModeOptions.NONE);
        SmartDashboard.putData("Autonomous Mode Chooser", autonomousModeChooser);

        startPositionChooser.addDefault("Start Position 1 (Front of Low Bar)", startPositionOptions.FRONT_OF_DEFENSE_1_LOW_BAR);
        startPositionChooser.addObject("Start Position 2 (Front of 2nd Defense)", startPositionOptions.FRONT_OF_DEFENSE_2 );
        startPositionChooser.addObject("Start Position 3 (Front of 3rd Defense)", startPositionOptions.FRONT_OF_DEFENSE_3);
        startPositionChooser.addObject("Start Position 4 (Front of 4th Defense)", startPositionOptions.FRONT_OF_DEFENSE_4);
        startPositionChooser.addObject("Start Position 5 (Front of 5th Defense)", startPositionOptions.FRONT_OF_DEFENSE_5);
        SmartDashboard.putData("Start Position Chooser", startPositionChooser);
               
 	}
	
	public void autoInitChoosers() {
        //Add other defense position text boxes
//        SmartDashboard.putNumber("Defense at Position 1", 0);
//        SmartDashboard.putNumber("Defense at Position 2", 3);
//        SmartDashboard.putNumber("Defense at Position 3", 4);
//        SmartDashboard.putNumber("Defense at Position 4", 7);
//        SmartDashboard.putNumber("Defense at Position 5", 8);
	}	

	public void teleopInitChoosers() {

	}	

	public void testInitChoosers() {
        testChooser.addObject("(0) Test Gyro", StrongholdConstants.diagnosticPOSTOptions.TEST_GYRO);
        testChooser.addObject("(1) Test Ultrasonic", StrongholdConstants.diagnosticPOSTOptions.TEST_ULTRASONIC);
        testChooser.addObject("(2) Test Infrared", StrongholdConstants.diagnosticPOSTOptions.TEST_IR);
        testChooser.addObject("(3) Test Left Talon", StrongholdConstants.diagnosticPOSTOptions.TEST_TALON_LEFT_MASTER);
        testChooser.addObject("(4) Test Right Talon", StrongholdConstants.diagnosticPOSTOptions.TEST_TALON_RIGHT_MASTER);
        testChooser.addObject("(5) Test Drive", StrongholdConstants.diagnosticPOSTOptions.TEST_CHASSIS_DRIVE);
        testChooser.addObject("(6) Test Shooter", StrongholdConstants.diagnosticPOSTOptions.TEST_SHOOTER);
        testChooser.addObject("(7) Test Grappler", StrongholdConstants.diagnosticPOSTOptions.TEST_GRAPPLER);
        testChooser.addObject("(8) Test Alignment", StrongholdConstants.diagnosticPOSTOptions.TEST_ALIGN_TO_CASTLE);
        testChooser.addObject("(9) Test Lift", StrongholdConstants.diagnosticPOSTOptions.TEST_LIFTER);
        testChooser.addObject("(10) Test Motion Profile", StrongholdConstants.diagnosticPOSTOptions.TEST_MOTION_PROFILE);
        testChooser.addObject("(11) Test Global Positioning", StrongholdConstants.diagnosticPOSTOptions.TEST_GLOBAL_POSITIONING);
        testChooser.addObject("(12) Test With Joystick", StrongholdConstants.diagnosticPOSTOptions.TEST_JOYSTICK);
        testChooser.addDefault("(-1) Do Nothing", StrongholdConstants.diagnosticPOSTOptions.TEST_NONE);
        SmartDashboard.putData("Test Init Chooser", testChooser);
	}

    public void testMotors() {
        testInitChoosers();

        testMotorChooser.addObject("(0) Test Actuator", StrongholdRobotConfigurationManager.motorTests.TEST_ACTUATOR);
        testMotorChooser.addObject("(1) Test Left Shooter", StrongholdRobotConfigurationManager.motorTests.TEST_LEFT_SHOOTER);
        testMotorChooser.addObject("(2) Test Right Shooter", StrongholdRobotConfigurationManager.motorTests.TEST_RIGHT_SHOOTER);
        testMotorChooser.addObject("(3) Test Drive Left Master", StrongholdRobotConfigurationManager.motorTests.TEST_DRIVE_LEFT_MASTER);
        testMotorChooser.addObject("(4) Test Drive Right Master", StrongholdRobotConfigurationManager.motorTests.TEST_DRIVE_RIGHT_MASTER);
        testMotorChooser.addObject("(5) Test Drive Left Slave", StrongholdRobotConfigurationManager.motorTests.TEST_DRIVE_LEFT_SLAVE);
        testMotorChooser.addObject("(6) Test Drive Right Slave", StrongholdRobotConfigurationManager.motorTests.TEST_DRIVE_RIGHT_SLAVE);
        testChooser.addDefault("(-1) Do Nothing", StrongholdRobotConfigurationManager.motorTests.TEST_NONE);
        SmartDashboard.putData("Motor Test Chooser", testMotorChooser);
    }
}
