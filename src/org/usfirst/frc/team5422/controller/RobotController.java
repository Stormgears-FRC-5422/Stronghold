package org.usfirst.frc.team5422.controller;

/*
 * @author suren
 */

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.commands.CrossDefenseCommand;
import org.usfirst.frc.team5422.commands.buttonCommands.*;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdUtils;

public class RobotController {
    public static void doActionsOnButtonPress(int buttonID) {
        switch (buttonID) {
            case StrongholdConstants.BIG_BLUE_BUTTON_ID:
                BigBlueCommand bigBlue = new BigBlueCommand();
                bigBlue.start();
                break;

            case StrongholdConstants.ORANGE_SWITCH_ID:
                OrangeSwitchCommand orangeSwitch = new OrangeSwitchCommand();
                orangeSwitch.start();
                break;

            case StrongholdConstants.WHITE_BUTTON_ID:
                //Nothing yet
                break;

            case StrongholdConstants.RED_BUTTON_ID:
                //Not used with java code
                break;

            case StrongholdConstants.YELLOW_BUTTON_ID:
                //Not used with java code
                break;

            case StrongholdConstants.GREEN_BUTTON_ID:
                //Cross defense
                int position = DSIO.getSelectedDefensePosition();

                StrongholdRobot.defensePositionSelected = position;
                StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(position);

                CrossDefenseCommand cross = new CrossDefenseCommand();

                cross.start();
                break;

            case StrongholdConstants.BLUE_BUTTON_ID:
                //Lock onto goal
                SmallBlueCommand blue = new SmallBlueCommand();

                blue.start();
                break;

            case StrongholdConstants.BLACK_BUTTON_ID:
                //Nothing yet
                break;
        }
    }

    public static void doActionsOnSliderPositions() {
        AngleSliderCommand angle = new AngleSliderCommand();

        angle.start();
    }
}
