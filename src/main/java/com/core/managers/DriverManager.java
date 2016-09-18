package com.core.managers;

import com.mobile.appium.AppiumDriverFactory;
import com.mobile.appium.AppiumManager;
import io.appium.java_client.AppiumDriver;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class DriverManager{

    private static AppiumManager appiumManager = new AppiumManager();

    public static AppiumDriver getAppiumDriver(){
        return appiumManager.getDriver();
    }


}
