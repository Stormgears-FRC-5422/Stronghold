package org.usfirst.frc.team5422.utils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.DSIO.SmartDashboardChooser;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.Driver;
import org.usfirst.frc.team5422.utils.StrongholdConstants.motorTests;
import org.usfirst.frc.team5422.utils.StrongholdConstants.teleopModes;

/**
 * @author Michael
 */
public class MotorDiagnostics {
    static double position, speed;
    public static void testMotors() {
        teleopModes test = (teleopModes) DSIO.choosers.modeChooser.getSelected();
        if (test == teleopModes.TEST) {
            motorTests motorToTest;

            StrongholdRobot.testModeInUse = true;

            motorToTest = (motorTests) SmartDashboardChooser.testMotorChooser.getSelected();

            //Get motorToTest id from selected radio button
            switch (motorToTest) {
                case TEST_ACTUATOR:
                    double angle = (DSIO.getLinearX() + 1) / 2 * StrongholdConstants.ACTUATOR_RANGE_DEGREES - StrongholdConstants.ACTUATOR_ANGLE_MIN;
                    StrongholdRobot.shooterSubsystem.changeAngle(angle);
                    break;
                case TEST_LEFT_SHOOTER:
                    StrongholdRobot.shooterSubsystem.talonL.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

                    StrongholdRobot.shooterSubsystem.talonL.set(DSIO.getY());

                    position = StrongholdRobot.shooterSubsystem.talonL.getEncPosition();
                    speed = StrongholdRobot.shooterSubsystem.talonR.getSpeed();

                    //SmartDashboard.putNumber("Left Shooter Motor Position: ", position);
                    //SmartDashboard.putNumber("Left Shooter Motor Speed: ", speed);
                    break;
                case TEST_RIGHT_SHOOTER:
                    StrongholdRobot.shooterSubsystem.talonR.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

                    StrongholdRobot.shooterSubsystem.talonR.set(DSIO.getY());

                    position = StrongholdRobot.shooterSubsystem.talonR.getEncPosition();
                    speed = StrongholdRobot.shooterSubsystem.talonR.getSpeed();

                    //SmartDashboard.putNumber("Right Shooter Motor Position: ", position);
                    //SmartDashboard.putNumber("Right Shooter Motor Speed: ", speed);
                    break;
                case TEST_DRIVE_LEFT_MASTER:
                    Driver.masterTalon[0].changeControlMode(CANTalon.TalonControlMode.PercentVbus);

                    Driver.masterTalon[0].set(DSIO.getY());

                    position = Driver.masterTalon[0].getEncPosition();
                    speed = Driver.masterTalon[0].getSpeed();

                    //SmartDashboard.putNumber("Left Drive Master Position: ", position);
                    //SmartDashboard.putNumber("Left Drive Master Speed: ", speed);
                    break;
                case TEST_DRIVE_RIGHT_MASTER:
                    Driver.masterTalon[1].changeControlMode(CANTalon.TalonControlMode.PercentVbus);

                    Driver.masterTalon[1].set(DSIO.getY());

                    position = Driver.masterTalon[1].getEncPosition();
                    speed = Driver.masterTalon[1].getSpeed();

                    //SmartDashboard.putNumber("Right Drive Master Position: ", position);
                    //SmartDashboard.putNumber("Right Drive Master Speed: ", speed);
                    break;
            }
        }
    }
}
