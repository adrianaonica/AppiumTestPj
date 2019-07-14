package com.applications.speedtest.pages.home;

import com.applications.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage{


    private HomePageObjects homePageObjects = new HomePageObjects();

    public HomePage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), homePageObjects);
    }

    public HomePage waitToLoad(){
        waitForPageToLoad(homePageObjects.addCloseButton);
        homePageObjects.addCloseButton.click();
        return this;
    }

    public boolean isPingTextPresent(){
        return homePageObjects.pingText.isDisplayed();
    }

    public boolean isDownloadTextPresent(){
        return homePageObjects.downloadText.isDisplayed();
    }

    public boolean isUploadTextPresent(){
        return homePageObjects.uploadText.isDisplayed();
    }

    public String getPingSpeed(){
        return homePageObjects.pingSpeed.getText();
    }

    public String getDownloadSpeed(){
        return homePageObjects.downloadSpeed.getText();
    }

    public String getUploadSpeed(){
        return homePageObjects.uploadSpeed.getText();
    }
}
