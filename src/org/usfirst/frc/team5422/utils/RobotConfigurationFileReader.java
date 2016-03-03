package org.usfirst.frc.team5422.utils;

import org.usfirst.frc.team5422.controller.StrongholdRobot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Michael
 */
public class RobotConfigurationFileReader {
    Properties properties = null;
    FileInputStream fis = null;
    String robot = StrongholdConstants.STRONGHOLD; //default
    String inputMethod = StrongholdConstants.PROP_INPUT_METHOD_OFFICIAL; //default

    public RobotConfigurationFileReader() {

        //Load the properties file
        try {
            fis = new FileInputStream("/home/lvuser/config.properties");
            properties = new Properties();
            properties.load(fis);

            fis.close();
            robot = properties.getProperty("robotInUse");
            inputMethod = properties.getProperty("inputMethodInUse");
           
            if (robot == null) robot = StrongholdConstants.STRONGHOLD;
            if (inputMethod == null) inputMethod = StrongholdConstants.PROP_INPUT_METHOD_OFFICIAL;
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        configureButtonIDs(inputMethod);

        System.out.println("[File Reader] Robot in use: " + robot);
        //If you want to ignore the file, comment this back in.
        //robot = "stronghold"; //Default ("rhino" for old rhino drive, "stronghold" for new official 2016 robot)
    }

    public String getRobotInUse() {
        return robot;
    }

    public void configureButtonIDs(String inputMethod) {
        if (inputMethod.equals(StrongholdConstants.PROP_INPUT_METHOD_SIMULATOR)) {
            StrongholdConstants.BIG_BLUE_BUTTON_ID = 2;
            StrongholdConstants.RED_BUTTON_ID = 11;
            StrongholdConstants.YELLOW_BUTTON_ID = 12;
            StrongholdConstants.GREEN_BUTTON_ID = 9;
            StrongholdConstants.BLUE_BUTTON_ID = 10;
            StrongholdConstants.BLACK_BUTTON_ID = 7;
            StrongholdConstants.WHITE_BUTTON_ID = 8;

            StrongholdRobot.bbInUse = false;
        }
    }
}
