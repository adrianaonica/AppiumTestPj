package com.core.utils;

import com.core.logger.CustomLogger;
import org.apache.log4j.Level;
import org.apache.log4j.net.SyslogAppender;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by pritamkadam on 10/09/16.
 */
public class TestUtils {

    @Test
    public void testPropertiesReader(){
        String usrDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        System.setProperty("my.log", usrDir + "/target/");

        CustomLogger.log.setLevel(Level.ALL);

//        PropertiesReader.loadAllProperties();
        System.out.println(PropertiesReader.config.getValue("APPIUM_JS_PATH"));

    }


}
