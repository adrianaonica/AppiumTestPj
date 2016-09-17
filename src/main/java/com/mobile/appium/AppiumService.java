package com.mobile.appium;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.os.iOS.iOSDevice;
import com.mobile.os.iOS.iOSWebKitDebugProxy;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.IOSServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;

import java.io.File;
import java.net.URL;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class AppiumService {

    AvailablePort port = new AvailablePort();
    public AppiumDriverLocalService appiumDriverLocalService;
    String webKitPort = "";

    public AppiumServiceBuilder startAppiumForAndroidDevice(String deviceID)
            throws Exception {
        System.out.println("**************************************************************************");
        System.out.println("** Starting Appium Service to handle Android Device:: " + deviceID + " ***");
        System.out.println("**************************************************************************");

        CustomLogger.log.info("Building Appium Service for android");

        int port = this.port.getPort();
        int chromePort = this.port.getPort();
        int bootstrapPort = this.port.getPort();
        int selendroidPort = this.port.getPort();

        String logFileLocation = System.getProperty("user.dir") + "/target/appiumlogs/" + deviceID
                .replaceAll("\\W", "_") + ".txt";

        String tempDir = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/" + "tmp_" + port;

        AppiumServiceBuilder builder =
                new AppiumServiceBuilder()
                        .withAppiumJS(new File((PropertiesReader.config.getValue("APPIUM_JS_PATH"))))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withLogFile(new File(logFileLocation))
                        .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, Integer.toString(chromePort))
                        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, Integer.toString(bootstrapPort))
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
                        .withArgument(GeneralServerFlag.TEMP_DIRECTORY, tempDir)
                        .withArgument(AndroidServerFlag.SELENDROID_PORT, Integer.toString(selendroidPort))
                        .usingPort(port);

        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();

        CustomLogger.log.debug("Appium service started with the following arguments =>" +
                "APPIUM_JS : " + PropertiesReader.config.getValue("APPIUM_JS_PATH") +
                "LOG_LEVEL : info" +
                "LOG LOCATION : " + logFileLocation +
                "CHROME_PORT : " + chromePort +
                "BOOTSTRAP_PORT : " + bootstrapPort +
                "SESSION_OVERRIDE" +
                "SUPPRESS_ADB_KILL_SERVER" +
                "TEMP_DIRECTORY" + tempDir +
                "SELENDROID_PORT : " + selendroidPort);

        return builder;
    }

    public AppiumServiceBuilder startAppiumForiOSDevice(iOSDevice iOSDevice) throws Exception {
        String deviceName = iOSDevice.getName();


        System.out.println("**********************************************************************");
        System.out.println("****** Starting Appium Service to handle IOS :: " + deviceName + " *****");
        System.out.println("**********************************************************************");

        CustomLogger.log.debug("Building Appium service for iOS devices.");

        String tmpDir = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/" + "tmp_" + port;

        String logFileLocation = System.getProperty("user.dir") + "/target/appiumlogs/" +
                deviceName.replaceAll("\\W", "_") + ".txt";

        File APPIUM_JS_FILE = new File(PropertiesReader.config.getValue("APPIUM_JS_PATH"));


        int appiumPort = this.port.getPort();


        AppiumServiceBuilder builder =
                new AppiumServiceBuilder()
                        .withAppiumJS(APPIUM_JS_FILE)
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withLogFile(new File(logFileLocation))
//                        .withArgument(IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT, webKitPort)
                        .withArgument(GeneralServerFlag.TEMP_DIRECTORY, tmpDir)
                        .withArgument(nativeInstruments)
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .usingPort(appiumPort);

//      Start webkit proxy only for real iOS devices
        if(iOSDevice.getUdid().length() == 40) {
            webKitPort = Integer.toString(this.port.getPort());
            iOSWebKitDebugProxy.start(iOSDevice, webKitPort);
            builder.withArgument(IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT, webKitPort);
        }

        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();

        CustomLogger.log.debug("Appium service started with following arguments => " +
                " APPIUM_JS_FILE : " + APPIUM_JS_FILE.getAbsolutePath() +
                " LOG_LEVEL : info" +
                " LOG LOCATION : " + logFileLocation +
                " WEBKIT_DEBUG_PROXY_PORT : " + webKitPort +
                " TEMP_DIRECTORY : " + tmpDir +
                " SESSION_OVERRIDE" +
                " PORT : " + appiumPort);

        return builder;

    }

    ServerArgument nativeInstruments = new ServerArgument() {
        @Override public String getArgument() {
            return "--native-instruments-lib";
        }
    };

    public URL getHost() {
        return appiumDriverLocalService.getUrl();
    }

    public void destroyAppiumService() {
        if(appiumDriverLocalService.isRunning()) {
            appiumDriverLocalService.stop();
            CustomLogger.log.debug("Destroyed Appium server.");
        }

        if(!webKitPort.isEmpty())
            iOSWebKitDebugProxy.kill(webKitPort);
    }
}
