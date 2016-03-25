package org.usfirst.frc.team5422.controller;


import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.DSIO.SmartDashboardChooser;
import org.usfirst.frc.team5422.commands.auto.AutoReachNCrossCommandGroup;
import org.usfirst.frc.team5422.commands.auto.AutonomousCommandGroup;
import org.usfirst.frc.team5422.lifter.Grappler;
import org.usfirst.frc.team5422.lifter.Lifter;
import org.usfirst.frc.team5422.navigator.*;
import org.usfirst.frc.team5422.opener.Opener;
import org.usfirst.frc.team5422.opener.SallyPortOpener;
import org.usfirst.frc.team5422.shooter.BallShooter;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.sensors.Vision;
import org.usfirst.frc.team5422.utils.*;
import org.usfirst.frc.team5422.utils.StrongholdConstants.autonomousModeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.diagnosticPOSTOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.startPositionOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.alliance;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * This is a demo program showing the use of the RobotDrive class
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
    public static boolean rhinoInUse = true, bbInUse = true;



    public static Navigator navigatorSubsystem;
    public static BallShooter shooterSubsystem;
    public static Opener openerSubsystem;
    public static Grappler grapplerSubsystem;
    public static Lifter lifterSubsystem;
    public static RobotConfigurationFileReader robotPropertiesGetter;
//    public static LiveWindow lw;

    public static DSIO dsio;
    public static DriverInterface driver;
    public static Vision vision;

    public static startPositionOptions startPositionSelected; 
    public static defenseTypeOptions defenseTypeSelected;
    public static int defensePositionSelected;
    public static shootOptions shootOptionSelected;
    public static autonomousModeOptions autoModeOptionSelected = autonomousModeOptions.REACH_N_CROSS;
    public static alliance allianceSelected;
    public static diagnosticPOSTOptions diagnosticTestSelected;
    public static Double initialX = 0.0, initialY = 0.0;

    public static boolean teleopNotRunning;

    public Command autonomousCommand = null;
    public Command liftingCommandGroup;
    
    public StrongholdRobot() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard

        robotPropertiesGetter = new RobotConfigurationFileReader();
        if (robotPropertiesGetter.getRobotInUse().equals(StrongholdConstants.RHINO)) {
            driver = new RhinoDriver();
        }
        else {
            driver = new StrongholdDriver();
        }

//        lw = new LiveWindow();

        navigatorSubsystem = new Navigator();
        shooterSubsystem = new BallShooter();
        openerSubsystem = new SallyPortOpener();
        grapplerSubsystem = new Grappler();
        lifterSubsystem = new Lifter();
        vision = new Vision();


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

        getSmartDashboardDataSelections();

        //selects and sets the autonomous command
        selectAutonomousCommand();

    	if (autonomousCommand != null) {

            GlobalMapping.resetValues(initialX, initialY, Math.PI / 2);

            System.out.println("Selecting from Defense Type as " + defenseTypeSelected + " at position " + defensePositionSelected + " and Goal selected as " + shootOptionSelected);

            //initialize the shooter is in horizontal to the ground position
            shooterSubsystem.changeAngle(0.0);

            autonomousCommand.start();

	        vision.turnOnLights();
	        driver.turnToAlignVision();
	        shooterSubsystem.changeAngle(Vision.getShooterAngle());

            //Only for testing purposes
            //liftingCommandGroup.start();
    	}

        teleopNotRunning = true;

//        GlobalMapping.resetValues(0, 0, Math.PI/2);
//        shooterSubsystem.changeAngle(0.0);
//        navigatorSubsystem.driveTo(0, 150-12);
//        vision.turnOnLights();
//        driver.turnToAlignVision();
//        shooterSubsystem.changeAngle(Vision.getShooterAngle());
        
        System.out.println("auto init ended.");
    }

    private void selectAutonomousCommand() {

    	switch (autoModeOptionSelected)
        {
            case REACH_N_CROSS:
            	//autonomous reach AND cross with NO shoot using SINGLE trapezoidal motion profile
                System.out.println("selecting reach 'n cross command.");
            	autonomousCommand = new AutoReachNCrossCommandGroup();
            	break;
            case REACH_N_CROSS_N_SHOOT:
            	//for autonomous reach, cross and shoot independently using separate trapezoidal motion profile
                System.out.println("selecting reach 'n cross 'n shoot command.");
            	autonomousCommand = new AutonomousCommandGroup();
            	break;
            case REACH:
            case NONE:
            default:
            	//autonomous reach AND cross with NO shoot using SINGLE trapezoidal motion profile
            	autonomousCommand = new AutoReachNCrossCommandGroup();
            	break;
        }
    	
    }
    
    private static void getSmartDashboardDataSelections() {
    	startPositionSelected = (startPositionOptions) SmartDashboardChooser.startPositionChooser.getSelected();
        defenseTypeSelected = (defenseTypeOptions) SmartDashboardChooser.defenseChooser.getSelected();
        shootOptionSelected = (shootOptions) SmartDashboardChooser.shootChooser.getSelected();
        autoModeOptionSelected = (autonomousModeOptions)SmartDashboardChooser.autonomousModeChooser.getSelected();
        allianceSelected = (alliance) SmartDashboardChooser.allianceChooser.getSelected();

//        defensePositionSelected = DSIO.getSelectedDefensePosition();

    	switch (startPositionSelected)
        {
            case FRONT_OF_DEFENSE_1_LOW_BAR:
                defensePositionSelected = 1; 
            	initialX =  StrongholdConstants.START_POSITION_1[0];
                initialY = StrongholdConstants.START_POSITION_1[1];
                break;
            case FRONT_OF_DEFENSE_2:
                defensePositionSelected = 2; 
            	initialX =  StrongholdConstants.START_POSITION_2[0];
                initialY = StrongholdConstants.START_POSITION_2[1];
                break;
            case FRONT_OF_DEFENSE_3:
                defensePositionSelected = 3; 
            	initialX =  StrongholdConstants.START_POSITION_3[0];
                initialY = StrongholdConstants.START_POSITION_3[1];
                break;
            case FRONT_OF_DEFENSE_4:
                defensePositionSelected = 4; 
            	initialX =  StrongholdConstants.START_POSITION_4[0];
                initialY = StrongholdConstants.START_POSITION_4[1];
                break;
            case FRONT_OF_DEFENSE_5:
                defensePositionSelected = 5; 
            	initialX =  StrongholdConstants.START_POSITION_5[0];
                initialY = StrongholdConstants.START_POSITION_5[1];
                break;
            case NONE:
            	initialX =  0.0;
                initialY = 0.0;
                break;
        }//End switch
    
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
        
        //only for testing
        GlobalMapping.getInstance().resetValues(0, 0, Math.PI/2);
        

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
        RobotController.doActionsOnSliderPositions();

        //TODO get rid of this comment
    	//StrongholdRobot.shooterSubsystem.changeAngle(StrongholdConstants.ACTUATOR_ARM_DOWN_ANGLE + (actuatorArmSliderValue/StrongholdConstants.ACTUATOR_ARM_ANGLE_CONVERSION_FACTOR));

        //Tell the rhinoDriver what goal is best for them, and whether they are within range
        if (ShooterHelper.isInBounds()) {
            System.out.println("within bounds, for goal: " + ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption).toString() + ".");
            SmartDashboard.putString("You are", " within bounds, for goal: " + ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption).toString() + ".");
        }
        else {

            SmartDashboard.putString("You are", " out of bounds.");
        }

        SmartDashboard.putNumber("Actuator position:", shooterSubsystem.actuator.getEncPosition());

        //Calculate shootOption
        shootOptionSelected = ShooterHelper.findBestGoal(dsio.teleopShootHeightOption);

        //Run the openDrive() method
        driver.openDrive(DSIO.getLinearX(), DSIO.getLinearY(), CANTalon.TalonControlMode.Speed);

        //Run WPILib commands
        Scheduler.getInstance().run();
    }

	/**  function is called periodically during disable */
	public void disabledInit() {
        //Stop all autonomous WPILib commands
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
	        Scheduler.getInstance().removeAll();
			autonomousCommand = null;
		}
		
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
                shooterSubsystem.shoot(StrongholdConstants.FULL_THROTTLE);                
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
//        lw.addActuator("Ball Shooter", "Arm", shooterSubsystem.actuator);
    }
}
