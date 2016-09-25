package com.applications.PMC.mobileambulance.login;

import com.applications.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class LoginPage extends BasePage{
    private LoginPageObjects loginPageObjects = new LoginPageObjects();

    public LoginPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), loginPageObjects);
    }

    public void login(){
        loginPageObjects.userNameTxt.clear();
        loginPageObjects.userNameTxt.sendKeys("Pritam");
        loginPageObjects.passwordTxt.clear();
        loginPageObjects.passwordTxt.sendKeys("Pritam");
        loginPageObjects.loginBtn.click();
    }

    public void verifyAllElementsPresentOnLandingPage(){
        Assert.assertTrue(loginPageObjects.pmcCareLogo.isDisplayed());
        Assert.assertTrue(loginPageObjects.enAppTitleMobileAmbulance.isDisplayed());
        Assert.assertTrue(loginPageObjects.roadEngineerRadioBtn.isDisplayed());
        Assert.assertTrue(loginPageObjects.contractorRadioBtn.isDisplayed());
        Assert.assertTrue(loginPageObjects.userNameTxt.isDisplayed());
        Assert.assertTrue(loginPageObjects.passwordTxt.isDisplayed());
        Assert.assertTrue(loginPageObjects.loginBtn.isDisplayed());
        Assert.assertTrue(loginPageObjects.lngEnglishBtn.isDisplayed());
        Assert.assertTrue(loginPageObjects.lngMarathiBtn.isDisplayed());
    }

    public void waitToLoad(){
        waitForPageToLoad(loginPageObjects.loginBtn);
    }
}
