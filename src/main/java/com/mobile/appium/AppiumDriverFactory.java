package com.mobile.appium;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.MobilePlatform;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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

    public static void setDriver(AppiumDriver driver) {
        AppiumDriverFactory.driver = driver;
    }

    public AppiumDriver getDriver() {

        return driver;
    }

    public synchronized DesiredCapabilities androidNativeCaps(String udid, String version) {
        CustomLogger.log.info("Generating desired capabilities for android native application.");

        DesiredCapabilities androidNativeCaps = new DesiredCapabilities();
        androidNativeCaps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        androidNativeCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);

        String APP_ACTIVITY = PropertiesReader.android.getValue("APP_ACTIVITY");
        String APP_PACKAGE = PropertiesReader.android.getValue("APP_PACKAGE");
        String ANDROID_APP_PATH = PropertiesReader.android.getValue("ANDROID_APP_PATH");

        if(!APP_ACTIVITY.isEmpty())
            androidNativeCaps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);

        if(!APP_PACKAGE.isEmpty())
            androidNativeCaps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);

        if(!ANDROID_APP_PATH.isEmpty())
            androidNativeCaps.setCapability(MobileCapabilityType.APP, ANDROID_APP_PATH);

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

        String BROWSER = PropertiesReader.android.getValue("BROWSER_NAME");

        if(BROWSER.isEmpty())
            BROWSER = "BROWSER";

        androidWebCaps.setCapability(MobileCapabilityType.BROWSER_NAME, BROWSER);

        androidWebCaps.setCapability(MobileCapabilityType.UDID, udid);
        androidWebCaps.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);

        CustomLogger.log.info("[Desired Capabilities] =>" +
                "DEVICE_NAME : Android Device" +
                "PLATFORM_VERSION : " + version +
                "BROWSER : " + BROWSER +
                "UDID : " + udid);

        return androidWebCaps;
    }

    public synchronized DesiredCapabilities iOSWebCaps(String name, String udid, String version) {
        CustomLogger.log.debug("Generating desired capabilities for iOS native application.");

        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);

        String BROWSER = PropertiesReader.iOS.getValue("BROWSER_NAME");

        if(BROWSER.isEmpty())
            BROWSER = "SAFARI";

        iOSCapabilities.setCapability(IOSMobileCapabilityType.BROWSER_NAME, BROWSER);

        if(udid.toCharArray().length == 40)
            iOSCapabilities.setCapability(MobileCapabilityType.UDID, udid);
        else
            iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, name);

        CustomLogger.log.info("[Desired Capabilities] =>" +
                " DEVICE_NAME | UDID : " + udid +
                " PLATFORM_VERSION : " + version +
                " BROWSER : " + BROWSER +
                " DEVICE_NAME : " + name);

        return iOSCapabilities;
    }


    public synchronized DesiredCapabilities iOSNativeCaps(String name, String udid, String version) {
        CustomLogger.log.debug("Generating desired capabilities for iOS native application.");

        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);

        String IOS_APP_PATH = PropertiesReader.iOS.getValue("IOS_APP_PATH");

        iOSCapabilities.setCapability(MobileCapabilityType.APP, IOS_APP_PATH);

        if(udid.toCharArray().length == 40)
            iOSCapabilities.setCapability(MobileCapabilityType.UDID, udid);
        else
            iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, name);

        CustomLogger.log.info("[Desired Capabilities] =>" +
                " DEVICE_NAME | UDID : " + udid +
                " PLATFORM_VERSION : " + version +
                " APP : " + IOS_APP_PATH +
                " DEVICE_NAME : " + name);

        return iOSCapabilities;
    }

    public AppiumDriver createDriver(URL host, String name, String udid, String version, MobilePlatform mobilePlatform) throws MalformedURLException {

        switch (mobilePlatform){

            case Web_Android:
                driver = new AndroidDriver(host, androidWebCaps(udid, version));
                break;

            case Web_iOS:
                driver = new IOSDriver(host, iOSWebCaps(name, udid, version));

            case iOS:
                driver = new IOSDriver(host, iOSNativeCaps(name, udid, version));
                break;

            case Android:
                driver = new AndroidDriver(host, androidNativeCaps(udid, version));
                break;

        }

        return driver;
    }
}
