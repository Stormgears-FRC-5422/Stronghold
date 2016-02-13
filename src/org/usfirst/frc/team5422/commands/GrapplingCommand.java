package org.usfirst.frc.team5422.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 * @author Michael
 */
public class GrapplingCommand extends Command {
    public GrapplingCommand() {
        super();
        requires(StrongholdRobot.grapplerSubsystem);
    }

    @Override
    protected void initialize() {
        System.out.println("Initializing GrapplingCommand.");


        System.out.println("Ended initialization of GrapplingCommand");
    }

    @Override
    protected void execute() {
        StrongholdRobot.grapplerSubsystem.alignToCastle();
        StrongholdRobot.grapplerSubsystem.grappleToCastle();
    }

    @Override
    protected boolean isFinished() {
        System.out.println("Robot has aligned and grappled to castle.");
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
