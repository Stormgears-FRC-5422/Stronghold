/**
 * 
 */
package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/**
 * @author Surendran
 *
 */
public class DefenseType implements DefenseTypeInterface {

	/**
	 * 
	 */
	public DefenseType() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team5422.defense.DefenseInterface#align(org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions, int)
	 */
	@Override
	public void align(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Aligning the Robot to the defenseType " + defenseType + " defense at position " + defensePosition);

	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team5422.defense.DefenseInterface#reach(org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions, int)
	 */
	@Override
	public void reach(defenseTypeOptions defenseType, int defensePosition) {
		// TODO Auto-generated method stub
		System.out.println("Robot Reaching the defenseType " + defenseType + " defense at " + defensePosition);
		// TODO Auto-generated method stub

		switch (defensePosition) {
			case 1:
			//do nothing as it's low bar
				break;
			case 2:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2_REACH[0], StrongholdConstants.POSITION_DEFENSE_2_REACH[1]);
				break;
			case 3:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3_REACH[0], StrongholdConstants.POSITION_DEFENSE_3_REACH[1]);
				break;
			case 4:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4_REACH[0], StrongholdConstants.POSITION_DEFENSE_4_REACH[1]);
				break;
			case 5:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_5_REACH[0], StrongholdConstants.POSITION_DEFENSE_5_REACH[1]);
				break;
		}
		System.out.format("Robot Reached the defenseType " + defenseType + " defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());

	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team5422.defense.DefenseInterface#cross(org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions, int)
	 */
	@Override
	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the defenseType " + defenseType + " defense at position " + defensePosition);
		// TODO Auto-generated method stub

		switch (defensePosition) {
			case 1:
			//do nothing as it's low bar
				break;
			case 2:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2_REACH[0], StrongholdConstants.POSITION_DEFENSE_2_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 3:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3_REACH[0], StrongholdConstants.POSITION_DEFENSE_3_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 4:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4_REACH[0], StrongholdConstants.POSITION_DEFENSE_4_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 5:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_5_REACH[0], StrongholdConstants.POSITION_DEFENSE_5_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
		}
		System.out.format("Robot Crossed the defenseType " + defenseType + " defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());

	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team5422.defense.DefenseInterface#positionToShoot(org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions, int, org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions)
	 */
	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the defenseType " + defenseType + " defense at position " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

		//Change angle to match angle to best goals
		//StrongholdRobot.navigatorSubsystem.driveTo(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());
		StrongholdRobot.navigatorSubsystem.turnTo(ShooterHelper.findHorizontalAngleToGoal(StrongholdRobot.shootOptionSelected));	

		System.out.format("Robot Positioned to Shoot for the defenseType " + defenseType + " defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());

	}

	@Override
	public void reachNCross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot reaching AND crossing the defenseType " + defenseType + " defense at position " + defensePosition);
		// TODO Auto-generated method stub

		switch (defensePosition) {
			case 1:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_1_REACH[0], StrongholdConstants.POSITION_DEFENSE_1_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 2:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_2_REACH[0], StrongholdConstants.POSITION_DEFENSE_2_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 3:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_3_REACH[0], StrongholdConstants.POSITION_DEFENSE_3_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 4:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_4_REACH[0], StrongholdConstants.POSITION_DEFENSE_4_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
			case 5:
				StrongholdRobot.navigatorSubsystem.driveTo(StrongholdConstants.POSITION_DEFENSE_5_REACH[0], StrongholdConstants.POSITION_DEFENSE_5_REACH[1] + StrongholdConstants.CROSS_DEFENSE_LENGTH_Y);
				break;
		}
		System.out.format("Robot reached AND crossed the defenseType " + defenseType + " defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());

		
	}

}
