package org.usfirst.frc.team5422.controller;

/*
 * @author suren
 */

import org.usfirst.frc.team5422.commands.CrossDefenseCommand;
import org.usfirst.frc.team5422.commands.buttonCommands.BigBlueCommand;
import org.usfirst.frc.team5422.commands.buttonCommands.OrangeSwitchCommand;
import org.usfirst.frc.team5422.commands.buttonCommands.WhiteCommand;
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
                //Lock-onto-goal button
                WhiteCommand white = new WhiteCommand();
                white.start();
                break;

            case StrongholdConstants.RED_BUTTON_ID:
                //Cross defense 1
                StrongholdRobot.defensePositionSelected = 1;
                StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(1);
                StrongholdRobot.shootOptionSelected = StrongholdConstants.shootOptions.NONE;

                CrossDefenseCommand cross1 = new CrossDefenseCommand();

                cross1.start();
                break;

            case StrongholdConstants.YELLOW_BUTTON_ID:
                //Cross defense 2
                StrongholdRobot.defensePositionSelected = 2;
                StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(2);
                StrongholdRobot.shootOptionSelected = StrongholdConstants.shootOptions.NONE;

                CrossDefenseCommand cross2 = new CrossDefenseCommand();

                cross2.start();
                break;

            case StrongholdConstants.GREEN_BUTTON_ID:
                //Cross defense 3
                StrongholdRobot.defensePositionSelected = 3;
                StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(3);
                StrongholdRobot.shootOptionSelected = StrongholdConstants.shootOptions.NONE;

                CrossDefenseCommand cross3 = new CrossDefenseCommand();

                cross3.start();
                break;

            case StrongholdConstants.BLUE_BUTTON_ID:
                //Cross defense 4
                StrongholdRobot.defensePositionSelected = 4;
                StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(4);
                StrongholdRobot.shootOptionSelected = StrongholdConstants.shootOptions.NONE;

                CrossDefenseCommand cross4 = new CrossDefenseCommand();

                cross4.start();
                break;

            case StrongholdConstants.BLACK_BUTTON_ID:
                //Cross defense 0
                StrongholdRobot.defensePositionSelected = 0;
                StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(0);
                StrongholdRobot.shootOptionSelected = StrongholdConstants.shootOptions.NONE;

                CrossDefenseCommand cross0 = new CrossDefenseCommand();

                cross0.start();
                break;
        }
    }
}
