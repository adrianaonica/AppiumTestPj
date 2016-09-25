package com.core.utils;

import com.core.cli.CommandPrompt;
import com.core.logger.CustomLogger;
import com.core.managers.OSManager;
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

        String usrDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        System.setProperty("my.log", usrDir + "/target/");
        CustomLogger.log.setLevel(Level.ALL);

        String APPIUM_JS_PATH = PropertiesReader.config.getValue("APPIUM_JS_PATH");

        String iOSWebKitDebugProxyRunnerPath = APPIUM_JS_PATH.substring(0, APPIUM_JS_PATH.indexOf("build")) + "bin/ios-webkit-debug-proxy-launcher.js";
        System.out.println(iOSWebKitDebugProxyRunnerPath);
    }

    @Test
    public void testRandomGenrator(){
        Random random = new Random();
        int number = random.nextInt(3) + 1;
        System.out.println(number);
    }


    @Test
    public void testRemoveTmpDir(){
        CustomLogger.configure();
        CommandPrompt.run("rm -rd " + OSManager.getWorkingDir() + "/target/tmp*");
    }

    @Test
    public void testSystemPath(){
        System.setProperty("HOME", "/Users/");
        System.out.print(System.getProperty("HOME"));
    }
}
