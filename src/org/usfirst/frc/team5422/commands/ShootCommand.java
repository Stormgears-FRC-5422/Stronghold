package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/**
 *@author suren
 */
public class ShootCommand extends DefenseCommand {
	private shootOptions shootOptionSelected;

    public ShootCommand() {
    	super();
        requires(StrongholdRobot.shooterSubsystem);
    }
    
    // Called just before this Command runs the first time
//    protected void initialize() {
//        System.out.println("initializing ShootCommand");
//    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//StrongholdRobot.shooterSubsystem.intakeAndShoot();
    	String temp  = "[AutoComm] Robot shooting ball... ";
		shootOptionSelected = StrongholdRobot.shootOptionSelected;
				
		switch(shootOptionSelected) {
		case HIGH_LEFT:
			temp += "HIGH_LEFT";
			break;
		case HIGH_CENTER:
			temp += "HIGH_CENTER";
			break;
		case HIGH_RIGHT:
			temp += "HIGH_RIGHT";
			break;
		case LOW_LEFT:
			temp += "LOW_LEFT";
			break;
		case LOW_RIGHT:
			temp += "LOW_RIGHT";
			break;
		default:
			break;
		}

		temp += ".";
		System.out.println(temp);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("[AutoComm] Robot shot ball");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
