package com.applications.speedtest.pages;

import com.applications.speedtest.pages.about.AboutPage;
import com.applications.speedtest.pages.begintest.BeginTestPage;
import com.applications.speedtest.pages.home.HomePage;
import com.applications.speedtest.pages.menu.Menu;
import com.applications.speedtest.pages.results.ResultsPage;
import com.applications.speedtest.pages.settings.SettingsPage;
import com.core.managers.DriverManager;
import io.appium.java_client.AppiumDriver;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class Speedtest {

    public AppiumDriver driver = DriverManager.getAppiumDriver();

    public BeginTestPage beginTestPage = new BeginTestPage(driver);

    public HomePage homePage = new HomePage(driver);

    public ResultsPage resultsPage = new ResultsPage(driver);

    public SettingsPage settingsPage = new SettingsPage(driver);

    public Menu menu = new Menu(driver);

    public AboutPage aboutPage = new AboutPage(driver);
}
