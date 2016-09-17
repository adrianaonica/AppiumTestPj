package com.applications.speedtest.pages.results;

import com.applications.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class ResultsPage extends BasePage {

    public ResultsPageObjects resultsPageObjects = new ResultsPageObjects();

    public ResultsPage(AppiumDriver<MobileElement> driver){
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), resultsPageObjects);
    }

}
