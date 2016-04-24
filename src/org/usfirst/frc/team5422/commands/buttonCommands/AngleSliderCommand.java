package org.usfirst.frc.team5422.commands.buttonCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.DSIO.DSIO;
import org.usfirst.frc.team5422.controller.StrongholdRobot;

/**
 * @author Michael
 */
public class AngleSliderCommand extends Command {

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (DSIO.assistShoot == false) {
//            System.out.println("Adjusting angle of shooter based on slider");

            double actuatorArmSliderValue = DSIO.getActuatorSliderValue();
            StrongholdRobot.shooterSubsystem.changeAngle(actuatorArmSliderValue);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
