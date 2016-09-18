package testng;

import com.core.cli.CommandPrompt;
import com.core.logger.CustomLogger;
import com.mobile.appium.AppiumManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class BaseTest extends AppiumManager{

    @BeforeClass()
    public void beforeClass() throws Exception {

//      Configure logger properties
        CustomLogger.configure();

//      Get all connected devices
        storeAllConnectedDevices();

//      Get next available device and start appium service.
        startAppiumService();

    }

    @AfterClass()
    public void afterClass() throws InterruptedException, IOException {

//      kill appium service and free device
        killAppiumServer();

//      Delete tmp files/dir from target folder
        CommandPrompt.run("rm -rd tmp*");

        CustomLogger.log.debug("Android devices status : " + androidDevicesHashSet.toString());
        CustomLogger.log.debug("iOS devices status : " + iOSDevicesHashSet.toString());
        CustomLogger.log.debug("iOS simulators status : " + iOSSimulatorsHashSet.toString());
    }


    @BeforeMethod()
    public void setupDriver(Method name) throws Exception {
       createDriverInstance();
    }


    @AfterMethod()
    public void killDriverInstance(ITestResult result)
    {
        getDriver().quit();
    }

}
