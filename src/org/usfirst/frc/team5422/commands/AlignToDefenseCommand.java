package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 *@author suren
 */
public class AlignToDefenseCommand extends DefenseCommand {
	
    public AlignToDefenseCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // Use requires() here to declare subsystem dependencies
        super();
        requires(StrongholdRobot.navigatorSubsystem);
                
    }

	// Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("initializing AlignToDefenseCommand");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	String temp  = "[AutoComm] Robot aligning to shoot after crossing the ";
    	defense.align(defenseTypeSelected, defensePositionSelected);
    	
    	switch(defenseTypeSelected){
	    	case CHIVAL_DE_FRISE:
	    		temp += "chival de frise";
	    		break;
	    		
	    	case LOW_BAR:
	    		temp += "low bar";
	    		break;
	    		
	    	case MOAT:
	    		temp += "moat";
	    		break;
	    		
	    	case PORTCULLIS:
	    		temp += "portcullis";
	    		break;
	    		
	    	case RAMPARTS:
	    		temp += "ramparts";
	    		break;
	    		
	    	case ROCK_WALL:
	    		temp += "rock wall";
	    		break;
	    		
	    	case ROUGH_TERRAIN:
	    		temp += "rough terrain";
	    		break;
	    		
	    	case SALLYPORT:
	    		temp += "sallyport";
	    		break;
	    	
	    	case NONE:
	    		temp = "[AutoComm] Robot not aligning to shoot";
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
    	System.out.println("[AutoComm] Robot finished aligning to shoot.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
