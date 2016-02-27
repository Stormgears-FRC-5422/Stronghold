package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 * Created by michael on 2/26/16.
 */
public class SpeedSliderCommand extends Command {

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (!DSIO.assistShoot) {
            System.out.println("Adjusting speed of motors with Speed Slider...");

            StrongholdRobot.shooterSubsystem.setSpeedMultiplier(DSIO.getSpeedSlider2Value());
        }
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
