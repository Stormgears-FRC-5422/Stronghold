package org.usfirst.frc.team5422.controller;


import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.DSIO.SmartDashboardChooser;
import org.usfirst.frc.team5422.commands.AutonomousCommandGroup;
import org.usfirst.frc.team5422.commands.LiftingCommandGroup;
import org.usfirst.frc.team5422.lifter.Grappler;
import org.usfirst.frc.team5422.lifter.Lifter;
import org.usfirst.frc.team5422.navigator.*;
import org.usfirst.frc.team5422.opener.Opener;
import org.usfirst.frc.team5422.opener.SallyPortOpener;
import org.usfirst.frc.team5422.shooter.BallShooter;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.*;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.diagnosticPOSTOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.alliance;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the rhinoDriver station or the field controls.
 * <p>
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * <p>
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */


/*
 * @author Suren Karavettil
 */

public class StrongholdRobot extends IterativeRobot {

    //Set this boolean to false if using official robot
    public static boolean rhinoInUse = true;



    public static Navigator navigatorSubsystem;
    public static BallShooter shooterSubsystem;
    public static Opener openerSubsystem;
    public static Grappler grapplerSubsystem;
    public static Lifter lifterSubsystem;
    public static RobotConfigurationFileReader robotPropertiesGetter;

    public static DSIO dsio;
    public static Driver driver;

    public static defenseTypeOptions defenseTypeSelected;
    public static int defensePositionSelected;
    public static shootOptions shootOptionSelected;
    public static alliance allianceSelected;
    public static diagnosticPOSTOptions diagnosticTestSelected;
    public static Double initialX = 0.0, initialY = 0.0;

    public static boolean teleopNotRunning;

    public Command autonomousCommand = null;
    public Command liftingCommandGroup;
    
    public StrongholdRobot() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard

//        robotPropertiesGetter = new RobotConfigurationFileReader();
//        if (robotPropertiesGetter.getRobotInUse().equals(StrongholdConstants.RHINO)) {
//            driver = new RhinoDriver();
//        }
//        else {
            driver = new StrongholdDriver();
//        }

        navigatorSubsystem = new Navigator();
        shooterSubsystem = new BallShooter();
        openerSubsystem = new SallyPortOpener();
        grapplerSubsystem = new Grappler();
        lifterSubsystem = new Lifter();


        teleopNotRunning = true;
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("robot init started.");
        dsio = new DSIO(0, 1);
        
        DSIO.createUI();

        DSIO.choosers.autoInitChoosers();

        System.out.println("robot init ended.");
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomousInit() {
        System.out.println("auto init started.");
        
        autonomousCommand = new AutonomousCommandGroup();
//        liftingCommandGroup = new LiftingCommandGroup();

        if (autonomousCommand != null) {
            defenseTypeSelected = (defenseTypeOptions) SmartDashboardChooser.defenseChooser.getSelected();
            defensePositionSelected = DSIO.getSelectedDefensePosition();
            shootOptionSelected = (shootOptions) SmartDashboardChooser.shootChooser.getSelected();
            allianceSelected = (alliance) SmartDashboardChooser.allianceChooser.getSelected();
            initialX = ((Double[]) SmartDashboardChooser.startPositionChooser.getSelected())[0];
            initialY = ((Double[]) SmartDashboardChooser.startPositionChooser.getSelected())[1];

            GlobalMapping.resetValues(initialX, initialY, Math.PI / 2);

            System.out.println("Selecting from Defense Type as " + defenseTypeSelected + " at position " + defensePositionSelected + " and Goal selected as " + shootOptionSelected);

            autonomousCommand.start();

            //Only for testing purposes
//            liftingCommandGroup.start();
        }
        
        teleopNotRunning = true;

        //Get input from DSIO smart dashboard
        //System.out.println("Defense position selected for autonomous is " + DSIO.getSelectedDefensePosition() + " inside auto init.");


        System.out.println("auto init ended.");
    }

    public void autonomousPeriodic() {
        
        if (autonomousCommand != null) {
            Scheduler.getInstance().run();
        }


        
    }

    public void teleopInit() {
        System.out.println("teleop init started.");
        if (autonomousCommand != null) autonomousCommand.cancel();
        teleopNotRunning = false;

//        RhinoDriver.initializeTrapezoid();
        System.out.println("teleop init ended.");
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void teleopPeriodic() {
        //Run actions based on input from button board
    	
    	int buttonID = DSIO.getButtons();
        RobotController.doActionsOnButtonPress(buttonID);

        double speedSliderVal = DSIO.getSpeedSlider2Value();
        double actuatorArmSliderValue = DSIO.getActuatorSliderValue();   	
    	SmartDashboard.putNumber("Slider Value: ", actuatorArmSliderValue);
//    	StrongholdRobot.shooterSubsystem.changeAngle(StrongholdConstants.ACTUATOR_ARM_DOWN_ANGLE + (actuatorArmSliderValue/StrongholdConstants.ACTUATOR_ARM_ANGLE_CONVERSION_FACTOR));
    	StrongholdRobot.shooterSubsystem.changeAngle(actuatorArmSliderValue);
        
        //Tell the rhinoDriver what goal is best for them, and whether they are within range
        if (ShooterHelper.isInBounds()) {
            System.out.println("within bounds, for goal: " + ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption).toString() + ".");
            SmartDashboard.putString("You are", " within bounds, for goal: " + ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption).toString() + ".");
        }
        else {

            SmartDashboard.putString("You are", " out of bounds.");
        }

        //Calculate shootOption
//        shootOptionSelected = ShooterHelper.findBestGoal(dsio.teleopShootHeightOption);

        //Run the openDrive() method
        driver.openDrive(DSIO.getLinearX(), DSIO.getLinearY(), CANTalon.TalonControlMode.Speed);

        //Adjust actuator
//        shooterSubsystem.fineTune(dsio.getFineTunerValue());

        //Run WPILib commands
        Scheduler.getInstance().run();
    }

	/**  function is called periodically during disable */
	public void disabledInit() {
		
	}
	
	/**  function is called periodically during disable */
	public void disabledPeriodic() {
		//reset all necessary things
//		SmartDashboard.putNumber("Total Distance: ", 0);
//		SmartDashboard.putNumber("Max Velocity: ", 0);
	}

	public void testInit() {
		
		System.out.println("In Roborio Test Mode...initiating Power On Self Test (POST) Diagnostics ...");
		
		diagnosticTestSelected = null;
		DSIO.choosers.testInitChoosers();

		while (diagnosticTestSelected == null) {
			diagnosticTestSelected = (diagnosticPOSTOptions) SmartDashboardChooser.testChooser.getSelected();
		}
		
        switch (diagnosticTestSelected) {
            case TEST_GYRO:
                System.out.println("Testing Gyro");
                break;

            case TEST_ULTRASONIC:
                System.out.println("Testing Ultrasonic");
                break;

            case TEST_IR:
                System.out.println("Testing IR");
                break;

            case TEST_TALON_LEFT_MASTER:
                System.out.println("Testing Left Master Talon");
                break;

            case TEST_TALON_RIGHT_MASTER:
                System.out.println("Testing Right Master Talon");
                break;

            case TEST_CHASSIS_DRIVE:
                System.out.println("Testing the chassis drive");
                break;

            case TEST_SHOOTER:
                System.out.println("Testing the shooter");
                shooterSubsystem.shoot(shootOptions.HIGH_CENTER);                
                break;

            case TEST_GRAPPLER:
                System.out.println("Testing the grappler");
                grapplerSubsystem.grappleToCastle();
                break;

            case TEST_ALIGN_TO_CASTLE:
                break;

            case TEST_LIFTER:
                System.out.println("Testing the lifter");
                break;
    		case TEST_MOTION_PROFILE:
    			System.out.println("Testing motion profile");
    			break;
    		case TEST_GLOBAL_POSITIONING:
    			System.out.println("Testing global positioning");
    			navigatorSubsystem.driveTo(0, 60);
    			navigatorSubsystem.driveTo(-20, 80);
    			navigatorSubsystem.driveTo(0, 0);
    			navigatorSubsystem.driveTo(0, 0);
    			navigatorSubsystem.driveTo(0, 0);
    	//		navigatorSubsystem.driveTo(Math.PI/2);
    			break;

            default:
                break;
        }
	}
	
    /**
     * Runs during test mode
     */
    public void testPeriodic() {
        
    }
}
