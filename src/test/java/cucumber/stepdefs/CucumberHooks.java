package cucumber.stepdefs;

import com.core.cli.CommandPrompt;
import com.core.logger.CustomLogger;
import com.mobile.MobileDevice;
import com.mobile.appium.AppiumManager;
import com.mobile.appium.AppiumService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class CucumberHooks extends AppiumManager{
    private static MobileDevice currentDevice;
    private static AppiumService appiumService;

    private static boolean globalMode = false;

    @Before
    public void beforeHook() throws Exception {

        if (globalMode == false)
            beforeAll();

        setCurrentDevice(currentDevice);
        setAppiumService(appiumService);
        createDriverInstance();

    }

    @After
    public void afterHook(){
        getDriver().quit();
    }

    public void beforeAll() throws Exception {

        globalMode = true;

//      Configure logger properties
        CustomLogger.configure();

//      Get all connected devices
        storeAllConnectedDevices();

//      Get next available device and start appium service.
        startAppiumService();
        currentDevice = getCurrentDevice();
        appiumService = getAppiumService();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                afterAll();
            }
        });
    }

    public void afterAll(){
        try {
//          kill appium service and free device
            killAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

//      Delete tmp files/dir from target folder
        CommandPrompt.run("rm -rd tmp*");

        CustomLogger.log.debug("Android devices status : " + androidDevicesHashSet.toString());
        CustomLogger.log.debug("iOS devices status : " + iOSDevicesHashSet.toString());
        CustomLogger.log.debug("iOS simulators status : " + iOSSimulatorsHashSet.toString());
    }

}
