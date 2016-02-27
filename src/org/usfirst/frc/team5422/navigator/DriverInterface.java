package org.usfirst.frc.team5422.navigator;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Created by michael on 2/20/16.
 */
public interface DriverInterface {
    public void openDrive(double yJoystick, double xJoystick, CANTalon.TalonControlMode controlMode);
    public void moveTrapezoid(int leftTicks, int rightTicks, double leftVelocity, double rightVelocity, int tableID);
    public void stopTrapezoid();
    public CANTalon getDriveTalonLeftMaster();
    public CANTalon getDriveTalonRightMaster();
}
