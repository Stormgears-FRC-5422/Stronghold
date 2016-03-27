package org.usfirst.frc.team5422.commands.auto;

import org.usfirst.frc.team5422.commands.ReachNCrossNShootDefenseCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@author Suren Karavettil
 */
public class AutoReachNCrossNShootCommandGroup extends CommandGroup {
	public ReachNCrossNShootDefenseCommand reachNcrossNshootDefense;
	    
    //overloading
    public AutoReachNCrossNShootCommandGroup() {
    	// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	reachNcrossNshootDefense = new ReachNCrossNShootDefenseCommand();
		addSequential(reachNcrossNshootDefense);
	}

	protected void initialize() {
		System.out.println("Initializing autonomous Reach and Cross and Shoot command group.");
    }
	
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("[AutoComm] Autonomous Reach and Cross and Shoot Command Group is Finished");
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("[AutoComm] Autonomous Reach and Cross and Shoot Command Group is Ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("[AutoComm] Autonomous Reach and Cross and Shoot Command Group is Interrupted");
    	//ensure that the command is finished if interrupted
    	isFinished();
    }
	
}
