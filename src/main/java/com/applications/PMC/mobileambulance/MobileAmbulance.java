package com.applications.PMC.mobileambulance;


import com.applications.PMC.mobileambulance.login.LoginPage;
import com.mobile.appium.AppiumManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by pritamkadam on 24/09/16.
 */
public class MobileAmbulance {

    public AppiumDriver driver = AppiumManager.getDriver();

    public MobileAmbulance(){
        Set<String> contextNames = driver.getContextHandles();

        contextNames.forEach(System.out::println);

        driver.context((String) contextNames.toArray()[1]);

        loginPage.waitToLoad();

    }


   public LoginPage loginPage = new LoginPage(driver);

}
