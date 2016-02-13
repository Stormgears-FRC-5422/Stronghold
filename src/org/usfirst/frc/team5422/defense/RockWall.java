package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/*
 * @author Michael
 */

public class RockWall implements Defense {

	public RockWall(defenseTypeOptions defenseType, int defensePosition) {
		
	}

	@Override
	public void align(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Aligning the Robot to the Rockwall defense at " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the Rockwall defense at " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Rockwall defense at " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

	}

}
