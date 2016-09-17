package com.applications.speedtest.pages.home;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class HomePageObjects {

    @AndroidFindBy(id = "org.zwanoo.android.speedtest:id/non_native_ad_btn_dismiss")
    public MobileElement addCloseButton;

    @AndroidFindBy(id = "org.zwanoo.android.speedtest:id/pingSpeed")
    public MobileElement pingSpeed;

    @AndroidFindBy(id = "org.zwanoo.android.speedtest:id/downloadSpeed")
    public MobileElement downloadSpeed;

    @AndroidFindBy(id = "org.zwanoo.android.speedtest:id/uploadSpeed")
    public MobileElement uploadSpeed;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PING\")")
    public MobileElement pingText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"DOWNLOAD\")")
    public MobileElement downloadText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"UPLOAD\")")
    public MobileElement uploadText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Begin Test\")")
    public MobileElement beginTestButton;

}
