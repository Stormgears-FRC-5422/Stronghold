package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 * @author Michael
 */
public class OrangeSwitchCommand extends Command {

    @Override
    protected void initialize() {
        System.out.println("Initializing button command for: Orange Switch");
    }

    @Override
    protected void execute() { 
    	if (DSIO.assistShoot == true) StrongholdRobot.shooterSubsystem.intakeAssisted();
    	else StrongholdRobot.shooterSubsystem.intakeManual();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
