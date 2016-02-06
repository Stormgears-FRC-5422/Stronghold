package org.usfirst.frc.team5422.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {
	public AlignToDefenseCommand alignToDefense;
	public CrossDefenseCommand crossDefense;
	public PositionToShootCommand positionToShoot;
	public ShootCommand shootCommand;
	
    public  AutonomousCommandGroup() {
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
    	alignToDefense = new AlignToDefenseCommand();
    	crossDefense = new CrossDefenseCommand();
    	positionToShoot = new PositionToShootCommand();
    	shootCommand = new ShootCommand();
    }
    
    protected void initialize() {
    	addSequential(alignToDefense);
    	addSequential(crossDefense);
    	addSequential(positionToShoot);
    	addSequential(shootCommand);
    	
    }
}
