package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

/**
 * @author Michael
 * This class handles push events for button 8 (the white button)
 */
public class Button8 extends Command {

    @Override
    protected void initialize() {
        System.out.println("Initializing command for button 8 press.");
    }

    @Override
    protected void execute() {
        System.out.println("Shooting based on push of white button...");
        StrongholdRobot.shooterSubsystem.intake();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
