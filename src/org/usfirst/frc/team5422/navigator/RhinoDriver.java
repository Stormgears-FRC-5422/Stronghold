package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidThread;
import org.usfirst.frc.team5422.utils.RhinoRobotConfigurationManager;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

/*
 * @author Michael
 */

public class RhinoDriver extends Driver {
    
    //Constructor
    public RhinoDriver() {
        configureRobot = new RhinoRobotConfigurationManager();
        configureRobot.configure();

        //Declare masterTalons
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

        masterTalon[0].setEncPosition(0);
        masterTalon[1].setEncPosition(0);
    }

	@Override
	public void turnToAlignVision() {
		// TODO Auto-generated method stub
		
	}

    
}