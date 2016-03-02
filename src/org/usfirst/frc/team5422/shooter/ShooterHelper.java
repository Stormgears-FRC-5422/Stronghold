package org.usfirst.frc.team5422.shooter;

import org.usfirst.frc.team5422.navigator.GlobalMapping;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdUtils;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;

/**
 * @author Michael
 */
public class ShooterHelper extends StrongholdUtils {
    private static boolean inBounds = false;
    /**
     * This function determines the best goal to shoot into, with the input of whether the goal should be high or low
     */
	
	public static double getDistanceToGoal(StrongholdConstants.shootOptions option) {
		shootOptions bestGoal = option;
		double distanceFromGoal;
		
		if (bestGoal == shootOptions.HIGH_CENTER) {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_HCENTER_GOAL[0], StrongholdConstants.POSITION_HCENTER_GOAL[1]);
		}
		else if (bestGoal == shootOptions.HIGH_LEFT) {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_HLEFT_GOAL[0], StrongholdConstants.POSITION_HLEFT_GOAL[1]);
		}
		else if (bestGoal == shootOptions.HIGH_RIGHT){
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_HRIGHT_GOAL[0], StrongholdConstants.POSITION_HRIGHT_GOAL[1]);
		}
		else if (bestGoal == shootOptions.LOW_LEFT) {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_LLEFT_GOAL[0], StrongholdConstants.POSITION_LLEFT_GOAL[1]);
		}
		else {
			distanceFromGoal = ShooterHelper.getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), 
					StrongholdConstants.POSITION_LRIGHT_GOAL[0], StrongholdConstants.POSITION_LRIGHT_GOAL[1]);
		}
		
		return distanceFromGoal;
	}
    
    public static StrongholdConstants.shootOptions findBestGoal(StrongholdConstants.shootHeightOptions highOrLow) {
        StrongholdConstants.shootOptions bestGoal = StrongholdConstants.shootOptions.HIGH_CENTER;
        double x = GlobalMapping.getInstance().getX(), y = GlobalMapping.getInstance().getY();


        //Bounds for high left goal and low left goal
        if (y <= 70.0 / 120.0 * x + 234.16666666666666 &&
                x >= 0 &&
                y >= 40.0 / -90.0 * x + 202.22222222222223 &&
                y >= -90.0 / -70.0 * x + 98.57142857142856) {
            inBounds = true;
            if (highOrLow == StrongholdConstants.shootHeightOptions.HIGH) {
                bestGoal = StrongholdConstants.shootOptions.HIGH_LEFT;
            }
            else bestGoal = StrongholdConstants.shootOptions.LOW_LEFT;
        }
        //Bounds for high center goal
        else if (y <= -100.0 / -40.0 * x + -105.0 &&
                y >= 40.0 / -90.0 * x + 202.22222222222223 &&
                y >= 140 &&
                y >= -40.0 / -70.0 * x + 25.714285714285722 &&
                y <= -160.0 / 60.0 * x + 793.3333333333333) {
            inBounds = true;
            if (highOrLow == StrongholdConstants.shootHeightOptions.HIGH) {
                bestGoal = StrongholdConstants.shootOptions.HIGH_CENTER;
            }
            else bestGoal = StrongholdConstants.shootOptions.NONE;
        }
        //Bounds for high right goal and low right
        else if (y >= -130.0 / 100.0 * x + 543.0 &&
                x <= 270 &&
                y <= -20.0 / 40.0 * x + 415.0) {
            inBounds = true;
            if (highOrLow == StrongholdConstants.shootHeightOptions.HIGH) {
                bestGoal = StrongholdConstants.shootOptions.HIGH_RIGHT;
            }
            else bestGoal = StrongholdConstants.shootOptions.LOW_RIGHT;
        }
        else inBounds = false;
        return bestGoal;
    }

    public static boolean isInBounds() {
        findBestGoal(StrongholdConstants.shootHeightOptions.LOW);
        return inBounds;
    }

    /**
     * This function finds the horizontal angle to the goal based on the robot's current position
     */
    public static double findHorizontalAngleToGoal(StrongholdConstants.shootOptions shootOption) {
        double currentX = GlobalMapping.getInstance().getX();
        double currentY = GlobalMapping.getInstance().getY();

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
                angleToGoal = Math.atan2(deltaYHL, deltaXHL);
                break;
            case HIGH_RIGHT:
                angleToGoal = Math.atan2(deltaYHR, deltaXHR);
                break;
            case HIGH_CENTER:
                angleToGoal = Math.atan2(deltaYHC, deltaXHC);
                break;
            case LOW_LEFT:
                angleToGoal = Math.atan2(deltaYLL, deltaXLL);
                break;
            case LOW_RIGHT:
                angleToGoal = Math.atan2(deltaYLR, deltaXLR);
                break;
            case NONE:
                angleToGoal = GlobalMapping.getInstance().getTheta();
                break;
        }
        return angleToGoal;
    }

    /**
     *These functions return the x and y of the closest fallback point respectively
     */
    public static double findClosestFallbackPointX() {
        double x = 0;
        if (!isInBounds()) {
            double distToLeft, distToCenter;
            distToLeft = getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), StrongholdConstants.FALLBACK_LEFT[0], StrongholdConstants.FALLBACK_LEFT[1]);
            distToCenter = getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), StrongholdConstants.FALLBACK_CENTER[0], StrongholdConstants.FALLBACK_CENTER[1]);
            if (distToLeft < distToCenter) x = StrongholdConstants.FALLBACK_LEFT[0];
            else x = StrongholdConstants.FALLBACK_CENTER[0];
        }
        else {
            x = GlobalMapping.getInstance().getX();
        }

        return x;
    }

    public static double findClosestFallbackPointY() {
        double y = 0;
        if (!isInBounds()) {
            double distToLeft, distToCenter;
            distToLeft = getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), StrongholdConstants.FALLBACK_LEFT[0], StrongholdConstants.FALLBACK_LEFT[1]);
            distToCenter = getDistance(GlobalMapping.getInstance().getX(), GlobalMapping.getInstance().getY(), StrongholdConstants.FALLBACK_CENTER[0], StrongholdConstants.FALLBACK_CENTER[1]);
            if (distToLeft < distToCenter) y = StrongholdConstants.FALLBACK_LEFT[1];
            else y = StrongholdConstants.FALLBACK_CENTER[1];
        }
        else {
            y = GlobalMapping.getInstance().getX();
        }

        return y;
    }
    
	public static double calculateAngle(StrongholdConstants.shootOptions goal) {
		double angle;
		if (goal == StrongholdConstants.shootOptions.HIGH_CENTER ||
				goal == StrongholdConstants.shootOptions.HIGH_RIGHT ||
				goal == StrongholdConstants.shootOptions.HIGH_LEFT) angle = Math.atan((StrongholdConstants.HEIGHT_TO_HIGH_GOAL / ShooterHelper.getDistanceToGoal(goal)));
		else angle = Math.atan(StrongholdConstants.HEIGHT_TO_LOW_GOAL/ShooterHelper.getDistanceToGoal(goal));
		return angle;
	}
	
	public static double getSpeedMultiplier(double sliderVal) {
		double multiplier;
		multiplier = (sliderVal + 1) / 2;
		return multiplier;
	}
	
//	public static double getAngleToTicks(double angle) {
//		double angleToTicks;
//		double anglePerTick;
//		double ticksAtZero;
//	
//		anglePerTick = (StrongholdConstants.ACTUATOR_ARM_DOWN_POT_FULLRANGE - StrongholdConstants.ACTUATOR_ARM_UP_POT_FULLRANGE) / (angleMax - angleMin);
//		
//		ticksAtZero = potMin - angleMin * anglePerTick;
//		
//		double angleToTicks = ticksAtZero - anglePerTick * angle;
//	}
}