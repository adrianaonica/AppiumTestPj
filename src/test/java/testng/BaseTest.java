package testng;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.ConnectedDevices;
import com.mobile.MobileDevice;
import com.mobile.appium.AppiumManager;
import com.mobile.appium.AppiumService;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.iOSDevice;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Level;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.sun.tools.doclint.Entity.prop;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class BaseTest extends AppiumManager{


    @BeforeClass()
    public void beforeClass() throws Exception {

//      set log4j properties
        String usrDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        System.setProperty("my.log", usrDir + "/target/");
        CustomLogger.log.setLevel(Level.ALL);

//      Get all connected devices
        storeAllConnectedDevices();

//      Get next available device and start appium service.
        startAppiumService();

    }

    @AfterClass()
    public void afterClass() throws InterruptedException, IOException {

//      kill appium service and free device
        killAppiumServer();

        CustomLogger.log.debug("Freed device : " + currentDevice.getName() + " & killed appium service hosted at " + appiumService.getHost());
        CustomLogger.log.debug("Android devices status : " + androidDevicesHashSet.toString());
        CustomLogger.log.debug("iOS devices status : " + iOSDevicesHashSet.toString());
        CustomLogger.log.debug("iOS simulators status : " + iOSSimulatorsHashSet.toString());
    }


    @BeforeMethod()
    public void startApp(Method name) throws Exception {

       driver = getDriverInstance();
//
//        if(prop.getProperty("DEVICE").equalsIgnoreCase("iOS"))
//            driver = startAppiumServerInParallel(name.getName(), iosNative());
//        else
//            driver = startAppiumServerInParallel(name.getName(), androidNative());
//
//        startLogResults(name.getName());
    }

/*
    @AfterMethod()
    public void killServer(ITestResult result)
            throws InterruptedException, IOException {
        endLogTestResults(result);
        getDriver().quit();
        //deleteAppIOS("com.tesco.sample");
    }


    @AfterClass()
    public void afterClass() throws InterruptedException, IOException {
        System.out.println("After Class" + Thread.currentThread().getId());
        killAppiumServer();
    }

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }


    public void getUserName() {
        String[] crds = Thread.currentThread().getName().toString().split("_");
        System.out.println(crds[1]);
        JSONObject user = jSonParser.getUserData(Integer.parseInt(crds[1]));
        System.out.println(user.get("userName"));
    }

    public DesiredCapabilities iosNative() {
        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        System.out.println("Setting iOS Desired Capabilities:");
        iOSCapabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("IOS_APP_PATH"));
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        if(prop.getProperty("mode").equalsIgnoreCase("simulator")){
            iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
            iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device_udid);
        }
        else {
            iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
            iOSCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        }
        return iOSCapabilities;
    }

    public synchronized DesiredCapabilities androidNative() {
        System.out.println("Setting Android Desired Capabilities:");
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");

        String output = null;
        try {
            output = commandPrompt.runCommand("adb -s "+device_udid+" shell getprop ro.build.version.release");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(output.length() == 3) output+=".0";


        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, output);
        androidCapabilities.setCapability("browserName", "");
        androidCapabilities
                .setCapability(MobileCapabilityType.APP, prop.getProperty("ANDROID_APP_PATH"));
        androidCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return androidCapabilities;

*/

}
