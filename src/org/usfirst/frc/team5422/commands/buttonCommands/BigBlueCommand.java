package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

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
//        if (DSIO.assistShoot) {
//            StrongholdConstants.shootOptions bestShootOption = ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption);
//
//            double theta = ShooterHelper.findHorizontalAngleToGoal(bestShootOption);
//
//            StrongholdRobot.navigatorSubsystem.turnTo(theta);
//
//            double shooterAngle = StrongholdRobot.shooterSubsystem.getAngle(bestShootOption);
//
//            StrongholdRobot.shooterSubsystem.changeAngle(shooterAngle);
//        }

        StrongholdRobot.shooterSubsystem.shoot(StrongholdConstants.shootOptions.HIGH_CENTER);
        DSIO.shooterRunning = true;
        DSIO.assistShoot = false;
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
