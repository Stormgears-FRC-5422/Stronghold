/**
 * 
 */
package org.usfirst.frc.team5422.defense;

import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/**
 * @author Surendran
 *
 */
public interface Defense {
	void align(defenseTypeOptions defenseType, int defensePosition);
	void reach(defenseTypeOptions defenseType, int defensePosition);	
	void cross(defenseTypeOptions defenseType, int defensePosition);
	void positionToShoot(defenseTypeOptions defenseType, int defensePosition, shootOptions shootOption);

}
