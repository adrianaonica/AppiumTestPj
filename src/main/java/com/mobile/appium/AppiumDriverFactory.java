package com.mobile.appium;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pritamkadam on 10/09/16.
 */
public class AppiumDriverFactory {
    private static AppiumDriver driver;
    private static AppiumManager appiumManager;
    static
    {
        CustomLogger.log.info("Loading all the properties files");
        PropertiesReader.loadAllProperties();
    }

    public static void setDriver(AppiumDriver driver) {
        AppiumDriverFactory.driver = driver;
    }

    public static AppiumDriver getDriver() {

        return driver;
    }

    public synchronized DesiredCapabilities androidNativeCaps(String udid, String version) {
        CustomLogger.log.info("Generating desired capabilities for android native application.");

        DesiredCapabilities androidNativeCaps = new DesiredCapabilities();
        androidNativeCaps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        androidNativeCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        androidNativeCaps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, PropertiesReader.android.getValue("APP_ACTIVITY"));
        androidNativeCaps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PropertiesReader.android.getValue("APP_PACKAGE"));
        androidNativeCaps.setCapability(MobileCapabilityType.APP, PropertiesReader.android.getValue("ANDROID_APP_PATH"));
        androidNativeCaps.setCapability(MobileCapabilityType.UDID, udid);

        CustomLogger.log.info("[Desired Capabilities] =>" +
                "DEVICE_NAME : Android Device" +
                "PLATFORM_VERSION : " + version +
                "APP_ACTIVITY : " + PropertiesReader.android.getValue("APP_ACTIVITY") +
                "APP_PACKAGE : " + PropertiesReader.android.getValue("APP_PACKAGE") +
                "APP : " + PropertiesReader.android.getValue("ANDROID_APP_PATH") +
                "UDID : " + udid);

        return androidNativeCaps;
    }

    public synchronized DesiredCapabilities androidWebCaps(String udid, String version) {
        CustomLogger.log.debug("Generating desired capabilities for android web.");

        DesiredCapabilities androidWebCaps = new DesiredCapabilities();
        androidWebCaps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        androidWebCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        androidWebCaps.setCapability(MobileCapabilityType.BROWSER_NAME, PropertiesReader.android.getValue("BROWSER_NAME"));
        androidWebCaps.setCapability(MobileCapabilityType.UDID, udid);
        androidWebCaps.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);

        CustomLogger.log.info("[Desired Capabilities] =>" +
                "DEVICE_NAME : Android Device" +
                "PLATFORM_VERSION : " + version +
                "BROWSER : " + PropertiesReader.android.getValue("BROWSER") +
                "UDID : " + udid);

        return androidWebCaps;
    }

    public synchronized DesiredCapabilities iOSCaps(String udid, String version, MobilePlatform mobilePlatform) {
        CustomLogger.log.debug("Generating desired capabilities for iOS native application.");

        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);

        if(mobilePlatform.getValue().equalsIgnoreCase("Web_iOS"))
            iOSCapabilities.setCapability(IOSMobileCapabilityType.BROWSER_NAME, PropertiesReader.iOS.getValue("BROWSER_NAME"));
        else
            iOSCapabilities.setCapability(MobileCapabilityType.APP, PropertiesReader.iOS.getValue("IOS_APP_PATH"));

        if(udid.toCharArray().length == 40)
            iOSCapabilities.setCapability(MobileCapabilityType.UDID, udid);
        else
            iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, udid);

        CustomLogger.log.info("[Desired Capabilities] =>" +
                "DEVICE_NAME | UDID : " + udid +
                "PLATFORM_VERSION : " + version +
                "APP : " + PropertiesReader.iOS.getValue("IOS_APP_PATH"));

        return iOSCapabilities;
    }


    public AppiumDriver createDriver(String udid, String version, MobilePlatform mobilePlatform) throws MalformedURLException {

        switch (mobilePlatform){

            case Web_Android:
                driver = new AndroidDriver(new URL(appiumManager.getHost() +"/wd/hub/"), androidWebCaps(udid, version));
                break;

            case iOS:
                driver = new IOSDriver(new URL(appiumManager.getHost() +"/wd/hub/"), iOSCaps(udid, version, mobilePlatform));
                break;

            case Android:
                driver = new AndroidDriver(new URL(appiumManager.getHost() +"/wd/hub/"), androidNativeCaps(udid, version));
                break;

        }

        return driver;
    }
}
