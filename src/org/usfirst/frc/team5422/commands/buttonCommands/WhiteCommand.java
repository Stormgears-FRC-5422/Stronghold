package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdUtils;

/**
 * @author Michael
 */
public class WhiteCommand extends Command {
    @Override
    protected void initialize() {
        System.out.println("Initializing button command for: White Button");
    }

    @Override
    protected void execute() {

        if (DSIO.ignoreJoystick) {
            //Lock onto nearest goal
            double x = GlobalMapping.getX();
            double y = GlobalMapping.getY();

            StrongholdConstants.shootOptions bestShootOption =ShooterHelper.findBestGoal(DSIO.teleopShootHeightOption);

            double theta = ShooterHelper.findHorizontalAngleToGoal(bestShootOption);

            StrongholdRobot.navigatorSubsystem.driveTo(x, y, theta);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        DSIO.ignoreJoystick = false;
    }

    @Override
    protected void interrupted() {

    }
}