package org.usfirst.frc.team5422.utils;

import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootHeightOptions;

import java.util.Arrays;

/**
 * @author Michael
 */
public class StrongholdUtils {

    /**
     *This function determines the best goal to shoot into, with the input of whether the goal should be high or low
     */
    public static shootOptions findBestGoal(shootHeightOptions highOrLow) {
        double currentX = GlobalMapping.getX();
        double angleToHL = 0, angleToHC = 0, angleToHR = 0, angleToLL = 0, angleToLR = 0;



        //Default to high center, since that's most common goal
        shootOptions bestShootOption = shootOptions.HIGH_CENTER;

        if (currentX < 171.5) {
            //The robot is on the left side of the field

            angleToHL = findAngleToGoal(shootOptions.HIGH_LEFT);
            //There is no high right

            angleToLL = findAngleToGoal(shootOptions.LOW_LEFT);
            //There is no low right


        }
        else if (currentX > 171.5) {
            //The robot is on the right side of the field

            angleToHR = findAngleToGoal(shootOptions.HIGH_RIGHT);
            //There is no high left

            angleToLR = findAngleToGoal(shootOptions.LOW_RIGHT);
            //There is no low left
        }
        else {
            //To center goal
            angleToHC = findAngleToGoal(shootOptions.HIGH_CENTER);
        }

        double[] highAngles = {angleToHL, angleToHC, angleToHR};
        double[] lowAngles = {angleToLL, angleToLR};

        Arrays.sort(highAngles);
        Arrays.sort(lowAngles);

        switch (highOrLow) {
            case HIGH:
                if (highAngles[0] == angleToHL) bestShootOption = shootOptions.HIGH_LEFT;
                if (highAngles[0] == angleToHC) bestShootOption = shootOptions.HIGH_CENTER;
                if (highAngles[0] == angleToHR) bestShootOption = shootOptions.HIGH_RIGHT;
                break;
            case LOW:
                if (lowAngles[0] == angleToLL) bestShootOption = shootOptions.LOW_LEFT;
                if (lowAngles[0] == angleToLR) bestShootOption = shootOptions.LOW_RIGHT;
                break;
            default:
                break;
        }

        return bestShootOption;
    }

    public static double findAngleToGoal(shootOptions shootOption) {
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
        return angleToGoal;
    }

    public static double getDistance(double xSource, double ySource, double xDestination, double yDestination) {
        //Returns direct distance given source x/y and destination x/y
        double distance = Math.sqrt((xSource - xDestination) * (xSource - xDestination) + (ySource - yDestination) * (ySource - yDestination));
        return distance;
    }
}
