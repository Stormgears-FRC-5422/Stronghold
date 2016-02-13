//VINAY CHANGES
//Entire Command Class

package org.usfirst.frc.team5422.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants.shootOptions;


public class AimShooterArmCommand extends Command {

	private shootOptions shootOptionSelected;
	
	public AimShooterArmCommand() {
		super();
		requires(StrongholdRobot.shooterSubsystem);		
	}
	
	@Override
	protected void initialize() {
 		System.out.println("initializing AimShooterArmCommand");
		
	}

	@Override
	protected void execute() {
		// TODO Add in method to aim articulating arm
    	String temp  = "[AutoComm] Robot aiming the articulating arm to ";
		shootOptionSelected = StrongholdRobot.shootOptionSelected;
				
		switch(shootOptionSelected){
		case HIGH_LEFT:
			temp += "HIGH_LEFT";
			break;
		case HIGH_CENTER:
			temp += "HIGH_CENTER";
			break;
		case HIGH_RIGHT:
			temp += "HIGH_RIGHT";
			break;
		case LOW_LEFT:
			temp += "LOW_LEFT";
			break;
		case LOW_RIGHT:
			temp += "LOW_RIGHT";
			break;
		default:
			break;
		}

		temp += " for shooting.";
		System.out.println(temp);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
