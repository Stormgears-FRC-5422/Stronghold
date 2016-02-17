package org.usfirst.frc.team5422.DSIO;

import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.endOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/*
 * @author Michael
 * @author Suren Karavettil
 * 
 */


public class SmartDashboardChooser {
    public static SendableChooser defenseChooser, shootChooser, endChooser, allianceChooser, testChooser, startPositionChooser;

    public SmartDashboardChooser() {
		//create choosers

    	//create defense chooser
    	defenseChooser = new SendableChooser();
        //Create shoot chooser
        shootChooser = new SendableChooser();
        //Create last move chooser
        endChooser = new SendableChooser();
        //Create alliance selector
        allianceChooser = new SendableChooser();
        //Create subsystem test selector
        testChooser = new SendableChooser();
        //Create start position chooser
        startPositionChooser = new SendableChooser();
        
	}
	
	public void initChoosers() {

		if (defenseChooser == null) System.out.println("say error in initializing choosers");
        defenseChooser.addDefault("(0) Low Bar", defenseTypeOptions.LOW_BAR);
        defenseChooser.addObject("(1) Portcullis", defenseTypeOptions.PORTCULLIS);
        defenseChooser.addObject("(2) Chival de Frise", defenseTypeOptions.CHIVAL_DE_FRISE);
        defenseChooser.addObject("(3) Moat", defenseTypeOptions.MOAT);
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

        endChooser.addDefault("Go to teleop starting position.", endOptions.TELEOP_STARTING_POSITION);
        endChooser.addObject("Stay in place.", endOptions.NONE);
        SmartDashboard.putData("End chooser", endChooser);

        allianceChooser.addDefault("Red", StrongholdConstants.alliance.RED);
        allianceChooser.addObject("Blue", StrongholdConstants.alliance.BLUE);
        SmartDashboard.putData("Alliance Chooser", allianceChooser);

        startPositionChooser.addDefault("Position 1 (Left Most, Near Low Bar)", StrongholdConstants.START_POSITION_1);
        startPositionChooser.addObject("Position 2", StrongholdConstants.START_POSITION_2);
        startPositionChooser.addObject("Position 3", StrongholdConstants.START_POSITION_3);
        startPositionChooser.addObject("Position 4", StrongholdConstants.START_POSITION_4);
        startPositionChooser.addObject("Position 5", StrongholdConstants.START_POSITION_5);
        startPositionChooser.addObject("Position 6 (Right Most, Near Secret Passage)", StrongholdConstants.START_POSITION_6);
        
//        testInitChoosers();
	}
	
	public void autoInitChoosers() {
        //Add other defense position text boxes
        SmartDashboard.putNumber("Defense at Position 1", -1);
        SmartDashboard.putNumber("Defense at Position 2", -1);
        SmartDashboard.putNumber("Defense at Position 3", -1);
        SmartDashboard.putNumber("Defense at Position 4", -1);
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
        testChooser.addObject("(-1) Do Nothing", StrongholdConstants.diagnosticPOSTOptions.TEST_NONE);
        SmartDashboard.putData("Test Init Chooser", testChooser);
	}	
}
