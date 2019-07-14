package com.applications.speedtest.pages.begintest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;


public class BeginTestPageObjects {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Begin Test\")")
    public MobileElement beginTestButton;


}
