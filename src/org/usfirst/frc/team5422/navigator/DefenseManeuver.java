package org.usfirst.frc.team5422.navigator;

import org.usfirst.frc.team5422.utils.StrongholdConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Mayank Mali
 */

public class DefenseManeuver implements Runnable{
	
	
	/**
	 * used to print out a message to <code>System.out</code> 
	 * with an abbreviated name of the class to make debugging easier
	 * @param message	message to print
	 */
	private static void namedPrint(String message){
		System.out.println("[DefMan] " + message);
	}
	
	private static double latestLocalX;
	private static double latestTheta;
	private static double latestLocalTheta; 
	
	private static NetworkTable ultraTable = NetworkTable.getTable("Sensors/Ultrasonic");
	private static NetworkTable gyroTable = NetworkTable.getTable("Sensors/Gyro");
	
	private static boolean rightUltraInRange;
	private static boolean leftUltraInRange;
	
	private static int stage = 1;
	private static int nStages = 4;
	
	public static boolean isLastStage(){
		return stage == nStages;
	}
	
	/**Is the passable width of any defense (between shields) in inches*/
	private static double defensePassableLength = StrongholdConstants.DEFENSE_PASSABLE_LENGTH;
	private static double defenseWidth = StrongholdConstants.DEFENSE_WIDTH;
	private static double defenseMiddlePosY = (double)StrongholdConstants.POSITION_DEFENSE_0_REACH[1];
	
	private static double robotWidth = StrongholdConstants.ROBOT_WIDTH;
	private static double sensorToRobotMiddleY= StrongholdConstants.SIDE_ULTRA_SENSOR_TO_ROBOT_MIDDLE_Y;
	
	private static DefenseManeuver instance;
	
	public DefenseManeuver() {
		namedPrint("[DefMan] Defense Maneuver Created");
	}
	
	public static DefenseManeuver getInstance(){
		return instance==null? new DefenseManeuver(): instance; 
	}
	
	public void repositionAtEndDefense(){
		
		//discrete y position for end of defense
		
		GlobalMapping.getInstance().setY(defenseMiddlePosY + defenseWidth/2);
		
		//find x position based on latest ultrasound value
		
		double fieldX = GlobalMapping.getInstance().getX();//find current defense by position
		
		int vals[] = {
				StrongholdConstants.POSITION_DEFENSE_0_REACH[0],
				StrongholdConstants.POSITION_DEFENSE_1_REACH[0],
				StrongholdConstants.POSITION_DEFENSE_2_REACH[0],
				StrongholdConstants.POSITION_DEFENSE_3_REACH[0],
				StrongholdConstants.POSITION_DEFENSE_4_REACH[0]
		};
		
		for(int i=0, n=vals.length;i<n;i+=1){
			if(fieldX - vals[i] < 0){
				fieldX = vals[i-1];
				break;
			}
		}
		
		if(0 <= latestTheta && latestTheta < Math.PI ){//if going to enemy castle
			fieldX += latestLocalX;
		}else{//if coming from enemy castle
			fieldX -= latestLocalX;
		}
		GlobalMapping.getInstance().setX(fieldX);
	}
	
	/**
	 * Updates the <code>latestDefenseX</code> and <code>theta</code> as the robot goes through the
	 * defense
	 * <p>
	 * this is called through a notifier (by <code>Navigator</code>) when going through a defense is anticipated.
	 */
	
	public synchronized void updateDefenseManeuver(){
		
		double leftSpace  =(double)ultraTable.getNumber("Left", 0);//gives ints
		
		double rightSpace = (double)ultraTable.getNumber("Right", 0);//gives ints
		
		if(leftSpace + rightSpace > 23){
			if(rightSpace == 6.0){
				rightUltraInRange = false;
				leftUltraInRange = true;
				namedPrint("right sensor out of range");
			}else if(leftSpace == 6.0){
				rightUltraInRange = true;
				leftUltraInRange = false;
				namedPrint("left sensor out of range");
			}
		}else{
			rightUltraInRange = true;
			leftUltraInRange = true;
			namedPrint("Both sensors in range");
		}
		
		if(rightUltraInRange && leftUltraInRange){
			//everything is okay
		}else if(rightUltraInRange){
			//calculate what left space would actually be
			leftSpace  = defensePassableLength/Math.cos(latestLocalTheta) - (rightSpace + robotWidth);
		}else if(leftUltraInRange){
			rightSpace  = defensePassableLength/Math.cos(latestLocalTheta) - (leftSpace + robotWidth);
		}else{
			namedPrint("something's wrong with Ultrasound ranges");
		}
		
		if(rightUltraInRange && leftUltraInRange){
			
			double partError = Math.abs(leftSpace + rightSpace + robotWidth - defensePassableLength)/(defensePassableLength);
			
			if(partError < 0.1){//is completely on ramp
				
				//latestLocalTheta = Math.acos(defensePassableWidth/(leftSpace + rightSpace + robotWidth));
				latestLocalTheta = gyroTable.getNumber("theta", -1);
				
				if(stage == 1){
					stage++;
				}
				
				latestLocalX = leftSpace * Math.cos(latestLocalTheta) - sensorToRobotMiddleY/2*Math.sin(latestLocalTheta);
				
			}else{//isn't completely on ramp
				
				stage += (stage == 2)? 1 : 0;
				
				if(leftSpace > 40 && rightSpace > 40){//should stop the process
					
					stage = (stage >= 2)? 4 : stage;
					
					GlobalMapping.getInstance().setTheta(latestLocalTheta + Math.PI/2);
					
				}else if(rightSpace > 40){
					
					latestLocalX = Math.cos(latestLocalTheta) * (leftSpace + robotWidth/2);
					stage += (stage == 2)? 1 : 0;
					
				}else if(leftSpace > 40){
					
					stage += (stage == 2)? 1 : 0;
					
					
				}else{
					namedPrint("Something's wrong with spacing...");
				}
			}
			
			
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		updateDefenseManeuver();
	}

}