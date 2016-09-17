package com.applications.speedtest.pages.menu;

import com.applications.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class Menu extends BasePage {

    private MenuObjects menuObjects = new MenuObjects();

    public Menu(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), menuObjects);
    }
}
