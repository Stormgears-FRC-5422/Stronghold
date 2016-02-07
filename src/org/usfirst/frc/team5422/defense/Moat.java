package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.utils.StrongholdConstants.defensePositionOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

public class Moat implements Defense {

	public Moat(defenseTypeOptions defenseType, defensePositionOptions defensePosition) {
		
	}
	
	@Override
	public void align(defenseTypeOptions defenseType, defensePositionOptions defensePosition) {
		System.out.println("Aligning the Robot to the Moat defense at " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void cross(defenseTypeOptions defenseType, defensePositionOptions defensePosition) {
		System.out.println("Robot crossing the Moat defense at " + defensePosition);
		// TODO Auto-generated method stub

	}

	@Override
	public void positionToShoot(defenseTypeOptions defenseType, defensePositionOptions defensePosition, shootOptions shootOption) {
		System.out.println("Robot positioning to shoot after crossing the Moat defense at " + defensePosition + " shooting into " + shootOption);
		// TODO Auto-generated method stub

	}

}
