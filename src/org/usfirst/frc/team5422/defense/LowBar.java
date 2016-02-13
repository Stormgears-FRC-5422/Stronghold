package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;


/*
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

	}
	

	@Override
	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the Low Bar defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Low Bar defense at position " + defensePosition + " shooting into " + shootOption);

	}

}
