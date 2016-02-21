package org.usfirst.frc.team5422.utils;

import java.io.File;
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
    String robot = "stronghold"; //default

    public RobotConfigurationFileReader() {

        //Load the properties file
        try {
            fis = new FileInputStream("/home/lvuser/config.properties");
            properties = new Properties();
            properties.load(fis);

            fis.close();
            robot = properties.getProperty("robotInUse");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[File Reader] Robot in use: " + robot);
        //If you want to ignore the file, comment this back in.
        //robot = "stronghold"; //Default ("rhino" for old rhino drive, "stronghold" for new official 2016 robot)
    }

    public String getRobotInUse() {
        return robot;
    }
}
