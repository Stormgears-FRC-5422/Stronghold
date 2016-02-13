package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/*
 * @author Michael
 */

public class Moat implements Defense {

	public Moat(defenseTypeOptions defenseType, int defensePosition) {
		
	}
	
	@Override
	public void align(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Aligning the Robot to the Moat defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void reach(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot needs the source and destination coordinates as parameters to reach Moat defense at position " + defensePosition);
		System.out.println("Robot Reaching the Moat defense at " + defensePosition);
		// TODO Auto-generated method stub

		switch (defensePosition) {
			case 1:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_1[0], StrongholdConstants.POSITION_DEFENSE_1[1], Math.PI / 2);
				break;
			case 2:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2[0], StrongholdConstants.POSITION_DEFENSE_2[1], Math.PI / 2);
				break;
			case 3:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3[0], StrongholdConstants.POSITION_DEFENSE_3[1], Math.PI / 2);
				break;
			case 4:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4[0], StrongholdConstants.POSITION_DEFENSE_4[1], Math.PI / 2);
				break;
		}
	}

	@Override
	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the Moat defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Moat defense at position " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

	}

}
