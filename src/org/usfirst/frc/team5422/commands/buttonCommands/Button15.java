package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdUtils;

/**
 * @author Michael
 *
 * This class handles the push events for Button15 (the red button)
 */
public class Button15 extends Command {

    @Override
    protected void initialize() {
        System.out.println("Initializing command for button 15 press.");
    }

    @Override
    protected void execute() {
        System.out.println("Crossing defense at position 0 based on push of red button...");
        StrongholdRobot.defensePositionSelected = 0;
        StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(0);
        StrongholdRobot.shootOptionSelected = StrongholdConstants.shootOptions.NONE;

        //Cross the defense

    }

    @Override
    protected boolean isFinished() {
        System.out.println("The defense at position 0 has been crossed.");
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}