package cucumber;

import com.core.cli.CommandPrompt;
import com.core.logger.CustomLogger;
import com.mobile.appium.AppiumManager;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class CucumberHooks extends AppiumManager{

    @Before
    public void beforeHook() throws Exception {

        if (!appiumService.appiumDriverLocalService.isRunning())
            beforeAll();

        driver = getDriverInstance();
    }

    @After
    public void afterHook(){
        driver.close();
        driver.quit();
    }

    public void beforeAll() throws Exception {
        //          Get all connected devices
        storeAllConnectedDevices();

//          Get next available device and start appium service.
        startAppiumService();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {

                try {
//                  kill appium service and free device
                    killAppiumServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//              Delete tmp files/dir from target folder
                CommandPrompt.run("rm -rd tmp*");

                CustomLogger.log.debug("Freed device : " + currentDevice.getName() + " & killed appium service hosted at " + appiumService.getHost());
                CustomLogger.log.debug("Android devices status : " + androidDevicesHashSet.toString());
                CustomLogger.log.debug("iOS devices status : " + iOSDevicesHashSet.toString());
                CustomLogger.log.debug("iOS simulators status : " + iOSSimulatorsHashSet.toString());
            }
        });
    }


}
