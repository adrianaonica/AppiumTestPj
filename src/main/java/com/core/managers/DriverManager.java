package com.core.managers;

import com.mobile.appium.AppiumDriverFactory;
import io.appium.java_client.AppiumDriver;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class DriverManager{

    public static AppiumDriver getAppiumDriver(){
        return AppiumDriverFactory.getDriver();
    }


}
