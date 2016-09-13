package com.core.utils;

import com.core.logger.CustomLogger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by pritamkadam on 10/09/16.
 */
public class PropertiesReader extends Properties{

    public static PropertiesReader config =  new PropertiesReader();
    public static PropertiesReader global = new PropertiesReader();
    public static PropertiesReader iOS = new PropertiesReader();
    public static PropertiesReader android = new PropertiesReader();

    static
    {
        loadAllProperties();
    }

    public static void loadAllProperties(){
        try {

            config.load(new FileInputStream("config.properties"));
            CustomLogger.log.info("Loaded config.properties.");

            global.load(new FileInputStream("global.properties"));
            CustomLogger.log.info("Loaded global.properties.");

            iOS.load(new FileInputStream("iOS.properties"));
            CustomLogger.log.info("Loaded iOS.properties.");

            android.load(new FileInputStream("android.properties"));
            CustomLogger.log.info("Loaded android.properties.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getValue(String key){
        if(containsKey(key))
            return getProperty(key);
        else {
            CustomLogger.log.error(key + " property not present in one of the properties file.");
            throw new RuntimeException(key + " property not present in one of properties file");
        }
    }

}
