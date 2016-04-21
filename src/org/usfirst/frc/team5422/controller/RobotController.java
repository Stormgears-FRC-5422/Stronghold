package org.usfirst.frc.team5422.controller;

/*
 * @author suren
 */

import org.usfirst.frc.team5422.commands.buttonCommands.*;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

public class RobotController {
	
    public static void doActionsOnButtonPress(int buttonID) {
//        if (buttonID == StrongholdConstants.BIG_BLUE_BUTTON_ID) {
//            BigBlueCommand bigBlue = new BigBlueCommand();
//            bigBlue.start();
//        }
    	if ((StrongholdRobot.blueButtonPressed) && (!StrongholdRobot.shotTaken)){
    		StrongholdRobot.shotTaken = true;
    		BigBlueCommand bigBlue = new BigBlueCommand();
    		bigBlue.start();
    		
    	} else if (buttonID == StrongholdConstants.ORANGE_SWITCH_ID) {
            OrangeSwitchCommand orangeSwitch = new OrangeSwitchCommand();
            orangeSwitch.start();
        }
        else if (buttonID == StrongholdConstants.WHITE_BUTTON_ID) {
            //Nothing yet
        }
        else if (buttonID == StrongholdConstants.RED_BUTTON_ID) {
            //Nothing yet
        }
        else if (buttonID == StrongholdConstants.YELLOW_BUTTON_ID) {
            //Nothing yet
        }
        else if (buttonID == StrongholdConstants.GREEN_BUTTON_ID) {
            //Nothing yet

        	//Cross defense
            //int position = DSIO.getSelectedDefensePosition();

            //StrongholdRobot.defensePositionSelected = position;
            //StrongholdRobot.defenseTypeSelected = StrongholdUtils.getDefenseFromPosition(StrongholdRobot.defensePositionSelected);

            //CrossDefenseCommand cross = new CrossDefenseCommand();

            //cross.start();
        }
        else if (buttonID == StrongholdConstants.BLUE_BUTTON_ID) {
            //Lock onto goal
            SmallBlueCommand blue = new SmallBlueCommand();

            blue.start();
        }
        else if (buttonID == StrongholdConstants.BLACK_BUTTON_ID) {
            //Nothing yet
        }
    }

    public static void doActionsOnSliderPositions() {
        AngleSliderCommand angle = new AngleSliderCommand();

        angle.start();
    }
}
