package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 *
 */
public class ReachDefenseCommand extends DefenseCommand {

    public ReachDefenseCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(StrongholdRobot.navigatorSubsystem);   	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
		System.out.println("initializing ReachDefenseCommand");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("[AutoComm] Robot reaching defense...");
    	if (defense != null) {
    		defense.reach(defenseTypeSelected, defensePositionSelected);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
