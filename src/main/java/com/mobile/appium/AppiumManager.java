package com.mobile.appium;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.ConnectedDevices;
import com.mobile.MobileDevice;
import com.mobile.MobilePlatform;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.iOSDevice;
import com.mobile.os.iOS.iOSWebKitDebugProxy;
import io.appium.java_client.AppiumDriver;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class AppiumManager extends ConnectedDevices{

    public static AppiumDriver driver = null;
    public AppiumService appiumService = new AppiumService();
    public iOSDevice iOSDevice = null;
    public AndroidDevice androidDevice;
    public MobileDevice currentDevice;
    public AppiumDriverFactory appiumDriverFactory = new AppiumDriverFactory();
    private String webKitPort = "";

    public static ConcurrentHashSet<iOSDevice> iOSDevicesHashSet = new ConcurrentHashSet<>();
    public static ConcurrentHashSet<AndroidDevice> androidDevicesHashSet = new ConcurrentHashSet<>();
    public static ConcurrentHashSet<iOSDevice> iOSSimulatorsHashSet = new ConcurrentHashSet<>();

    public static synchronized void storeAllConnectedDevices(){
        ConcurrentHashSet<iOSDevice> iOSDevicesAndSimulators = new ConcurrentHashSet<>();
        String deviceType = PropertiesReader.config.getValue("DEVICE");

        if(deviceType.equalsIgnoreCase("Any")) {
            androidDevicesHashSet = ConnectedDevices.getConnectedAndroidDevices();
            iOSDevicesAndSimulators = ConnectedDevices.getConnectediOSDevicesOrSimulators();
        }
        else if(deviceType.contains("iOS"))  iOSDevicesAndSimulators = ConnectedDevices.getConnectediOSDevicesOrSimulators();
        else if(deviceType.equalsIgnoreCase("Android"))   androidDevicesHashSet = ConnectedDevices.getConnectedAndroidDevices();
        else {
            CustomLogger.log.error("Invalid value for PLATFORM " +
                    "Supported values are => 1) Android " +
                                            "2) iOS " +
                                            "3) iOS_Simulator " +
                                            "4) Any");
            throw new RuntimeException("Invalid value for property \"PLATFORM\" provided.");
        }

//      Extract out iOS simulators & iOS real devices
        if(!iOSDevicesAndSimulators.isEmpty()){
            for(iOSDevice iOSDevice : iOSDevicesAndSimulators){
                if(iOSDevice.isSimulator()) iOSSimulatorsHashSet.add(iOSDevice);
                else    iOSDevicesHashSet.add(iOSDevice);
            }
        }

    }

    public static synchronized iOSDevice getNextAvailableiOSDevice() {
        for(iOSDevice iOSDevice : iOSDevicesHashSet){
            if(iOSDevice.isAvailable()) {
                iOSDevice.setAvailable(false);
                return iOSDevice;
            }
        }

        CustomLogger.log.warn("Currently there are no iOS devices available. Searching for simulator..");
        iOSDevice iOSSimulator = getNextAvailableiOSimulator();
        return iOSSimulator;
    }

//    As xcode support only one iOS simulator to be launched at one time,
//    Here getting random iOS available simulator
//    And marking rest of the simulators as false for execution.
    public static synchronized iOSDevice getNextAvailableiOSimulator() {
        Random random = new Random();
        int number = random.nextInt(iOSSimulatorsHashSet.size() + 1);
        int i = 0;

        for(iOSDevice iOSDevice : iOSSimulatorsHashSet){

            if(i < number) {
                i++;
                continue;
            }
            if(iOSDevice.isAvailable()) {
                iOSDevice.setAvailable(false);
                for(iOSDevice iOSDevice1 : iOSSimulatorsHashSet){
                    iOSDevice1.setAvailable(false);
                }
                return iOSDevice;
            }
        }
        CustomLogger.log.warn("Currently there are no iOS simulators available.");
        return null;
    }

    public static synchronized AndroidDevice getNextAvailableAndroidDevice() {
        for(AndroidDevice androidDevice : androidDevicesHashSet){
            if(androidDevice.isAvailable()) {
                androidDevice.setAvailable(false);
                return androidDevice;
            }
        }
        CustomLogger.log.warn("Currently there are no android devices available.");
        return null;
    }

    public synchronized void startAppiumService() throws Exception {

//      Get next available device and start appium server.
        String DEVICE = PropertiesReader.config.getValue("DEVICE");

        if(DEVICE.equalsIgnoreCase("iOS_Simulator") || DEVICE.equalsIgnoreCase("iOS")) {
            if (DEVICE.equalsIgnoreCase("iOS_Simulator")) {
                iOSDevice = getNextAvailableiOSimulator();
            }else if (DEVICE.equalsIgnoreCase("iOS")) {
                iOSDevice = getNextAvailableiOSDevice();
                if(iOSDevice==null)
                    iOSDevice = getNextAvailableiOSimulator();
            }
            if(iOSDevice == null){
                CustomLogger.log.debug("iOS device not found. Searching for available android device.");
                androidDevice = getNextAvailableAndroidDevice();
                if(androidDevice == null){
                    CustomLogger.log.error("No device available to start execution.");
                    throw new RuntimeException("No device found.");
                }
                appiumService.startAppiumForAndroidDevice(androidDevice.getName());
                currentDevice = androidDevice;
            }else {
                appiumService.startAppiumForiOSDevice(iOSDevice);
                currentDevice = iOSDevice;
            }
        }else if(DEVICE.equalsIgnoreCase("Android")){
            androidDevice = getNextAvailableAndroidDevice();
            if(androidDevice == null){
                CustomLogger.log.error("No device  available to start execution.");
                throw new RuntimeException("No device found.");
            }
            appiumService.startAppiumForAndroidDevice(androidDevice.getName());
            currentDevice = androidDevice;
        }else if(DEVICE.equalsIgnoreCase("Any")){
            Random random = new Random();
            int number = random.nextInt(3) +1;
            switch (number){
                case 1 : iOSDevice = getNextAvailableiOSimulator();
                    appiumService.startAppiumForiOSDevice(iOSDevice);
                    currentDevice = iOSDevice;
                    break;
                case 2 : androidDevice = getNextAvailableAndroidDevice();
                    appiumService.startAppiumForAndroidDevice(androidDevice.getName());
                    currentDevice = androidDevice;
                    break;
                case 3 : iOSDevice = getNextAvailableiOSDevice();
                    appiumService.startAppiumForiOSDevice(iOSDevice);
                    currentDevice = iOSDevice;
                    break;
            }
        }
    }

    public synchronized void killAppiumServer() throws InterruptedException, IOException {
        appiumService.destroyAppiumService();

        freeDevice(currentDevice);
    }

    public static void freeDevice(MobileDevice currentDevice) {
        CustomLogger.log.debug("Setting isAvailable flag to true for device : " + currentDevice.getName());
        currentDevice.setAvailable(true);
    }

    public synchronized AppiumDriver getDriverInstanceForiOS() throws MalformedURLException {
        if(PropertiesReader.config.getValue("AUT").equalsIgnoreCase("Web"))
            appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.Web_iOS);
        else
            appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.iOS);
        driver = appiumDriverFactory.getDriver();
        return driver;
    }

    public synchronized AppiumDriver getDriverInstanceForAndroid() throws MalformedURLException {
        if(PropertiesReader.config.getValue("AUT").equalsIgnoreCase("Web"))
            appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.Web_Android);
        else
            appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.Android);
        driver = appiumDriverFactory.getDriver();
        return driver;

    }

    public synchronized AppiumDriver getDriverInstance() throws MalformedURLException {
        String deviceName = currentDevice.getName();
        String udid = currentDevice.getUdid();

        if(deviceName.contains("Apple") || deviceName.contains("iPhone") || deviceName.contains("iPad") || udid.length() == 40)
            driver = getDriverInstanceForiOS();
        else
            driver = getDriverInstanceForAndroid();

        return driver;
    }
/*


    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName)
            throws Exception {
        ExtentTestManager.loadConfig();
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            setAuthorName(methodName);
        }
        Thread.sleep(3000);
        startingServerInstance();
        return driver;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName,
                                                                                DesiredCapabilities iosCaps, DesiredCapabilities androidCaps) throws Exception {
        ExtentTestManager.loadConfig();
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            setAuthorName(methodName);
        }
        Thread.sleep(3000);
        startingServerInstance(iosCaps, androidCaps);
        return driver;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName,
                                                                                DesiredCapabilities caps) throws Exception {
        ExtentTestManager.loadConfig();
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            setAuthorName(methodName);
        }
        startingServerInstance(caps);
        return driver;
    }

    public void setAuthorName(String methodName) throws NoSuchMethodException {
        String authorName;
        ArrayList<String> listeners = new ArrayList<>();
        if (getClass().getMethod(methodName).getAnnotation(Author.class) != null) {
            authorName = getClass().getMethod(methodName).getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = ExtentTestManager
                    .startTest(methodName.toString() + " ---- AuthorName:: " + authorName)
                    .assignCategory(category + "_" + device_udid.replaceAll("\\W", "_"),
                            "Author:" + listeners);
        } else {
            child = ExtentTestManager.startTest(methodName.toString())
                    .assignCategory(category + "_Pritam" );
        }
    }

    public void startingServerInstance() throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (iosDevice.checkiOSDevice(device_udid)) {
                    driver = new IOSDriver<>(appiumService.getAppiumUrl(), iosNative());
                } else if (!iosDevice.checkiOSDevice(device_udid)) {
                    driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidNative());
                }
            } else {
                driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidNative());
            }
        }
    }

    public void startingServerInstance(DesiredCapabilities caps) throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (prop.getProperty("DEVICE").equalsIgnoreCase("iOS")) {
                    driver = new IOSDriver<>(appiumService.getAppiumUrl(), caps);
                } else {
                    checkSelendroid(caps);
                    driver = new AndroidDriver<>(appiumService.getAppiumUrl(), caps);
                }
            } else {
                driver = new AndroidDriver<>(appiumService.getAppiumUrl(), caps);
            }
        }
    }

    public void startingServerInstance(DesiredCapabilities iosCaps, DesiredCapabilities androidCaps)
            throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (iosDevice.checkiOSDevice(device_udid)) {
                    driver = new IOSDriver<>(appiumService.getAppiumUrl(), iosCaps);
                } else if (!iosDevice.checkiOSDevice(device_udid)) {
                    checkSelendroid(androidCaps);
                    driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidCaps);
                }
            } else {
                checkSelendroid(androidCaps);
                driver = new AndroidDriver<>(appiumService.getAppiumUrl(), androidCaps);
            }
        }
    }

    public DesiredCapabilities checkSelendroid(DesiredCapabilities androidCaps) {
        int android_api = 0;
        try {
            String deviceAPI = androidDevice.getDevices().get(device_udid);
            android_api = Integer.parseInt(deviceAPI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Android API Level::" + android_api);
        if (android_api <= 16) {
            androidCaps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
        }
        return androidCaps;
    }

    public void startLogResults(String methodName)
            throws FileNotFoundException, FileNotFoundException {
        if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
            System.out.println("Starting ADB logs" + device_udid);
            logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            logFile = new File(System.getProperty("user.dir") + "/target/adblogs/" + device_udid
                    .replaceAll("\\W", "_") + "__" + methodName + ".txt");
            log_file_writer = new PrintWriter(logFile);
        }
    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {

        final String classNameCur = result.getName().split(" ")[2].substring(1);
        final Package[] packages = Package.getPackages();
        String className = null;
        for (final Package p : packages) {
            final String pack = p.getName();
            final String tentative = pack + "." + classNameCur;
            try {
                Class.forName(tentative);
            } catch (final ClassNotFoundException e) {
                continue;
            }
            className = tentative;
            break;
        }


        if (result.isSuccess()) {
            ExtentTestManager.getTest()
                    .log(LogStatus.PASS, result.getMethod().getMethodName(), "Pass");
            if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                        "ADBLogs:: <a href=" + CI_BASE_URI + "/target/adblogs/" + device_udid
                                .replaceAll("\\W", "_") + "__" + result.getMethod().getMethodName() + ".txt"
                                + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }

        }
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest()
                    .log(LogStatus.FAIL, result.getMethod().getMethodName(), result.getThrowable());
            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                System.out.println("im in");
                deviceModel = androidDevice.getDeviceModel(device_udid);
                //captureScreenshot(result.getMethod().getMethodName(), "android", className);
                captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                        result.getTestClass().getName());
            } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                deviceModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
                captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                        result.getTestClass().getName());
            }

            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                File framedImageAndroid = new File(
                        System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid
                                .replaceAll("\\W", "_") + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp + deviceModel
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.png");
                if (framedImageAndroid.exists()) {
                    ExtentTestManager.getTest()
                            .log(LogStatus.INFO, result.getMethod().getMethodName(),
                                    "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(
                                            CI_BASE_URI + "/target/screenshot/android/" + device_udid
                                                    .replaceAll("\\W", "_") + "/" + className + "/" + result
                                                    .getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                                    + deviceModel + "_failed_" + result.getMethod().getMethodName()
                                                    + "_framed.png"));
                } else {
                    ExtentTestManager.getTest()
                            .log(LogStatus.INFO, result.getMethod().getMethodName(),
                                    "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(
                                            CI_BASE_URI + "/target/screenshot/android/" + device_udid
                                                    .replaceAll("\\W", "_") + "/" + className + "/" + result
                                                    .getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                                    + deviceModel + "_" + result.getMethod().getMethodName()
                                                    + "_failed.png"));
                }


            }
            if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                File framedImageIOS = new File(
                        System.getProperty("user.dir") + "/target/screenshot/iOS/" + device_udid
                                .replaceAll("\\W", "_") + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp + deviceModel
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.png");
                if (framedImageIOS.exists()) {
                    ExtentTestManager.getTest()
                            .log(LogStatus.INFO, result.getMethod().getMethodName(),
                                    "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(
                                            CI_BASE_URI + "/target/screenshot/iOS/" + device_udid
                                                    .replaceAll("\\W", "_") + "/" + className + "/" + result
                                                    .getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                                    + deviceModel + "_failed_" + result.getMethod().getMethodName()
                                                    + "_framed.png"));
                } else {
                    ExtentTestManager.getTest()
                            .log(LogStatus.INFO, result.getMethod().getMethodName(),
                                    "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(
                                            CI_BASE_URI + "/target/screenshot/iOS/" + device_udid
                                                    .replaceAll("\\W", "_") + "/" + className + "/" + result
                                                    .getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                                    + deviceModel + "_" + result.getMethod().getMethodName()
                                                    + "_failed.png"));
                }

            }


            if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                        "ADBLogs:: <a href=" + CI_BASE_URI + "/target/adblogs/" + device_udid
                                .replaceAll("\\W", "_") + "__" + result.getMethod().getMethodName() + ".txt"
                                + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }

        }
        if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped");
        }
//        parentContext.get(Thread.currentThread().getId()).appendChild(child);
        ExtentManager.getInstance().flush();
    }

    public synchronized void killAppiumServer() throws InterruptedException, IOException {
        System.out.println(
                "**************ClosingAppiumSession****************" + Thread.currentThread().getId());
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            ExtentManager.getInstance().endTest(parent);
            ExtentManager.getInstance().flush();
        }
        appiumService.destroyAppiumNode();
        if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            if(!prop.getProperty("mode").equalsIgnoreCase("simulator"))
                iosDevice.destroyIOSWebKitProxy();
            else
                return;
        }
        freeDevice(device_udid);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public void waitForElement(By id, int time) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable((id)));
    }

    public void resetAppData() throws InterruptedException, IOException {
        androidDevice.clearAppData(device_udid, prop.getProperty("APP_PACKAGE"));
    }

    public void closeOpenApp() throws InterruptedException, IOException {
        androidDevice.closeRunningApp(device_udid, prop.getProperty("APP_PACKAGE"));
    }

    public void removeApkFromDevice(String app_package) throws Exception {
        androidDevice.removeApkFromDevices(device_udid, app_package);
    }


    public synchronized DesiredCapabilities androidNative() {
        System.out.println("Setting Android Desired Capabilities:");
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.X");
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                prop.getProperty("APP_ACTIVITY"));
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                prop.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability("browserName", "");
        checkSelendroid(androidCapabilities);
        androidCapabilities
                .setCapability(MobileCapabilityType.APP, prop.getProperty("ANDROID_APP_PATH"));
        androidCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return androidCapabilities;
    }

    public synchronized DesiredCapabilities androidWeb() {
        DesiredCapabilities androidWebCapabilities = new DesiredCapabilities();
        androidWebCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidWebCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.X");
        // If you want the tests on real device, make sure chrome browser is
        // installed
        androidWebCapabilities
                .setCapability(MobileCapabilityType.BROWSER_NAME, prop.getProperty("BROWSER_TYPE"));
        androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        return androidWebCapabilities;
    }

    public synchronized DesiredCapabilities iosNative() {
        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        System.out.println("Setting iOS Desired Capabilities:");
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
//        iOSCapabilities.setBrowserName(BrowserType.SAFARI);

        iOSCapabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("IOS_APP_PATH"));
//        iOSCapabilities
//            .setCapability(IOSMobileCapabilityType.BUNDLE_ID, prop.getProperty("BUNDLE_ID"));
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6s Simulator");
//        iOSCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return iOSCapabilities;
    }

    public void deleteAppIOS(String bundleID) throws InterruptedException, IOException {
        iosDevice.unInstallApp(device_udid, bundleID);
    }

    public void installAppIOS(String appPath) throws InterruptedException, IOException {
        iosDevice.installApp(device_udid, appPath);
    }

    public Boolean checkAppIsInstalled(String bundleID) throws InterruptedException, IOException {
        return iosDevice.checkIfAppIsInstalled(bundleID);
    }


    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    public void onTestStart(ITestResult result) {
        Object currentClass = result.getInstance();
        AppiumDriver<MobileElement> driver = ((AppiumParallelTest) currentClass).getDriver();
        SkipIf skip =
                result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(SkipIf.class);
        if (skip != null) {
            String info = skip.platform();
            if (driver.toString().split("\\(")[0].trim().toString().contains(info)) {
                System.out.println("skipping test");
                throw new SkipException("Skipped because property was set to :::" + info);
            }
        }
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public void captureScreenshot(String methodName, String device, String className)
            throws IOException, InterruptedException {
        String context = getDriver().getContext();
        boolean contextChanged = false;
        if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver") && !context
                .equals("NATIVE_APP")) {
            getDriver().context("NATIVE_APP");
            contextChanged = true;
        }
        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            getDriver().context(context);
        }
        FileUtils.copyFile(scrFile, new File(
                CI_BASE_URI + "/target/screenshot/" + device + "/" + device_udid.replaceAll("\\W", "_")
                        + "/" + className + "/" + methodName + "/" + deviceModel + "_" + methodName
                        + "_failed" + ".png"));
        Thread.sleep(3000);
    }

    public void logger(String message) {
        ExtentTestManager.logger(message);
    }

    public void captureScreenShot(String screenShotName, int status, String className,
                                  String methodName) throws IOException, InterruptedException {
        String context = getDriver().getContext();
        boolean contextChanged = false;
        if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver")
                && !context.equals("NATIVE_APP")) {
            getDriver().context("NATIVE_APP");
            contextChanged = true;
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            getDriver().context(context);
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
            String androidModel =
                    screenShotNameWithTimeStamp + androidDevice.getDeviceModel(device_udid);
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, androidModel,
                    "android");
        } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            String iosModel = screenShotNameWithTimeStamp + iosDevice
                    .getIOSDeviceProductTypeAndVersion(device_udid);
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, iosModel,
                    "iOS");
        }
    }

    public void captureScreenShot(String screenShotName, int status, String screenClassName)
            throws IOException, InterruptedException {
        captureScreenShot(screenShotName, status, screenClassName, screenShotName);
    }

    public void captureScreenShot(String screenShotName) throws InterruptedException, IOException {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        String className = new Exception().getStackTrace()[1].getClassName();
        captureScreenShot(screenShotName, 1, className, methodName);
    }

    public void screenShotAndFrame(String screenShotName, int status, File scrFile,
                                   String methodName, String className, String model, String platform) {
        String failedScreen =
                System.getProperty("user.dir") + "/target/screenshot/" + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_" + methodName + "_failed" + ".png";
        String capturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/" + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + screenShotName
                        + ".png";
        String framedCapturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/" + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + model + "_"
                        + screenShotName + "_results.png";
        String framedFailedScreen =
                System.getProperty("user.dir") + "/target/screenshot/" + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + model
                        + "_failed_" + methodName + "_framed.png";

        try {
            File framePath =
                    new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            if (status == ITestResult.FAILURE) {
                FileUtils.copyFile(scrFile, new File(failedScreen));
            } else {
                FileUtils.copyFile(scrFile, new File(capturedScreen));
            }

            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { //this line weeds out other directories/foldestartAppiumServerInParallelrs
                        System.out.println(files1[i]);

                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (model.toString().toLowerCase()
                                .contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                if (status == ITestResult.FAILURE) {
                                    String screenToFrame = failedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedFailedScreen);
                                    ExtentTestManager.logOutPut(framedFailedScreen,
                                            screenShotName.toUpperCase());
                                    File fileToDelete = new File(screenToFrame);
                                    fileToDelete.delete();
                                } else {
                                    String screenToFrame = capturedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedCapturedScreen);
                                    ExtentTestManager.logOutPut(framedCapturedScreen,
                                            screenShotName.toUpperCase());
                                    File fileToDelete = new File(screenToFrame);
                                    fileToDelete.delete();
                                }

                                break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IM4JavaException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }
*/


}
