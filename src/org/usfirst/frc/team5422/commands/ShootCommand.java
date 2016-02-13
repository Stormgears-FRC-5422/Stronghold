package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 *@author suren
 */
public class ShootCommand extends DefenseCommand {

    public ShootCommand() {
    	super();
        requires(StrongholdRobot.shooterSubsystem);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("initializing ShootCommand");
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//StrongholdRobot.shooterSubsystem.intakeAndShoot();
    	System.out.println("[AutoComm] Robot shooting ball...");
    	
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
