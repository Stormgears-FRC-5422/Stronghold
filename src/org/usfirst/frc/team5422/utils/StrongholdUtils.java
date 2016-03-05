package org.usfirst.frc.team5422.utils;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;

/**
 * @author Michael
 */
public class StrongholdUtils {
    public static double getDistance(double xSource, double ySource, double xDestination, double yDestination) {
        //Returns direct distance given source x/y and destination x/y
        double distance = Math.sqrt((xSource - xDestination) * (xSource - xDestination) + (ySource - yDestination) * (ySource - yDestination));
        return distance;
    }

//    public static defenseTypeOptions getDefenseFromPosition(int defensePosition) {
//        defenseTypeOptions defense = defenseTypeOptions.LOW_BAR;
//        int defenseID = DSIO.pos[defensePosition];
//
//        switch (defenseID) {
//            case 0:
//                defense = defenseTypeOptions.LOW_BAR;
//                break;
//            case 1:
//                defense = defenseTypeOptions.PORTCULLIS;
//                break;
//            case 2:
//                defense = defenseTypeOptions.CHIVAL_DE_FRISE;
//                break;
//            case 3:
//                defense = defenseTypeOptions.MOAT;
//                break;
//            case 4:
//                defense = defenseTypeOptions.RAMPARTS;
//                break;
//            case 5:
//                defense = defenseTypeOptions.DRAWBRIDGE;
//                break;
//            case 6:
//                defense = defenseTypeOptions.SALLYPORT;
//                break;
//            case 7:
//                defense = defenseTypeOptions.ROCK_WALL;
//                break;
//            case 8:
//                defense = defenseTypeOptions.ROUGH_TERRAIN;
//                break;
//        }
//        return defense;
//    }
}
