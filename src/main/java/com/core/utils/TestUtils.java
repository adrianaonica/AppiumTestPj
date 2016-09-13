package com.core.utils;

import com.core.logger.CustomLogger;
import org.apache.log4j.Level;
import org.testng.annotations.Test;

/**
 * Created by pritamkadam on 10/09/16.
 */
public class TestUtils {

    @Test
    public void testPropertiesReader(){

        CustomLogger.log.setLevel(Level.ALL);
//        PropertiesReader.loadAllProperties();
        System.out.println(PropertiesReader.config.getValue("config"));

    }


}
