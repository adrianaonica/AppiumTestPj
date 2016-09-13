package com.mobile.appium;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.IOSServerFlag;
import java.io.File;
import java.net.URL;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class AppiumService {

    AvailablePort port = new AvailablePort();
    public AppiumDriverLocalService appiumDriverLocalService;

    public AppiumServiceBuilder startAppiumForAndroidAndiOSSimulator(String deviceID)
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

/*

    public AppiumServiceBuilder startAppiumForiOSSimulators(String simulator, String methodName) throws Exception {

        System.out.println("**********************************************************************\n");
        System.out.println("Starting Appium Server "  + "\n");
        System.out.println("**********************************************************************\n");

        CustomLogger.log.info("Building Appium Service for iOS Simulator.");

        String logFileLocation = System.getProperty("user.dir") + "/target/appiumlogs/" + simulator
                .replaceAll("\\W", "_") + "__" + methodName + ".txt";

        String tempDir = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/" + "tmp_" + port;

        int port = this.port.getPort();
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder()
                        .withAppiumJS(new File(PropertiesReader.config.getValue("APPIUM_JS_PATH")))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withLogFile(new File(logFileLocation))
                        .withArgument(GeneralServerFlag.TEMP_DIRECTORY, tempDir)
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE).usingPort(port);
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        return builder;
    }
*/

    public AppiumServiceBuilder startAppiumForiOSDevices(String deviceID, String webKitPort) throws Exception {
        System.out.println("**********************************************************************");
        System.out.println("****** Starting Appium Service to handle IOS :: " + deviceID + " *****");
        System.out.println("**********************************************************************");

        CustomLogger.log.debug("Building Appium service for iOS devices.");

        String tmpDir = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/" + "tmp_" + port;

        String logFileLocation = System.getProperty("user.dir") + "/target/appiumlogs/" +
                deviceID.replaceAll("\\W", "_") + ".txt";

        File APPIUM_JS_FILE = new File(PropertiesReader.config.getValue("APPIUM_JS_PATH"));

        int port = this.port.getPort();
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder()
                        .withAppiumJS(APPIUM_JS_FILE)
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withLogFile(new File(logFileLocation))
                        .withArgument(IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT, webKitPort)
                        .withArgument(GeneralServerFlag.TEMP_DIRECTORY, tmpDir)
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .usingPort(port);
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();

        CustomLogger.log.debug("Appium service started with following arguments => " +
                " APPIUM_JS_FILE : " + APPIUM_JS_FILE.getAbsolutePath() +
                " LOG_LEVEL : info" +
                " LOG LOCATION : " + logFileLocation +
                " WEBKIT_DEBUG_PROXY_PORT : " + webKitPort +
                " TEMP_DIRECTORY : " + tmpDir +
                " SESSION_OVERRIDE" +
                " PORT : " + port);

        return builder;

    }

    public URL getHost() {
        return appiumDriverLocalService.getUrl();
    }

    public void destroyAppiumService() {
        if(appiumDriverLocalService.isRunning()) {
            appiumDriverLocalService.stop();
            CustomLogger.log.debug("Destroyed Appium server.");
        }
    }
}
