package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.navigator.trapezoidal.TrapezoidThread;
import org.usfirst.frc.team5422.utils.StrongholdRobotConfigurationManager;
import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.CANTalon;
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

}