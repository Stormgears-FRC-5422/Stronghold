package org.usfirst.frc.team5422.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Michael
 */
public class LiftingCommandGroup extends CommandGroup {
    public GrapplingCommand grapplingCommand;
    public LiftingCommand liftingCommand;

    public LiftingCommandGroup() {
        grapplingCommand = new GrapplingCommand();
        liftingCommand = new LiftingCommand();

        addSequential(grapplingCommand);
        addSequential(liftingCommand);
    }

    protected void initialize() {
        System.out.println("LiftingCommandGoup initializing.");
    }
}
