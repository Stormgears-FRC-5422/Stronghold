package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;

/*
 * @author Suren Karavettil
 * @author Michael
 */

public class RockWall extends DefenseType {

	public RockWall(defenseTypeOptions defenseType, int defensePosition) {
		StrongholdRobot.navigatorSubsystem.setRPS(5.0);
		
	}

//	protected void navigatorAlignmentToShoot(int posX, int posY, double turnToAngle) {
//		StrongholdRobot.navigatorSubsystem.driveTo(posX, posY);
//
//		StrongholdRobot.navigatorSubsystem.turnToRelative(Math.toRadians(turnToAngle));
//	}

	protected int calcExtraDistanceByDefenseType() {
		return 90;
	}

}
