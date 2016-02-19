package org.usfirst.frc.team5422.navigator.trapezoidal;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Notifier;

public class MotionProfileExample {

	private CANTalon.MotionProfileStatus _status = new CANTalon.MotionProfileStatus();
	private CANTalon _talon;
	private double [][] profilePoints;

	//state machine stuff
	private int _state = 0;
	private int _loopTimeout = -1;
	private boolean _bStart = false;
	private CANTalon.SetValueMotionProfile _setValue = CANTalon.SetValueMotionProfile.Disable;

	
	//used to maintain timing
	private static final int kMinPointsInTalon = 5;
	private static final int kNumLoopsTimeout = 10;
	
	//nested class for thread which continually pushes points to MPB
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {
	    	_talon.processMotionProfileBuffer();    
	    }
	}
	Notifier _notifer = new Notifier(new PeriodicRunnable());

	
	public MotionProfileExample(CANTalon talon) {
		_talon = talon;
		_talon.configEncoderCodesPerRev(2048);
	
		profilePoints = new double[1][1];
		profilePoints[0][0] = 0;
		
		_talon.changeMotionControlFramePeriod(5);
		_notifer.startPeriodic(0.005);
		
	}
	
	public void setProfile(double [][] points) {
		profilePoints = points;
	}
	
	public void reset() {
		_talon.clearMotionProfileTrajectories();
		_setValue = CANTalon.SetValueMotionProfile.Disable;
		_state = 0;
		_loopTimeout = -1;
		_bStart = false;
	}

	
	public void control() {	
		_talon.getMotionProfileStatus(_status);

		if (_loopTimeout < 0) {
			//do nothing
		}
		 
		else {
		
			if (_loopTimeout == 0) 
				Instrumentation.OnNoProgress();
			else 
				--_loopTimeout;	
		}
		
		if (_talon.getControlMode() != TalonControlMode.MotionProfile) {
			_state = 0;
			_loopTimeout = -1;
		} 
		
		else {
			//enter state machine
			switch (_state) {
				case 0:
					if (_bStart) {
						_bStart = false;
						_setValue = CANTalon.SetValueMotionProfile.Disable;
						startFilling();
						_state = 1;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 1:
					if (_status.btmBufferCnt > kMinPointsInTalon) {
						_setValue = CANTalon.SetValueMotionProfile.Enable;
						_state = 2;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 2:
					
					if (_status.isUnderrun == false) {
						_loopTimeout = kNumLoopsTimeout;
					}
				
					if (_status.activePointValid && _status.activePoint.isLastPoint) {
						_setValue = CANTalon.SetValueMotionProfile.Hold;
						_state = 0;
						_loopTimeout = -1;
					}
					break;
			}
		}
		
		Instrumentation.process(_status);
	}
	
	private void startFilling() {
		startFilling(profilePoints, profilePoints.length);
	}

	private void startFilling(double[][] profile, int totalCnt) {
		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
	
		if (_status.hasUnderrun) {
			Instrumentation.OnUnderrun();
			_talon.clearMotionProfileHasUnderrun();
		}
	
		_talon.clearMotionProfileTrajectories();

	
		for (int i = 0; i < totalCnt; ++i) {
			point.position = profile[i][0];
			point.velocity = profile[i][1];
			point.timeDurMs = (int) profile[i][2];
			point.profileSlotSelect = 0; 
			point.velocityOnly = false; 
										
			/**WE DO NOT USE ZEROPOS, because we do not want to continually reset encoders**/

			point.isLastPoint = (i + 1) == totalCnt;
			_talon.pushMotionProfileTrajectory(point);
		}
	}

	public void startMotionProfile() {
		_bStart = true;
	}
	
	public void stopMotionProfile() {
		_bStart = false;
	}
	
	public CANTalon.SetValueMotionProfile getSetValue() {
		return _setValue;
	}
}
