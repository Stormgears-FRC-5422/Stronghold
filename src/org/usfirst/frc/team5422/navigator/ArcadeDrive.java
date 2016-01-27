package org.usfirst.frc.team5422.navigator;

public class ArcadeDrive {
	//Declare variables
	static double leftMotorSpeed;
    static double rightMotorSpeed;
    
    //This method calculates the left and right velocities based on x and y of joystick
	public static void arcadeDrive(double moveValue, double rotateValue) {
		moveValue = limit(moveValue);
		rotateValue = limit(rotateValue);

		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			}
			else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		}
		else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
			else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}

		System.out.println("Left = " + leftMotorSpeed +  "  Right = " + rightMotorSpeed);
	}
	
	public static double arcadeDriveLeft() {
		return leftMotorSpeed;
	}
	
	public static double arcadeDriveRight() {
		return rightMotorSpeed;
	}

	//Limit motor speeds if they are outside of -1 and 1
	protected static double limit(double num) {
		if (num > 1.0) {
			return 1.0;
		}
		if (num < -1.0) {
			return -1.0;
		}
		return num;
	}
}
