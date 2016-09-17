package cucumber.stepdefs;

import com.core.managers.DriverManager;
import com.mobile.appium.AppiumDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class BaseStepDefinition {

    public AppiumDriver driver;

    public AppiumDriver<MobileElement> getAppiumDriver(){
        driver = AppiumDriverFactory.getDriver();
        return driver;
    }


}
