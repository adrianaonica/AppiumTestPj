package com.applications.speedtest.pages;
import com.applications.speedtest.pages.begintest.BeginTestPage;
import com.applications.speedtest.pages.home.HomePage;
import com.applications.speedtest.pages.menu.Menu;
import com.applications.speedtest.pages.results.ResultsPage;
import com.applications.speedtest.pages.settings.SettingsPage;
import com.core.managers.DriverManager;
import io.appium.java_client.AppiumDriver;


public class Speedtest {

    public AppiumDriver driver = DriverManager.getAppiumDriver();

    public BeginTestPage beginTestPage = new BeginTestPage(driver);

    public HomePage homePage = new HomePage(driver);

  }
