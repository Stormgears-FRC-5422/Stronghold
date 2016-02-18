package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.navigator.trapezoidal.MotionProfileExample;
import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidControl;
import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidThread;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

import static org.usfirst.frc.team5422.utils.StrongholdConstants.TALON_DRIVE_RIGHT_MASTER;

/*
 * @author Michael
 */

public class Driver {
    public static CANTalon talon[] = new CANTalon[2];

 
    static double F, P, I, D;
    private TrapezoidThread trapThread;
    
    //Constructor
    public Driver() {
        //Set PID closed loop gains


        //Change the PID values here. Keep F as it is.
        F = 1.705;
        P = 0.000185;
        I = 0;
        D = 0;

        //Declare talons
        talon[0] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_MASTER);
        talon[1] = new CANTalon(TALON_DRIVE_RIGHT_MASTER);
        talon[0].reverseOutput(true);

        /**create the trap thread**/
        
        trapThread = new TrapezoidThread(talon[0], talon[1]);
        
        for (int i = 0; i < 2; i++) {
            talon[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
            talon[i].configEncoderCodesPerRev(2048);
            talon[i].configNominalOutputVoltage(+0.0f, -0.0f);
            talon[i].setPID(P, I, D);
            talon[i].setF(F);
        }

    }

    /**
     * This function drives the robot around the carpet. It is not precise.
     */
    public static void openDrive(double yJoystick, double xJoystick, CANTalon.TalonControlMode controlMode) {
        //Declare variables
        double velocityLeft = 0, velocityRight = 0;

        //Declare talons
        if (controlMode == CANTalon.TalonControlMode.Speed | controlMode == CANTalon.TalonControlMode.PercentVbus) {
            talon[0].reverseOutput(true);
            for (int i = 0; i < 2; i++) {
                talon[i].changeControlMode(controlMode);
                talon[i].setPID(P, I, D);
                talon[i].setF(F);
            }
        } else {
            System.out.println("Invalid Talon Control Mode, set the talon in Speed mode or PercentVbus mode");
        }

        //Calculate velocities
        ArcadeDrive.arcadeDrive(xJoystick, yJoystick);
        velocityLeft = ArcadeDrive.arcadeDriveLeft() * 0.5;
        velocityRight = ArcadeDrive.arcadeDriveRight() * 0.5;

        //Set the velocity of the talons
        if (controlMode == CANTalon.TalonControlMode.Speed | controlMode == CANTalon.TalonControlMode.PercentVbus) {
            if (controlMode == CANTalon.TalonControlMode.Speed) {
                talon[0].set(velocityLeft * 81.92);
            } else talon[0].set(velocityLeft);

            if (controlMode == CANTalon.TalonControlMode.Speed) {
                talon[1].set(velocityRight * 81.92);
            } else talon[1].set(velocityRight);
        } else {
            System.out.println("Invalid Talon Control Mode, set the talon in Speed mode or PercentVbus mode");
        }


        //Output to SmartDashboard for diagnostics

        DSIO.outputToSFX("velocityLeft", velocityLeft);
        DSIO.outputToSFX("velocityRight", velocityRight);

        //Current being put through the talons
        DSIO.outputToSFX("Talon ID 3 Current (Right)", talon[0].getOutputCurrent());
        DSIO.outputToSFX("Talon ID 0 Current (Right)", talon[1].getOutputCurrent());

        //Talon speeds
        DSIO.outputToSFX("Talon ID 3 Velocity (Right)", talon[0].getSpeed());
        DSIO.outputToSFX("Talon ID 0 Velocity (Right)", talon[1].getSpeed());
    }

   
    public void moveTrapezoid(int leftTicks, int rightTicks, double leftVelocity, double rightVelocity) {
		
		trapThread.activateTrap(leftTicks, rightTicks, leftVelocity, rightVelocity);
		
	}

   
}