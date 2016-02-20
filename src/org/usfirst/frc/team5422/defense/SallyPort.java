package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdUtils;
/*
 * @author Suren Karavettil
 * @author Michael
 */

public class SallyPort implements Defense {
	public SallyPort(defenseTypeOptions defenseType, int defensePosition) {
		
	}

	@Override
	public void align(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Aligning the Robot to the SallyPort defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void reach(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot needs the source and destination coordinates as parameters to reach SallyPort defense at position " + defensePosition);
		System.out.println("Robot Reaching the SallyPort defense at " + defensePosition);

		switch (defensePosition) {
			case 1:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_1_REACH[0], StrongholdConstants.POSITION_DEFENSE_1_REACH[1], Math.PI / 2);
				break;
			case 2:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2_REACH[0], StrongholdConstants.POSITION_DEFENSE_2_REACH[1], Math.PI / 2);
				break;
			case 3:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3_REACH[0], StrongholdConstants.POSITION_DEFENSE_3_REACH[1], Math.PI / 2);
				break;
			case 4:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4_REACH[0], StrongholdConstants.POSITION_DEFENSE_4_REACH[1], Math.PI / 2);
				break;
		}

	}

	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the SallyPort defense at position " + defensePosition);
		// TODO Auto-generated method stub

		switch (defensePosition) {
			case 1:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_1_REACH[0], StrongholdConstants.POSITION_DEFENSE_1_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y, Math.PI / 2);
				break;
			case 2:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2_REACH[0], StrongholdConstants.POSITION_DEFENSE_2_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y, Math.PI / 2);
				break;
			case 3:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3_REACH[0], StrongholdConstants.POSITION_DEFENSE_3_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y, Math.PI / 2);
				break;
			case 4:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4_REACH[0], StrongholdConstants.POSITION_DEFENSE_4_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y, Math.PI / 2);
				break;
		}
	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the SallyPort defense at position " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

		//Change angle to match angle to best goals
		StrongholdRobot.navigatorSubsystem.driveTo(GlobalMapping.getX(), GlobalMapping.getY(), ShooterHelper.findHorizontalAngleToGoal(StrongholdRobot.shootOptionSelected));	}

}
