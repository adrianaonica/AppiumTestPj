package com.mobile.appium;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.ConnectedDevices;
import com.mobile.MobileDevice;
import com.mobile.MobilePlatform;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.iOSDevice;
import io.appium.java_client.AppiumDriver;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class AppiumManager {

    private AppiumDriver driver = null;
    private AppiumService appiumService = new AppiumService();
    private iOSDevice iOSDevice = null;
    private AndroidDevice androidDevice;
    private MobileDevice currentDevice;
    private AppiumDriverFactory appiumDriverFactory = new AppiumDriverFactory();

    private static String DEVICE;

//    ConcurrentHashSet will contain all the list of devices
//    This is shared with all the threads.
    public static ConcurrentHashSet<iOSDevice> iOSDevicesHashSet = new ConcurrentHashSet<>();
    public static ConcurrentHashSet<AndroidDevice> androidDevicesHashSet = new ConcurrentHashSet<>();
    public static ConcurrentHashSet<iOSDevice> iOSSimulatorsHashSet = new ConcurrentHashSet<>();

    public static synchronized void storeAllConnectedDevices(){
        ConcurrentHashSet<iOSDevice> iOSDevicesAndSimulators = new ConcurrentHashSet<>();
        DEVICE = PropertiesReader.config.getValue("DEVICE");
        if(DEVICE.equalsIgnoreCase("Any")) {
            androidDevicesHashSet = ConnectedDevices.getConnectedAndroidDevices();
            iOSDevicesAndSimulators = ConnectedDevices.getConnectediOSDevicesOrSimulators();
        }
        else if(DEVICE.contains("iOS"))  iOSDevicesAndSimulators = ConnectedDevices.getConnectediOSDevicesOrSimulators();
        else if(DEVICE.equalsIgnoreCase("Android"))   androidDevicesHashSet = ConnectedDevices.getConnectedAndroidDevices();
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

        CustomLogger.log.debug("Freed device : " + currentDevice.getName() +
                " & killed appium service hosted at " + appiumService.getHost());
    }

    public static void freeDevice(MobileDevice currentDevice) {
        CustomLogger.log.debug("Setting isAvailable flag to true for device : " + currentDevice.getName());
        currentDevice.setAvailable(true);
    }

    public synchronized AppiumDriver createDriverInstanceForiOS() throws MalformedURLException {
        if(PropertiesReader.config.getValue("AUT").equalsIgnoreCase("Web"))
            driver = appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.Web_iOS);
        else
            driver = appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.iOS);

        return driver;
    }

    public synchronized AppiumDriver createDriverInstanceForAndroid() throws MalformedURLException {
        if(PropertiesReader.config.getValue("AUT").equalsIgnoreCase("Web"))
            driver = appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.Web_Android);
        else
            driver = appiumDriverFactory.createDriver(appiumService.getHost(),currentDevice,MobilePlatform.Android);

        return driver;

    }

    public synchronized void createDriverInstance() throws MalformedURLException {
        String deviceName = currentDevice.getName();
        String udid = currentDevice.getUdid();

        if(deviceName.contains("Apple") || deviceName.contains("iPhone") || deviceName.contains("iPad") || udid.length() == 40)
            driver = createDriverInstanceForiOS();
        else
            driver = createDriverInstanceForAndroid();

    }

//  Getters & Setters

    public AppiumDriver getDriver() {
        return driver;
    }

    public void setCurrentDevice(MobileDevice currentDevice) {
        this.currentDevice = currentDevice;
    }

    public MobileDevice getCurrentDevice() {
        return currentDevice;
    }

    public void setAppiumService(AppiumService appiumService) {
        this.appiumService = appiumService;
    }

    public AppiumService getAppiumService() {
        return appiumService;
    }


}
