package org.usfirst.frc.team5422.commands.buttonCommands;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.sensors.Vision;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        if (DSIO.assistShoot) {
            StrongholdRobot.driver.turnToAlignVision();            
    		if(!StrongholdRobot.gripNotWorking) {
	            StrongholdRobot.shooterSubsystem.changeAngleAssisted(Vision.getShooterAngle());
	            Timer.delay(0.5);
	            StrongholdRobot.shooterSubsystem.shoot(ShooterHelper.getSpeedMultiplier(DSIO.getSpeedSlider2Value()));
	            SmartDashboard.putNumber("Center Y: ", Vision.getCenterY());
	            SmartDashboard.putNumber("Distance: ", Vision.getRadialDistanceVision());
    		}
        	
        } else 
        	StrongholdRobot.shooterSubsystem.shoot(
        			ShooterHelper.getSpeedMultiplier(DSIO.getSpeedSlider2Value()));
        DSIO.shooterRunning = false;
    }

    @Override
    protected boolean isFinished() {
        Scheduler.getInstance().removeAll();
        return true;
    }

    @Override
    protected void end() {
    	//Stops shooter from running 2x by clearing all commands in queue
    }

    @Override
    protected void interrupted() {

    }
}
