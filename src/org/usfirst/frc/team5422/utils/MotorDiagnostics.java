package org.usfirst.frc.team5422.utils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.DSIO.SmartDashboardChooser;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.shooter.ShooterHelper;
import org.usfirst.frc.team5422.utils.StrongholdConstants.motorTests;
import org.usfirst.frc.team5422.utils.StrongholdConstants.teleopModes;

/**
 * @author Michael
 */
public class MotorDiagnostics {
    public static void testMotors() {
        teleopModes test = (teleopModes) DSIO.choosers.modeChooser.getSelected();
        if (test == teleopModes.TEST) {
            motorTests motorToTest;
            CANTalon motor;

            motorToTest = (motorTests) SmartDashboardChooser.testMotorChooser.getSelected();

            int motorId = -1;

            //Get motorToTest id from selected radio button
            switch (motorToTest) {
                case TEST_ACTUATOR:
                    double angle = (DSIO.getLinearX() + 1) / 2 * StrongholdConstants.ACTUATOR_RANGE_DEGREES - StrongholdConstants.ACTUATOR_ANGLE_MIN;
                    StrongholdRobot.shooterSubsystem.changeAngle(angle);

                    double ticks = StrongholdRobot.shooterSubsystem.actuator.getPosition();

                    SmartDashboard.putNumber("Pot Ticks: ", ticks);
                    SmartDashboard.putNumber("Commanded Ticks: ", ShooterHelper.getAngleToTicks(angle));
                    SmartDashboard.putNumber("Drift in Ticks: ", ShooterHelper.getAngleToTicks(angle) - ticks);
                    break;
                case TEST_LEFT_SHOOTER:
                    motorId = StrongholdConstants.TALON_LEFT_SHOOTER;

                    motor = new CANTalon(motorId);
                    motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
                    motor.changeControlMode(CANTalon.TalonControlMode.Speed);
                    motor.configNominalOutputVoltage(+0.0f, -0.0f);
                    motor.setPID(StrongholdConstants.SHOOTER_LEFT_P, StrongholdConstants.SHOOTER_LEFT_I, StrongholdConstants.SHOOTER_LEFT_D);
                    motor.setF(StrongholdConstants.SHOOTER_LEFT_F);
                    motor.setCloseLoopRampRate(0.95);

                    motor.set(DSIO.getLinearX() * 0.5);

                    SmartDashboard.putNumber("Left Shooter Motor Position: ", motor.getEncPosition());
                    SmartDashboard.putNumber("Left Shooter Motor Speed: ", motor.getSpeed());
                    break;
                case TEST_RIGHT_SHOOTER:
                    motorId = StrongholdConstants.TALON_RIGHT_SHOOTER;

                    motor = new CANTalon(motorId);
                    motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
                    motor.changeControlMode(CANTalon.TalonControlMode.Speed);
                    motor.configNominalOutputVoltage(+0.0f, -0.0f);
                    motor.setPID(StrongholdConstants.SHOOTER_LEFT_P, StrongholdConstants.SHOOTER_LEFT_I, StrongholdConstants.SHOOTER_LEFT_D);
                    motor.setF(StrongholdConstants.SHOOTER_LEFT_F);
                    motor.setCloseLoopRampRate(0.95);

                    motor.set(DSIO.getLinearX() * 0.5);

                    SmartDashboard.putNumber("Right Shooter Motor Position: ", motor.getEncPosition());
                    SmartDashboard.putNumber("Right Shooter Motor Speed: ", motor.getSpeed());
                    break;
                case TEST_DRIVE_LEFT_MASTER:
                    motorId = StrongholdConstants.TALON_DRIVE_LEFT_MASTER;

                    motor = new CANTalon(motorId);
                    motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
                    motor.configEncoderCodesPerRev(2048);
                    motor.configNominalOutputVoltage(+0.0f, -0.0f);
                    motor.setPID(StrongholdConstants.OPEN_DRIVE_P, StrongholdConstants.OPEN_DRIVE_I, StrongholdConstants.OPEN_DRIVE_D);
                    motor.setF(StrongholdConstants.OPEN_DRIVE_F);

                    motor.set(DSIO.getLinearY() * 81.92 * 0.5);

                    SmartDashboard.putNumber("Left Drive Master Position: ", motor.getEncPosition());
                    SmartDashboard.putNumber("Left Drive Master Speed: ", motor.getSpeed());
                    break;
                case TEST_DRIVE_RIGHT_MASTER:
                    motorId = StrongholdConstants.TALON_DRIVE_RIGHT_MASTER;

                    motor = new CANTalon(motorId);
                    motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
                    motor.configEncoderCodesPerRev(2048);
                    motor.configNominalOutputVoltage(+0.0f, -0.0f);
                    motor.setPID(StrongholdConstants.OPEN_DRIVE_P, StrongholdConstants.OPEN_DRIVE_I, StrongholdConstants.OPEN_DRIVE_D);
                    motor.setF(StrongholdConstants.OPEN_DRIVE_F);

                    motor.set(DSIO.getLinearY() * 81.92 * 0.5);

                    SmartDashboard.putNumber("Right Drive Master Position: ", motor.getEncPosition());
                    SmartDashboard.putNumber("Right Drive Master Speed: ", motor.getSpeed());
                    break;
                default:
                    motorId = -1;
                    break;
            }
        }
    }
}
