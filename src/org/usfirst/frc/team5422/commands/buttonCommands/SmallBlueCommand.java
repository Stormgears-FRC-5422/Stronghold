package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.sensors.Vision;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/**
 * @author Michael
 */
public class SmallBlueCommand extends Command {
    @Override
    protected void initialize() {
        System.out.println("Initializing button command for: Small Blue Button");
    }

    @Override
    protected void execute() {

        if (DSIO.assistShoot) {
            //Lock onto nearest goal
            //double x = GlobalMapping.getInstance().getX();
            //double y = GlobalMapping.getInstance().getY();

            StrongholdConstants.shootOptions bestShootOption = ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption);

            //If the option is set to high, calculate angles automatically
            if (bestShootOption == shootOptions.HIGH_LEFT ||
                    bestShootOption == shootOptions.HIGH_CENTER ||
                    bestShootOption == shootOptions.HIGH_RIGHT)
            {
                StrongholdRobot.driver.turnToAlignVision();
                StrongholdRobot.shooterSubsystem.changeAngleAssisted(Vision.getShooterAngle());
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        DSIO.assistShoot = false;
    }

    @Override
    protected void interrupted() {

    }
}
