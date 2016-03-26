package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidThread;
import org.usfirst.frc.team5422.sensors.Vision;
import org.usfirst.frc.team5422.utils.StrongholdRobotConfigurationManager;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/*
 * @author Michael
 */

public class StrongholdDriver extends Driver {
	public static CANTalon slaveTalon[] = new CANTalon[2];

    //Constructor
    public StrongholdDriver() {
        configureRobot = new StrongholdRobotConfigurationManager();
        configureRobot.configure();

        //Declare talons
        masterTalon[0] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_MASTER);
        masterTalon[1] = new CANTalon(StrongholdConstants.TALON_DRIVE_RIGHT_MASTER);

        masterTalon[0].reverseOutput(true);
        masterTalon[1].reverseOutput(true);

        /**create the trap thread**/
        
        trapThread = new TrapezoidThread(masterTalon[0], masterTalon[1]);
        
        for (int i = 0; i < 2; i++) {
        	masterTalon[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
        	masterTalon[i].configEncoderCodesPerRev(2048);
        	masterTalon[i].configNominalOutputVoltage(+0.0f, -0.0f);
        	masterTalon[i].setPID(StrongholdConstants.OPEN_DRIVE_P, StrongholdConstants.OPEN_DRIVE_I, StrongholdConstants.OPEN_DRIVE_D);
        	masterTalon[i].setF(StrongholdConstants.OPEN_DRIVE_F);
        }

        slaveTalon[0] = new CANTalon(StrongholdConstants.TALON_DRIVE_LEFT_SLAVE);
        slaveTalon[1] = new CANTalon(StrongholdConstants.TALON_DRIVE_RIGHT_SLAVE);
        
        slaveTalon[0].reverseOutput(false);
        slaveTalon[1].reverseOutput(false);

        slaveTalon[0].changeControlMode(TalonControlMode.Follower);
        slaveTalon[1].changeControlMode(TalonControlMode.Follower);
        
        masterTalon[0].setEncPosition(0);
        masterTalon[1].setEncPosition(0);
        
        slaveTalon[0].set(StrongholdConstants.TALON_DRIVE_LEFT_MASTER);
        slaveTalon[1].set(StrongholdConstants.TALON_DRIVE_RIGHT_MASTER);
    }
    
    
    double error = 0;
    public void turnToAlignVision() {
    	
    	System.out.println("Turn to Align Vision entered");
    	final double CAMERA_ERROR = 20;
    	final double radiansPerPixelHorizontal = Math.toRadians(84.25/800.0);
    	double setPoint = 400 + 20;
    	
    	//0-319, 160
    	//error = current pixel position - 160
    	//set F on both talons to error * K --> K is some constant
    	//used for turning, run motors in reverse/opposite 	
    	Timer.delay(1.0);
    
    	masterTalon[0].changeControlMode(TalonControlMode.Position);
    	masterTalon[1].changeControlMode(TalonControlMode.Position);
    	
    	masterTalon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	masterTalon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	
    	
    	
    	System.out.println("error: " + error);
    	
    	
    	double accumulatedError = 0;
    	long loopCount = 0;
    	
    	double centerX = Vision.getCenterX();
    	while(centerX == 0) centerX = Vision.getCenterX();
    	
    	System.out.println("first centerX: " + centerX);
    	error = centerX - setPoint;
    	System.out.println("first error: " + error);
    	
    	
    	StrongholdRobot.navigatorSubsystem.turnToRelative(-1 * error * radiansPerPixelHorizontal);
    	System.out.println("turn angle original: " + error * radiansPerPixelHorizontal);
    	
    	
    	double startTime = Timer.getFPGATimestamp();
    
    	error = Vision.getCenterX() - setPoint;
    	error /= -200.0;
    	while(Math.abs(error) > 0.025 && Timer.getFPGATimestamp() - startTime <= 5) {
    		if(error < 0)
    			StrongholdRobot.navigatorSubsystem.turnToRelative(-0.05);
    		else
    			StrongholdRobot.navigatorSubsystem.turnToRelative(0.05);
    		
    		error = Vision.getCenterX() - setPoint;
    		error/= -200.0;
    
    		System.out.println("error: " + error);
    		
    		

	    		if(loopCount == 100) {
	    			accumulatedError = error;
	    			loopCount = 0;
	    		}
	    		else {
	    			accumulatedError += error;
	    			loopCount ++;
	    		}
	    		
	   	
	    		if(Math.abs(accumulatedError) == 210) {
	    			masterTalon[0].changeControlMode(TalonControlMode.PercentVbus);
	    			masterTalon[1].changeControlMode(TalonControlMode.PercentVbus);
	    			masterTalon[0].set(0);
	    			masterTalon[1].set(0);
	    			break;
	    		}
    		
    		}
    	
	    	masterTalon[0].changeControlMode(TalonControlMode.PercentVbus);
			masterTalon[1].changeControlMode(TalonControlMode.PercentVbus);
			masterTalon[0].set(0);
			masterTalon[1].set(0);
	    
	    	System.out.println("error: " + error);
	    	
	    	
	    }
}