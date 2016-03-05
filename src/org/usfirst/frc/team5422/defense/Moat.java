package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/*
 * @author Suren Karavettil
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
		//System.out.println("Robot needs the source and destination coordinates as parameters to reach Moat defense at position " + defensePosition);
		System.out.println("Robot Reaching the Moat defense at " + defensePosition);
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
		System.out.format("Robot Reached the Moat defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());
	}

	@Override
	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the Moat defense at position " + defensePosition);
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
		System.out.format("Robot Crossed the Moat defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());
	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Moat defense at position " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

		//Change angle to match angle to best goals
		//StrongholdRobot.navigatorSubsystem.driveTo(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());
		StrongholdRobot.navigatorSubsystem.turnTo(ShooterHelper.findHorizontalAngleToGoal(StrongholdRobot.shootOptionSelected));	

		System.out.format("Robot Positioned to Shoot for the Moat defense at " + defensePosition + " and GP (%.3g,%.3g): \n",GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY());
	}

}
