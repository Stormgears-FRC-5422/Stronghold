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

    public RobotConfigurationFileReader() {
        FileInputStream fis = null;


        //Load the properties file
//        try {
//            fis = new FileInputStream("config.properties");
//            properties = new Properties();
//            properties.load(fis);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public String getRobotInUse() {
        String robot = "stronghold"; //Default ("rhino" for old rhino drive, "stronghold" for new official 2016 robot)
        //robot = properties.getProperty("robotInUse");

        return robot;
    }
}
