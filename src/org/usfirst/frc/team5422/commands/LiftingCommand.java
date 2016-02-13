package org.usfirst.frc.team5422.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 * @author Michael
 */
public class LiftingCommand extends Command {

    @Override
    protected void initialize() {
        System.out.println("Initializing LiftingCommand.");


        System.out.println("Ended initialization of GrapplingCommand");
    }

    @Override
    protected void execute() {
        StrongholdRobot.lifterSubsystem.lift();
    }

    @Override
    protected boolean isFinished() {
        System.out.println("Robot has been lifted.");
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
