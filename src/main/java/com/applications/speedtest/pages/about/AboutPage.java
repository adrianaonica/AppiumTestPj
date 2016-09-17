package com.applications.speedtest.pages.about;

import com.applications.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class AboutPage extends BasePage{

    private AboutPageObjects aboutPageObjects = new AboutPageObjects();

    public AboutPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), aboutPageObjects);
    }
}
