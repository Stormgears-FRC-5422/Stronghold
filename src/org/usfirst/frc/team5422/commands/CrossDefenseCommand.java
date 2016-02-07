package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.Navigator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrossDefenseCommand extends DefenseCommand {

    public CrossDefenseCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super();
    	requires(StrongholdRobot.navigatorSubsystem);   	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("[AutoComm] Robot crossing defense...");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
