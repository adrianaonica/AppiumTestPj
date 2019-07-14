package com.applications.speedtest.pages.begintest;

import com.applications.BasePage;
import com.applications.speedtest.pages.home.HomePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;


public class BeginTestPage extends BasePage{


    private BeginTestPageObjects beginTestPageObjects = new BeginTestPageObjects();

    public BeginTestPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), beginTestPageObjects);
    }

    public HomePage tapOnBeginTest() {
        beginTestPageObjects.beginTestButton.click();
        return new HomePage(driver).waitToLoad();
    }

    public BeginTestPage waitToLoad(){
        waitForPageToLoad(beginTestPageObjects.beginTestButton);
        return new BeginTestPage(driver);
    }

}
