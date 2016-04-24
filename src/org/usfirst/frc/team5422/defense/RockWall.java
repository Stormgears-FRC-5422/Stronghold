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
		StrongholdRobot.navigatorSubsystem.setRPS(4.0);
		
	}

//	protected void navigatorAlignmentToShoot(int posX, int posY, double turnToAngle) {
//		int climbRockWallDistance = 25;
//		int defenseRampDistance = 12;
//		
//		StrongholdRobot.navigatorSubsystem.driveTo(posX, StrongholdConstants.POSITION_DEFENSE_2_REACH[1] + defenseRampDistance);
//		StrongholdRobot.navigatorSubsystem.setRPS(2.0);		
//		StrongholdRobot.navigatorSubsystem.driveTo(posX, climbRockWallDistance);
//		StrongholdRobot.navigatorSubsystem.setRPS(3.0);				
//		StrongholdRobot.navigatorSubsystem.driveTo(posX, posY - (StrongholdConstants.POSITION_DEFENSE_2_REACH[1] + defenseRampDistance + climbRockWallDistance));
//
//		StrongholdRobot.navigatorSubsystem.turnToRelative(Math.toRadians(turnToAngle));
//	}
//
	protected int calcExtraDistanceByDefenseType() {
		return 40;
	}

}
