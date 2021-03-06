package org.usfirst.frc.team5422.DSIO;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5422.controller.StrongholdRobot;
import org.usfirst.frc.team5422.utils.StrongholdConstants;
import org.usfirst.frc.team5422.utils.StrongholdConstants.teleopModes;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author Michael
 */

public class DSIO {
    //This class controls the I/O of the rhinoDriver station, basically the back-end of the user interface
    //It takes in inputs such as button presses and the x and y of the joystick; does not drive anything
    //Depending on the method used, it outputs either a boolean or an x and y value that should be passed on to another class

    //Constants may need to be changed

    static Joystick joystick;
    public static StrongholdConstants.shootHeightOptions teleopShootHeightOption;
    public static Joystick buttonBoard;
    public static boolean shooterRunning = false, assistShoot = false;

    public static int pos[] = new int[5];

    public static SmartDashboardChooser choosers;

    private static int green = 0, orange = 0, red = 0;

    //Constructor
    public DSIO(int joyStickChannel, int buttonBoardChannel) {
        joystick = new Joystick(joyStickChannel);
        buttonBoard = new Joystick(buttonBoardChannel);
    }

    //Check if a button is pressed; if it is, do the respective command
    public static int getButtons()
    {
        int button = -1;
        //Shooter operation buttons
//        if (buttonBoard.getRawButton(StrongholdConstants.BIG_BLUE_BUTTON_ID))
//        {
//            button = StrongholdConstants.BIG_BLUE_BUTTON_ID;
//        }
        if ((buttonBoard.getRawButton(StrongholdConstants.BIG_BLUE_BUTTON_ID)) &&
        		(!StrongholdRobot.blueButtonPressed))
        {
            //button = StrongholdConstants.BIG_BLUE_BUTTON_ID;
            StrongholdRobot.blueButtonPressed = true;
        }

        if ((!buttonBoard.getRawButton(StrongholdConstants.BIG_BLUE_BUTTON_ID)) &&
        		(StrongholdRobot.blueButtonPressed))
        {
            //button = -1;
            StrongholdRobot.blueButtonPressed = false;
            StrongholdRobot.shotTaken = false;
        }
        if (StrongholdRobot.bbInUse)
        {
            //Intake switch
            if (buttonBoard.getRawButton(StrongholdConstants.ORANGE_SWITCH_ID))
            {
                button = StrongholdConstants.ORANGE_SWITCH_ID;
            }
            else if (!shooterRunning)
            {
                StrongholdRobot.shooterSubsystem.stop();
            }

            //Red switch (assisted shooting or manual shooting)
            if (buttonBoard.getRawButton(StrongholdConstants.RED_SWITCH_ID))
            {
                assistShoot = true;
                SmartDashboard.putString("ASSIST SHOOTING:", " ON");
            }
            else
            {
                assistShoot = false;
                SmartDashboard.putString("ASSIST SHOOTING: ", " OFF");
            }

            //Get the position of the green switch; if it's up, set the shoot height to high
            //This does not require a command
            if (buttonBoard.getRawButton(StrongholdConstants.GREEN_SWITCH_ID))
            {
                teleopShootHeightOption = StrongholdConstants.shootHeightOptions.HIGH;
            }
            else
            {
                teleopShootHeightOption = StrongholdConstants.shootHeightOptions.LOW;
            }
        }
        else
        {
            SmartDashboard.putNumber("Green Switch", green);
            SmartDashboard.putNumber("Orange Switch", orange);
            SmartDashboard.putNumber("Red Switch", red);

            green = (int) SmartDashboard.getNumber("Green Switch", green);
            orange = (int) SmartDashboard.getNumber("Orange Switch", orange);
            red = (int) SmartDashboard.getNumber("Red Switch", red);

            if (green == 1) teleopShootHeightOption = StrongholdConstants.shootHeightOptions.HIGH;
            else teleopShootHeightOption = StrongholdConstants.shootHeightOptions.LOW;

            if (orange == 1) button = StrongholdConstants.ORANGE_SWITCH_ID;
            else if (!shooterRunning) StrongholdRobot.shooterSubsystem.stop();

            if (red == 1)
            {
                assistShoot = true;
                SmartDashboard.putString("ASSIST SHOOTING:", " ON");
            }
            else
            {
                assistShoot = false;
                SmartDashboard.putString("ASSIST SHOOTING: ", " OFF");
            }
        }

        //Lock button
        if (buttonBoard.getRawButton(StrongholdConstants.BLUE_BUTTON_ID))
        {
           button = StrongholdConstants.BLUE_BUTTON_ID;
        }

        //Cross defense button
        else if (buttonBoard.getRawButton(StrongholdConstants.GREEN_BUTTON_ID))
        {
            button = StrongholdConstants.GREEN_BUTTON_ID;
        }

        return button;
    }

    @Deprecated
    public double getAngleSliderValue()
    {
        return buttonBoard.getX();
    }

    @Deprecated
    public double getSpeedSliderValue ()
    {
        return buttonBoard.getY();
    }

    //Inputs: nothing
    //Outputs: x and y of joystick (raw) respectively
    //Linear joystick position recieving methods
    public static double getLinearX()
    {
        double xPos = joystick.getX();

        //Put a nullzone on values that are between -0.2 and 0.2
        if (xPos >= -0.2 & xPos <= 0.2) xPos = 0;

        return xPos;
    }

    public static double getLinearY()
    {
        double yPos = joystick.getY();

        //Put a nullzone on values that are between -0.2 and 0.2
        if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

        return yPos;
    }

    public static double getY()
    {
        double yPos = joystick.getY();
        return yPos;
    }


    //Inputs: nothing
    //Outputs: averaged x and y of joystick over a period of 15 milliseconds respectively
    //Sample size (length of x and y arrays) may need to be changed depending on how much you want the vals to be dampened
    public static double dampenWithAvgX()
    {
        double xPos[] = new double[15];

        double xPosAvg = 0;

        for (int c = 0; c < xPos.length; c++)
        {
            xPos[c] = joystick.getX();

            xPosAvg += xPos[c];

            //Wait 1 millisecond
            try
            {
                Timer.delay(0.001);
            }
            catch (Exception e)
            {
                System.out.println("Couldn't sleep the thread.");
            }
        }

        //Avg the positions from the total calculated in above for loop
        xPosAvg /= 15;

        //Put a nullzone on values that are between -0.2 and 0.2
        if (xPosAvg >= -0.2 & xPosAvg <= 0.2) xPosAvg = 0;

        return xPosAvg;
    }

    public static double dampenWithAvgY()
    {
        double yPos[] = new double[15];

        double yPosAvg = 0;

        for (int c = 0; c < yPos.length; c++)
        {
            yPos[c] = joystick.getY();

            yPosAvg += yPos[c];

            //Wait 1 millisecond
            try
            {
                Timer.delay(0.001);
            }
            catch (Exception e)
            {
                System.out.println("Couldn't sleep the thread.");
            }
        }

        //Avg the positions from the total calculated in above for loop
        yPosAvg /= 15;

        //Put a nullzone on values that are between -0.2 and 0.2
        if (yPosAvg >= -0.2 & yPosAvg <= 0.2) yPosAvg = 0;

        return yPosAvg;
    }

    //Inputs: nothing
    //Outputs: x and y of joystick after going through a x^2 graph respectively
    public static double dampenWithCurveX()
    {
        double xPos = joystick.getX();
        double yPos = joystick.getY();

        double xFinal;

        //Put the x (left, right) through a curved graph (quadratic function)
        xFinal = xPos * xPos;

        //If x is negative, reverse the direction of the graph
        if (xPos < 0) xFinal *= -1;

        //Put a nullzone on values that are between -0.2 and 0.2
        if (xPos >= -0.2 & xPos <= 0.2) xPos = 0;
        if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

        return xFinal;
    }

    public static double dampenWithCurveY()
    {
        double yPos = joystick.getY();

        double yFinal;

        //Put the y value (fwd, back) through a curved graph (quadratic function)
        yFinal = yPos * yPos;

        //If y is negative, reverse the direction of the graph
        if (yPos < 0) yFinal *= -1;

        //Put a nullzone on values that are between -0.2 and 0.2
        if (yPos >= -0.2 & yPos <= 0.2) yPos = 0;

        return yFinal;
    }

    //Inputs: what to print out
    //Outputs: nothing
    //Wrapper method for putNumber()
    public static void outputToSFX(String label, double value)
    {
        SmartDashboard.putNumber(label, value);
    }

    public static void createUI()
    {
        NetworkTable.initialize();

        //Defense at position 0 is always low bar (ID 0)
        pos[0] = 0;

        choosers = new SmartDashboardChooser();
        choosers.initChoosers();
        //choosers.autoInitChoosers();
    }
   
//    public static int getSelectedDefensePosition()
//    {
//        int position = -1;
//
//        //Defense at position 0 is always low bar (ID 0)
//        pos[0] = 0;
//        pos[1] = (int) SmartDashboard.getNumber("Defense at Position 2", -1);
//        pos[2] = (int) SmartDashboard.getNumber("Defense at Position 3", -1);
//        pos[3] = (int) SmartDashboard.getNumber("Defense at Position 4", -1);
//        pos[4] = (int) SmartDashboard.getNumber("Defense at Position 5", -1);
//
//        int[] defensePositions = new int[9];
//
//        for (int i = 1; i < 9; i++)
//        {
//            defensePositions[i] = -1;
//        }
//
//        for (int i = 1; i < 9; i++)
//        {
//            for (int c = 1; c < 5; c++)
//            {
//                if (pos[c] == i) {
//                    defensePositions[i] = c;
//                }
//            }
//        }
//
//        //If auto is running, get position from SmartDashboard
//        if (StrongholdRobot.teleopNotRunning)
//        {
//            switch ((defenseTypeOptions) SmartDashboardChooser.defenseChooser.getSelected())
//            {
//                case LOW_BAR:
//                    position = 0; //Position of low bar is always 0
//                    break;
//                case PORTCULLIS:
//                    position = defensePositions[1];
//                    break;
//                case CHIVAL_DE_FRISE:
//                    position = defensePositions[2];
//                    break;
//                case MOAT:
//                    position = defensePositions[3];
//                    break;
//                case RAMPARTS:
//                    position = defensePositions[4];
//                    break;
//                case DRAWBRIDGE:
//                    position = defensePositions[5];
//                    break;
//                case SALLYPORT:
//                    position = defensePositions[6];
//                    break;
//                case ROCK_WALL:
//                    position = defensePositions[7];
//                    break;
//                case ROUGH_TERRAIN:
//                    position = defensePositions[8];
//                    break;
//                case NONE:
//                    position = -1;
//                    break;
//            }//End switch
//        }
//        System.out.println("Defense type " + (defenseTypeOptions) SmartDashboardChooser.defenseChooser.getSelected() + " at position " + position);
//        return position;
//    }//End method

    public static double getActuatorSliderValue()
    {
    	//return joystick.getThrottle();
    	return buttonBoard.getX();
    }
    public static double getSpeedSlider2Value()
    {
//    	return joystick.getThrottle();
//    	double zValue = buttonBoard.getZ(); 
//    	System.out.println("Speed Slider Value: " + zValue);
//    	return zValue;
    	return buttonBoard.getY();

    }

    public static boolean createTestChoosers() {
        teleopModes mode = (teleopModes) choosers.modeChooser.getSelected();
        if (mode == teleopModes.TEST) {
            choosers.initTestMotorChooser();
            choosers.testInitChoosers();

            return true;
        }
        else return false;
    }
}
