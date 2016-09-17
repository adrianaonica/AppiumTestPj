package com.applications.speedtest.pages.settings;

import com.applications.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class SettingsPage extends BasePage {

    public SettingPageObjects settingPageObjects = new SettingPageObjects();

    public SettingsPage(AppiumDriver<MobileElement> driver){
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), settingPageObjects);
    }

}
