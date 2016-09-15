package com.core.utils;

import com.core.logger.CustomLogger;
import org.apache.log4j.Level;
import org.apache.log4j.net.SyslogAppender;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Random;

/**
 * Created by pritamkadam on 10/09/16.
 */
public class TestUtils {

    @Test
    public void testPropertiesReader(){

        System.out.println(PropertiesReader.config.getValue("APPIUM_JS_PATH"));

    }

    @Test
    public void testRandomGenrator(){
        Random random = new Random();
        int number = random.nextInt(3) + 1;
        System.out.println(number);
    }

}
