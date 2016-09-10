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


    public static void loadAllProperties(){
        try {

            config.load(new FileInputStream("config.properties"));
            global.load(new FileInputStream("global.properties"));
            iOS.load(new FileInputStream("iOS.properties"));
            android.load(new FileInputStream("android.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getValue(String key){
        if(containsKey(key))
            return getProperty(key);
        else {
            CustomLogger.log.error(key + " property not present in config/global properties file");
            throw new RuntimeException(key + " property not present in config/global properties file");
        }
    }

}
