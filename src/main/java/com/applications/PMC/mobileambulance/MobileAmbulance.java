package com.applications.PMC.mobileambulance;


import com.applications.PMC.mobileambulance.login.LoginPage;
import com.mobile.appium.AppiumManager;
import io.appium.java_client.AppiumDriver;

import java.util.Set;

/**
 * Created by pritamkadam on 24/09/16.
 */
public class MobileAmbulance {

    public AppiumDriver driver = AppiumManager.getDriver();
    public LoginPage loginPage = new LoginPage(driver);

    public MobileAmbulance(){
        Set<String> contextNames = driver.getContextHandles();

        contextNames.forEach(System.out::println);

        driver.context((String) contextNames.toArray()[1]);

        loginPage.waitToLoad();

    }

}
