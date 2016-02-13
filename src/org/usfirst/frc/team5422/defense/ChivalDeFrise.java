package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

public class ChivalDeFrise implements Defense {
	public ChivalDeFrise(defenseTypeOptions defenseType, int defensePosition) {
		
	}

	@Override
	public void align(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Aligning the Robot to the Chival De Frise defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void reach(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot needs the source and destination coordinates as parameters to reach Chival De Frise defense at position " + defensePosition);
		System.out.println("Robot Reaching the Chival De Frise defense at " + defensePosition);

	}

	public void cross(defenseTypeOptions defenseType, int defensePosition) {
		System.out.println("Robot crossing the Chival De Frise defense at position " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Chival De Frise defense at position " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

	}

}
