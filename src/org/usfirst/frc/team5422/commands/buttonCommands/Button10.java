package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdUtils;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootHeightOptions;

/**
 * @author Michael
 *
 * This class handles the push events for Button10 (the big blue button)
 */
public class Button10 extends Command {

    @Override
    protected void initialize() {
        System.out.println("Initializing command for button 10 press.");
    }

    @Override
    protected void execute() {
        System.out.println("Shooting based on push of big blue button...");
        //Find best goal based on position of green switch (up = high; down = low)
        if (DSIO.buttonBoard.getRawButton(StrongholdConstants.GREEN_SWITCH_ID)) {
            StrongholdRobot.shootOptionSelected = StrongholdUtils.findBestGoal(shootHeightOptions.HIGH);

            //Shoot
            StrongholdRobot.shooterSubsystem.shoot(StrongholdUtils.findBestGoal(shootHeightOptions.HIGH));
        }
        else {
            StrongholdRobot.shootOptionSelected = StrongholdUtils.findBestGoal(shootHeightOptions.LOW);

            //Shoot
            StrongholdRobot.shooterSubsystem.shoot(StrongholdUtils.findBestGoal(shootHeightOptions.LOW));
        }
    }

    @Override
    protected boolean isFinished() {
        System.out.println("The ball has been shot towards the best goal.");
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
