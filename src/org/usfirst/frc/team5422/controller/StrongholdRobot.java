package org.usfirst.frc.team5422.controller;


import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.commands.AutonomousCommandGroup;
import org.usfirst.frc.team5422.commands.GrapplingCommand;
import org.usfirst.frc.team5422.commands.LiftingCommandGroup;
import org.usfirst.frc.team5422.lifter.Grappler;
import org.usfirst.frc.team5422.lifter.Lifter;
import org.usfirst.frc.team5422.navigator.Driver;
import org.usfirst.frc.team5422.navigator.Navigator;
import org.usfirst.frc.team5422.opener.Opener;
import org.usfirst.frc.team5422.opener.SallyPortOpener;
import org.usfirst.frc.team5422.shooter.BallShooter;
import org.usfirst.frc.team5422.shooter.Shooter;
import org.usfirst.frc.team5422.utils.PIDTuner;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.diagnosticPOSTOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.alliance;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
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
    public static Navigator navigatorSubsystem;
    public static Shooter shooterSubsystem;
    public static Opener openerSubsystem;
    public static Grappler grapplerSubsystem;
    public static Lifter lifterSubsystem;

    public static DSIO dsio;
    public static Driver driver;
    public static PIDTuner pidTuner;
    public static Gyro gyro;
    public static Ultrasonic usonic;


    public static defenseTypeOptions defenseTypeSelected;
    public static int defensePositionSelected;
    public static shootOptions shootOptionSelected;
    public static alliance allianceSelected;

    public static boolean teleopNotRunning;

    RobotDrive myRobot;
    Joystick stick;

    LiveWindow lw;

    public Command autonomousCommand;
    public Command liftingCommandGroup;

    public StrongholdRobot() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard
        lw = new LiveWindow();

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
        dsio = new DSIO(0, 0);
        driver = new Driver(CANTalon.TalonControlMode.Speed);

        DSIO.createUI();
        autonomousCommand = new AutonomousCommandGroup();
        liftingCommandGroup = new LiftingCommandGroup();


        System.out.println("robot init ended.");
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomousInit() {
        System.out.println("auto init started.");
        
        if (autonomousCommand != null) {
            defenseTypeSelected = (defenseTypeOptions) DSIO.defenseChooser.getSelected();
            defensePositionSelected = DSIO.getSelectedDefensePosition();
            shootOptionSelected = (shootOptions) DSIO.shootChooser.getSelected();
            allianceSelected = (alliance) DSIO.allianceChooser.getSelected();

            System.out.println("Selecting from Defense Type as " + defenseTypeSelected + " at position " + defensePositionSelected + " and Goal selected as " + shootOptionSelected);

            autonomousCommand.start();

            //Only for testing purposes
            liftingCommandGroup.start();
        }
        

        teleopNotRunning = true;

        //Get input from DSIO smart dashboard
        //System.out.println("Defense position selected for autonomous is " + DSIO.getSelectedDefensePosition() + " inside auto init.");


        System.out.println("auto init ended.");
    }

    public void autonomousPeriodic() {
        System.out.println("auto periodic started.");
        if (autonomousCommand != null) {
            Scheduler.getInstance().run();
        }


        System.out.println("auto periodic ended.");
    }

    public void teleopInit() {
        System.out.println("teleop init started.");
        if (autonomousCommand != null) autonomousCommand.cancel();
        teleopNotRunning = false;

        System.out.println("teleop init ended.");
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void teleopPeriodic() {
        System.out.println("teleop started.");

        Scheduler.getInstance().run();

        //Run the openDrive() method
        Driver.openDrive(DSIO.getLinearY(), DSIO.getLinearX(), CANTalon.TalonControlMode.Speed);

        System.out.println("teleop ended.");
    }

	/**  function is called periodically during disable */
	public void disabledInit() {
		
	}
	
	/**  function is called periodically during disable */
	public void disabledPeriodic() {
		//reset all necessary things
//		SmartDashboard.putNumber("Total Distance: ", 0);
//		SmartDashboard.putNumber("Max Velocity: ", 0);
		
		Driver.resetTrapezoid();
	}

	public void testInit() {
		
	}
	
    /**
     * Runs during test mode
     */
    public void testPeriodic() {
        System.out.println("In Roborio Test Mode...initiating Power On Self Test (POST) Diagnostics ...");

        diagnosticPOSTOptions key = diagnosticPOSTOptions.TEST_CHASSIS_DRIVE;

        switch (key) {
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
    			Driver.moveTrapezoid(8192, 8192, 0.5, 0.5);
    			break;

            default:
                break;
        }
    }

}
