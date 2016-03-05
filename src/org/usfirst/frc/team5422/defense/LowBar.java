package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;


/*
 * @author Suren Karavettil
 * @author Michael
 */

public class LowBar implements Defense {

	public LowBar(defenseTypeOptions defenseType, int defensePosition) {
		
	}
	
	@Override
	public void align(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Aligning the Robot to the Low Bar defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}
	
	@Override
	public void reach(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot needs the source and destination coordinates as parameters to reach LowBar defense..." + defensePosition);
		System.out.println("Robot Reaching the Low Bar defense at position " + defensePosition);
		// TODO Auto-generated method stub

		StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_1_REACH[0], StrongholdConstants.POSITION_DEFENSE_1_REACH[1]);

	}
	

	@Override
	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the Low Bar defense at position " + defensePosition);
		// TODO Auto-generated method stub

		switch (defensePosition) {
			case 1:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_1_REACH[0], StrongholdConstants.POSITION_DEFENSE_1_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 2:
//				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2_REACH[0], StrongholdConstants.POSITION_DEFENSE_2_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 3:
//				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3_REACH[0], StrongholdConstants.POSITION_DEFENSE_3_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 4:
//				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4_REACH[0], StrongholdConstants.POSITION_DEFENSE_4_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 5:
			//do nothing as it's low bar
				break;
		}

	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Low Bar defense at position " + defensePosition + " shooting into " + shootOption);

		//Change angle to match angle to best goals
		//StrongholdRobot.navigatorSubsystem.driveTo(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());
		StrongholdRobot.navigatorSubsystem.turnTo(ShooterHelper.findHorizontalAngleToGoal(StrongholdRobot.shootOptionSelected));	
	}

}
