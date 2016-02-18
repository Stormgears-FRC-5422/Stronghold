package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.navigator.trapezoidal.MotionProfileExample;
import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidControl;
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

    static MotionProfileExample leftProfile;
    static MotionProfileExample rightProfile;

    //Constructor
    public Driver() {
        //Declare talons
        talon[0] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_MASTER);
        talon[1] = new CANTalon(TALON_DRIVE_RIGHT_MASTER);
        talon[0].reverseOutput(true);

        for (int i = 0; i < 2; i++) {
            talon[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
            talon[i].configEncoderCodesPerRev(2048);
            talon[i].configNominalOutputVoltage(+0.0f, -0.0f);
            talon[i].setPID(StrongholdConstants.OPEN_DRIVE_P, StrongholdConstants.OPEN_DRIVE_I, StrongholdConstants.OPEN_DRIVE_D);
            talon[i].setF(StrongholdConstants.OPEN_DRIVE_F);
        }

        leftProfile = new MotionProfileExample(talon[0]);
        rightProfile = new MotionProfileExample(talon[1]);
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
                talon[i].setPID(StrongholdConstants.OPEN_DRIVE_P, StrongholdConstants.OPEN_DRIVE_I, StrongholdConstants.OPEN_DRIVE_D);
                talon[i].setF(StrongholdConstants.OPEN_DRIVE_F);
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

    public static void initializeTrapezoid() {
        talon[0] = new CANTalon(0);
        talon[1] = new CANTalon(3);

        leftProfile = new MotionProfileExample(talon[0]);
        rightProfile = new MotionProfileExample(talon[1]);

        talon[0].setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        talon[1].setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

        talon[0].setPID(1, 0, 0);
        talon[1].setPID(1, 0, 0);
        talon[0].setF(0);
        talon[1].setF(0);

        talon[0].changeControlMode(TalonControlMode.MotionProfile);
        talon[1].changeControlMode(TalonControlMode.MotionProfile);
    }

    public static void moveTrapezoid(int leftTicks, int rightTicks, double leftVelocity, double rightVelocity) {


        int leftStartingTicks = talon[0].getEncPosition();
        int rightStartingTicks = talon[1].getEncPosition();

        double leftRotations = leftTicks / 8192.0;
        double leftStartingRotations = leftStartingTicks / 8192.0;
        double rightRotations = rightTicks / 8192.0;
        double rightStartingRotations = rightStartingTicks / 8192.0;

        //config the talons as appropriate


        if (leftTicks > 0) {
            talon[0].reverseOutput(true); //may need to be changed
            talon[0].reverseSensor(false); //may need to be changed

        } else {
            talon[0].reverseOutput(false); //may need to be changed
            talon[0].reverseSensor(true); //may need to be changed
        }


        if (rightTicks > 0) {
            talon[1].reverseOutput(false); //may need to be changed
            talon[1].reverseSensor(true); //may need to be changed
        } else {
            talon[1].reverseOutput(true); //may need to be changed
            talon[1].reverseSensor(false); //may need to be changed
        }

        //create both of the motion profiles(done in teleopPeriodic)
        leftProfile.setProfile(TrapezoidControl.motionProfileUtility(leftRotations, leftVelocity, leftStartingRotations));


        rightProfile.setProfile(TrapezoidControl.motionProfileUtility(rightRotations, rightVelocity, rightStartingRotations));


        //set up all the "formalities" so that the motion profiles can actually be run(Teleop Periodic)
        leftProfile.control();
        rightProfile.control();


        CANTalon.SetValueMotionProfile setOutputLeft = leftProfile.getSetValue();
        CANTalon.SetValueMotionProfile setOutputRight = rightProfile.getSetValue();

        talon[0].set(setOutputLeft.value);
        talon[1].set(setOutputRight.value);


        //start the motion profile(Teleop periodic)
        leftProfile.startMotionProfile();
        rightProfile.startMotionProfile();
    }

    public static void resetTrapezoid() {
        talon[0].changeControlMode(TalonControlMode.PercentVbus);
        talon[0].set(0);
        talon[0].setEncPosition(0); //will need to be commented out

        talon[1].changeControlMode(TalonControlMode.PercentVbus);
        talon[1].set(0);
        talon[1].setEncPosition(0);
    }
}