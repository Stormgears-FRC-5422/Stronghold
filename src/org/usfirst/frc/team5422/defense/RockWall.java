package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;

/*
 * @author Suren Karavettil
 * @author Michael
 */

public class RockWall extends DefenseType {

	public RockWall(defenseTypeOptions defenseType, int defensePosition) {
		StrongholdRobot.navigatorSubsystem.setRPS(3.5);
		
	}
}
