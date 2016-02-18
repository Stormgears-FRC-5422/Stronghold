package org.usfirst.frc.team5422.utils;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootHeightOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.defenseTypeOptions;

import java.util.Arrays;

/**
 * @author Michael
 */
public class StrongholdUtils {

    private static boolean inBounds;
    /**
     * This function determines the best goal to shoot into, with the input of whether the goal should be high or low
     */
    public static shootOptions findBestGoal(shootHeightOptions highOrLow) {
        shootOptions bestGoal = shootOptions.HIGH_CENTER;
        double x = GlobalMapping.getX(), y = GlobalMapping.getY();


        //Bounds for high left goal and low left goal
        if (y <= 70.0 / 120.0 * x + 234.16666666666666 &&
                x >= 0 &&
                y >= 40.0 / -90.0 * x + 202.22222222222223 &&
                y >= -90.0 / -70.0 * x + 98.57142857142856) {
            if (highOrLow == shootHeightOptions.HIGH) {
                bestGoal = shootOptions.HIGH_RIGHT;
            }
            else bestGoal = shootOptions.LOW_LEFT;
        }
        //Bounds for high center goal
        else if (y <= -100.0 / -40.0 * x + -105.0 &&
                y >= 40.0 / -90.0 * x + 202.22222222222223 &&
                y >= 140 &&
                y >= -40.0 / -70.0 * x + 25.714285714285722 &&
                y <= -160.0 / 60.0 * x + 793.3333333333333) {
            if (highOrLow == shootHeightOptions.HIGH) {
                bestGoal = shootOptions.HIGH_RIGHT;
            }
            else bestGoal = shootOptions.LOW_LEFT;
        }
        //Bounds for high right goal and low right
        else if (y >= -130.0 / 100.0 * x + 543.0 &&
                x <= 270 &&
                y <= -20.0 / 40.0 * x + 415.0) {
            if (highOrLow == shootHeightOptions.HIGH) {
                bestGoal = shootOptions.HIGH_RIGHT;
            }
            else bestGoal = shootOptions.LOW_LEFT;
        }
        else inBounds = false;
        return bestGoal;
    }

    public static boolean isInBounds() {
        findBestGoal(shootHeightOptions.LOW);
        return inBounds;
    }

    /**
     * This function finds the horizontal angle to the goal based on the robot's current position
     */
    public static double findHorizontalAngleToGoal(shootOptions shootOption) {
        double currentX = GlobalMapping.getX();
        double currentY = GlobalMapping.getY();

        //3 High goals
        double deltaXHL = StrongholdConstants.POSITION_HLEFT_GOAL[0] - currentX;
        double deltaYHL = StrongholdConstants.POSITION_HLEFT_GOAL[1] - currentY;
        double deltaXHC = StrongholdConstants.POSITION_HCENTER_GOAL[0] - currentX;
        double deltaYHC = StrongholdConstants.POSITION_HCENTER_GOAL[1] - currentY;
        double deltaXHR = StrongholdConstants.POSITION_HRIGHT_GOAL[0] - currentX;
        double deltaYHR = StrongholdConstants.POSITION_HRIGHT_GOAL[1] - currentY;

        //2 Low goals
        double deltaXLL = StrongholdConstants.POSITION_LLEFT_GOAL[0] - currentX;
        double deltaYLL = StrongholdConstants.POSITION_LLEFT_GOAL[1] - currentY;
        double deltaXLR = StrongholdConstants.POSITION_LRIGHT_GOAL[0] - currentX;
        double deltaYLR = StrongholdConstants.POSITION_LRIGHT_GOAL[1] - currentY;

        double angleToGoal = -1;

        switch (shootOption) {
            case HIGH_LEFT:
                angleToGoal = 90 - Math.toDegrees(Math.atan2(deltaYHL, deltaXHL));
                break;
            case HIGH_RIGHT:
                angleToGoal = 90 - Math.toDegrees(Math.atan2(deltaYHR, deltaXHR));
                break;
            case HIGH_CENTER:
                angleToGoal = 90 - Math.toDegrees(Math.atan2(deltaYHC, deltaXHC));
                break;
            case LOW_LEFT:
                angleToGoal = 90 - Math.toDegrees(Math.atan2(deltaYLL, deltaXLL));
                break;
            case LOW_RIGHT:
                angleToGoal = 90 - Math.toDegrees(Math.atan2(deltaYLR, deltaXLR));
                break;
            case NONE:
                angleToGoal = -1;
                break;
        }
        return Math.toRadians(angleToGoal);
    }

    public static double findClosestFallbackPointX() {
        double x = 0;
        if (!isInBounds()) {
            double distToLeft, distToCenter;
            distToLeft = getDistance(GlobalMapping.getX(), GlobalMapping.getY(), StrongholdConstants.FALLBACK_LEFT[0], StrongholdConstants.FALLBACK_LEFT[1]);
            distToCenter = getDistance(GlobalMapping.getX(), GlobalMapping.getY(), StrongholdConstants.FALLBACK_CENTER[0], StrongholdConstants.FALLBACK_CENTER[1]);
            if (distToLeft < distToCenter) x = StrongholdConstants.FALLBACK_LEFT[0];
            else x = StrongholdConstants.FALLBACK_CENTER[0];
        }
        else {
            x = GlobalMapping.getX();
        }

        return x;
    }

    public static double findClosestFallbackPointY() {
        double y = 0;
        if (!isInBounds()) {
            double distToLeft, distToCenter;
            distToLeft = getDistance(GlobalMapping.getX(), GlobalMapping.getY(), StrongholdConstants.FALLBACK_LEFT[0], StrongholdConstants.FALLBACK_LEFT[1]);
            distToCenter = getDistance(GlobalMapping.getX(), GlobalMapping.getY(), StrongholdConstants.FALLBACK_CENTER[0], StrongholdConstants.FALLBACK_CENTER[1]);
            if (distToLeft < distToCenter) y = StrongholdConstants.FALLBACK_LEFT[1];
            else y = StrongholdConstants.FALLBACK_CENTER[1];
        }
        else {
            y = GlobalMapping.getX();
        }

        return y;
    }

    public static double getDistance(double xSource, double ySource, double xDestination, double yDestination) {
        //Returns direct distance given source x/y and destination x/y
        double distance = Math.sqrt((xSource - xDestination) * (xSource - xDestination) + (ySource - yDestination) * (ySource - yDestination));
        return distance;
    }

    public static defenseTypeOptions getDefenseFromPosition(int defensePosition) {
        defenseTypeOptions defense = defenseTypeOptions.LOW_BAR;
        int defenseID = DSIO.pos[defensePosition];

        switch (defenseID) {
            case 0:
                defense = defenseTypeOptions.LOW_BAR;
                break;
            case 1:
                defense = defenseTypeOptions.PORTCULLIS;
                break;
            case 2:
                defense = defenseTypeOptions.CHIVAL_DE_FRISE;
                break;
            case 3:
                defense = defenseTypeOptions.MOAT;
                break;
            case 4:
                defense = defenseTypeOptions.RAMPARTS;
                break;
            case 5:
                defense = defenseTypeOptions.DRAWBRIDGE;
                break;
            case 6:
                defense = defenseTypeOptions.SALLYPORT;
                break;
            case 7:
                defense = defenseTypeOptions.ROCK_WALL;
                break;
            case 8:
                defense = defenseTypeOptions.ROUGH_TERRAIN;
                break;
        }
        return defense;
    }
}
