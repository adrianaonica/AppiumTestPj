package com.core.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class CustomLogger {

    public static Logger log;

    public static void configure(){

//      set log4j properties
        String usrDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        System.setProperty("my.log", usrDir + "/target/");
        log= Logger.getLogger(CustomLogger.class);
        log.setLevel(Level.ALL);
    }

}
