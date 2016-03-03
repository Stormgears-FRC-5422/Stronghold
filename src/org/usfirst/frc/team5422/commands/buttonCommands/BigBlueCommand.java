package org.usfirst.frc.team5422.commands.buttonCommands;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author
 */
public class BigBlueCommand extends Command {

    @Override
    protected void initialize() {
        System.out.println("Initializing button command for: Big Blue Button");
    }

    @Override
    protected void execute() {
        DSIO.shooterRunning = true;
        if (DSIO.assistShoot) 
        	StrongholdRobot.shooterSubsystem.shoot(
        		ShooterHelper.calculateAngle(StrongholdRobot.shootOptionSelected), StrongholdConstants.FULL_THROTTLE);
        else 
        	StrongholdRobot.shooterSubsystem.shoot(
        			ShooterHelper.getSpeedMultiplier(DSIO.getSpeedSlider2Value()));
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
