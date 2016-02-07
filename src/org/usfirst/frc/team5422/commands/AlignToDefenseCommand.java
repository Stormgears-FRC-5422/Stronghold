package org.usfirst.frc.team5422.commands;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignToDefenseCommand extends Command {
	
	defenseTypeOptions defense;

    public AlignToDefenseCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // Use requires() here to declare subsystem dependencies
        requires(StrongholdRobot.navigatorSubsystem);
        
        defense = (defenseTypeOptions) StrongholdRobot.dsio.defenseChooser.getSelected(); 
        
        switch(defense){
        	case CHIVAL_DE_FRISE:
        		break;
        		
        	case LOW_BAR:
        		break;
        		
        	case MOAT:
        		break;
        		
        	case PORTCULLIS:
        		break;
        		
        	case RAMPARTS:
        		break;
        		
        	case ROCK_WALL:
        		break;
        		
        	case ROUGH_TERRAIN:
        		break;
        		
        	case SALLYPORT:
        		break;
        	
        	case NONE:
        		break;
        		
        	default:
        		break;
        }
        
    }

	// Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	String temp  = "[AutoComm] Robot aligning to shoot ";
    	
    	switch(defense){
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
        return false;
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
