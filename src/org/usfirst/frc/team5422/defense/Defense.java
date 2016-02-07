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
	public void align(defenseTypeOptions defenseType);
	public void cross(defenseTypeOptions defenseType);
	public void positionToShoot(defenseTypeOptions defenseType, shootOptions shootOption);

}
