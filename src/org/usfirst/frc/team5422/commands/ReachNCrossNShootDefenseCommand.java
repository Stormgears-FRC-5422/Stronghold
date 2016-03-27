package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 *@author Suren
 */
public class ReachNCrossNShootDefenseCommand extends DefenseCommand {

    public ReachNCrossNShootDefenseCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(StrongholdRobot.navigatorSubsystem);   	
    }

    // Called just before this Command runs the first time
//    protected void initialize() {
//        System.out.println("initializing CrossDefenseCommand");
//    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("[AutoComm] Robot crossing defense...");
    	if (defense != null) {
    		defense.reachNCrossNShoot(defenseTypeSelected, defensePositionSelected);
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("[AutoComm] Robot crossed defense");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
