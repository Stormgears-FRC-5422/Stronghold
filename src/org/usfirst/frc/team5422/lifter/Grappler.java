package org.usfirst.frc.team5422.lifter;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Michael
 */
public class Grappler extends Subsystem {
    @Override
    protected void initDefaultCommand() {

    }

    public void alignToCastle() {
        //Code that aligns the robot to the castle goes here.
        System.out.println("Aligning the robot to castle...");
    }

    public void grappleToCastle() {
        //Code that grapples to castle goes here.
        System.out.println("Grappling robot to castle...");


    }
}
