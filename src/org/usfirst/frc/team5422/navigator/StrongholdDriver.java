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
    public static double error = 0;

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
    
    public void turnToAlignVision() {
    	StrongholdRobot.gripNotWorking = false;
    	System.out.println("Turn to Align Vision entered");
    	
    	final double degreePerPixelHorizontal  = 67.5/StrongholdConstants.CAMERA_WIDTH_PIXELS;
    	final double radiansPerPixelHorizontal = Math.toRadians(degreePerPixelHorizontal );
    	final long pixelsPerDegreeDeviation =  Math.round(1/degreePerPixelHorizontal);
    	
    	//assume the deviation/correction between the robot drive moving to angle accuracy to camera angle accuracy
    	final double deviationMultiplier = 1.0;
    	
    	double setPoint = StrongholdConstants.CAMERA_WIDTH_PIXELS/2.0 + StrongholdConstants.T2_CAMERA_ERROR;//400 - 15;
    	
    	//0-319, 160
    	//error = current pixel position - 160
    	//set F on both talons to error * K --> K is some constant
    	//used for turning, run motors in reverse/opposite 	
//    	Timer.delay(1.0);
    
    	masterTalon[0].changeControlMode(TalonControlMode.Position);
    	masterTalon[1].changeControlMode(TalonControlMode.Position);
    	
    	masterTalon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	masterTalon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	
    	System.out.println("error: " + error);
    	double centerX = Vision.getCenterX();
    	double startTime = Timer.getFPGATimestamp();

    	while(centerX == 0) {
    		centerX = Vision.getCenterX();
    		if((Timer.getFPGATimestamp() - startTime) > 3) {
    			StrongholdRobot.gripNotWorking = true;
    			break;
    		}
    	}
    	
    	startTime = Timer.getFPGATimestamp();
    	error = centerX - setPoint;
    	
   		if(!StrongholdRobot.gripNotWorking) {
   			boolean errorInLoop = false;
   	    	while((Math.abs(error) > 5) && ((Timer.getFPGATimestamp() - startTime) <= 10) && errorInLoop == false) {
   	    		
   	    		double turnAngle = error * radiansPerPixelHorizontal;
   	    		if(Math.abs(turnAngle) < 0.05) {
   	    			if(error < 0) turnAngle = -0.05;
   	    			else turnAngle = 0.05;
   	    		}
   	   				//this multiplier changes based on which treads are used. 1.15 for original treads
//   	   				StrongholdRobot.navigatorSubsystem.turnToRelative(-1 * error * radiansPerPixelHorizontal * 1.115);
   				StrongholdRobot.navigatorSubsystem.turnToRelative(-1 * turnAngle);
   	   			System.out.println("SetPoint: " + setPoint +  " Centerx: " + centerX + " Error: " + error + " turn angle: " + Math.toDegrees(-1*turnAngle));
   	   			
   	   			centerX = Vision.getCenterX();
   	    		if(centerX != 0) { 
   	    			error = centerX - setPoint;
   	    		} else {
   	   	    		System.out.println("Center X is ZERO, therefore breaking");   	    			
   	    			StrongholdRobot.gripNotWorking = true;
   	    			break;
   	    		}
   	    		System.out.println("new Center X: " + centerX);
   	    		System.out.println("error: " + error);
   	    	}  
   	      	    	
			if (!StrongholdRobot.gripNotWorking) {
		    	masterTalon[0].changeControlMode(TalonControlMode.PercentVbus);
				masterTalon[1].changeControlMode(TalonControlMode.PercentVbus);
				masterTalon[0].set(0);
				masterTalon[1].set(0);
			}
			error = 0;
   		}    	
    }
    
//    public void turnToAlignVision() {
//    	StrongholdRobot.gripNotWorking = false;
//    	System.out.println("Turn to Align Vision entered");
//    	final double CAMERA_ERROR = 20;
//    	final double radiansPerPixelHorizontal = Math.toRadians(84.25/800.0);
//    	double setPoint = 400 - 15;
//    	
//    	//0-319, 160
//    	//error = current pixel position - 160
//    	//set F on both talons to error * K --> K is some constant
//    	//used for turning, run motors in reverse/opposite 	
//    	Timer.delay(1.0);
//    
//    	masterTalon[0].changeControlMode(TalonControlMode.Position);
//    	masterTalon[1].changeControlMode(TalonControlMode.Position);
//    	
//    	masterTalon[0].setFeedbackDevice(FeedbackDevice.QuadEncoder);
//    	masterTalon[1].setFeedbackDevice(FeedbackDevice.QuadEncoder);
//    	
//    	
//    	
//    	System.out.println("error: " + error);
//    	
//    	
//    	double accumulatedError = 0;
//    	long loopCount = 0;
//    	
//    	double centerX = Vision.getCenterX();
//    	double start = Timer.getFPGATimestamp();
//    	while(centerX == 0) {
//    		centerX = Vision.getCenterX();
//    		if((Timer.getFPGATimestamp() - start) > 3) {
//    			StrongholdRobot.gripNotWorking = true;
//    			break;
//    		}
//    	}
//    	
//    	System.out.println("first centerX: " + centerX);
//    	error = centerX - setPoint;
//    	System.out.println("first error: " + error);
//    	
//   		if(!StrongholdRobot.gripNotWorking) {
//	    	StrongholdRobot.navigatorSubsystem.turnToRelative(-1 * error * radiansPerPixelHorizontal*1.15);
//	    	System.out.println("turn angle original: " + error * radiansPerPixelHorizontal);
//   		}    	
//    	
//    	double startTime = Timer.getFPGATimestamp();
//    
//    	error = Vision.getCenterX() - setPoint;
//    	error /= -200.0;
//    	while((Math.abs(error) > 0.025) && ((Timer.getFPGATimestamp() - startTime) <= 3)) {
//    	
//    		if(Vision.getCenterX() == 0) break;
//    		if(error < 0)
//    			StrongholdRobot.navigatorSubsystem.turnToRelative(-0.05);
//    		else
//    			StrongholdRobot.navigatorSubsystem.turnToRelative(0.05);
//    		
//    		error = Vision.getCenterX() - setPoint;
//    		error/= -200.0;
//    
//    		System.out.println("error: " + error);
//    		
//    		
//
//	    		if(loopCount == 100) {
//	    			accumulatedError = error;
//	    			loopCount = 0;
//	    		}
//	    		else {
//	    			accumulatedError += error;
//	    			loopCount ++;
//	    		}
//	    		
//	   	
//	    		if(Math.abs(accumulatedError) == 210) {
//	    			masterTalon[0].changeControlMode(TalonControlMode.PercentVbus);
//	    			masterTalon[1].changeControlMode(TalonControlMode.PercentVbus);
//	    			masterTalon[0].set(0);
//	    			masterTalon[1].set(0);
//	    			break;
//	    		}
//	    		
//	    		if((Timer.getFPGATimestamp() - startTime) > 3) break;
//    		
//    		}
//    	
//	    	masterTalon[0].changeControlMode(TalonControlMode.PercentVbus);
//			masterTalon[1].changeControlMode(TalonControlMode.PercentVbus);
//			masterTalon[0].set(0);
//			masterTalon[1].set(0);
//	    
//	    	System.out.println("error: " + error);
//	    	
//	    	
//	    }
}