package com.applications.PMC.mobileambulance.login;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class LoginPageObjects {

    @FindBy(xpath = "//img[contains(@src,'pmc-care-logo')]")
    public MobileElement pmcCareLogo;

    @FindBy(xpath = "//*[contains(text(),'Mobile Ambulance')]")
    public MobileElement appTitleMobileAmbulance;

    @FindBy(xpath = "//input[@type='radio'][@value='ROAD ENGINEER']")
    public MobileElement roadEngineerRadioBtn;

    @FindBy(xpath = "//input[@type='radio'][@value='CONTRACTOR']")
    public MobileElement contractorRadioBtn;

    @FindBy(name = "userName")
    public MobileElement userNameTxt;

    @FindBy(name = "password")
    public MobileElement passwordTxt;

    @FindBy(xpath = "//img[contains(@src,'login-icon')]")
    public MobileElement loginBtn;

    @FindBy(xpath = "//button[contains(text(),'English')]")
    public MobileElement lngEnglishBtn;

    @FindBy(xpath = "//button[contains(text(),'Marathi')]")
    public MobileElement lngMarathiBtn;

}
