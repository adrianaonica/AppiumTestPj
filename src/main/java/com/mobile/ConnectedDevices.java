package com.mobile;

import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.os.android.ADB;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.Instruments;
import com.mobile.os.iOS.iOSDevice;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.ArrayList;

public class ConnectedDevices {

    private static ConcurrentHashSet<AndroidDevice> androidDevices = new ConcurrentHashSet<>();
    private static ConcurrentHashSet<iOSDevice> iOSDevices = new ConcurrentHashSet<>();
    private static ConcurrentHashSet<iOSDevice> iOSimulators = new ConcurrentHashSet<>();
    private static ConcurrentHashSet<iOSDevice> iOSRealDevicesAndSimulators = new ConcurrentHashSet<>();

    public static ConcurrentHashSet<AndroidDevice> getConnectedAndroidDevices(){
        ArrayList<String> listOfAndroidDevices;
        AndroidDevice device;

        listOfAndroidDevices = ADB.getConnectedDevices();

        for(String udid : listOfAndroidDevices){
            ADB adb = new ADB(udid);
            String version = adb.getAndroidVersionAsString();
            String name = adb.getDeviceName();
            boolean isAvailable = true;
            boolean isSimulator = true;
            device = new AndroidDevice(name, version, udid, isAvailable, isSimulator);
            androidDevices.add(device);
        }
        CustomLogger.log.debug(androidDevices.toString());

        return androidDevices;
    }

    public static ConcurrentHashSet<iOSDevice> getConnectediOSDevicesOrSimulators(){
        ArrayList<String> lisOfiOSDevices;
        iOSDevice device;

        lisOfiOSDevices = Instruments.getConnectedDevices();

        for(String line : lisOfiOSDevices){
            String name = line.substring(0, line.indexOf("(")-1).trim();
            String version = line.substring(line.indexOf("(")+1, line.indexOf(")")).trim();
            String udid = line.substring(line.indexOf("[")+1, line.indexOf("]")).trim();
            boolean isAvailable = true;
            boolean isSimulator = false;
            if(line.contains("Simulator"))  isSimulator = true;

            device = new iOSDevice(name, version, udid, isAvailable, isSimulator);
            if(isSimulator)
                iOSimulators.add(device);
            else
                iOSDevices.add(device);

        }
        CustomLogger.log.debug(iOSDevices.toString());
        String deviceType =PropertiesReader.config.getValue("DEVICE");
        if(deviceType.equalsIgnoreCase("iOS"))
            return iOSDevices;
        else if(deviceType.equalsIgnoreCase("iOS_Simulator"))
            return iOSimulators;
        else if(deviceType.equalsIgnoreCase("Any")){
            iOSRealDevicesAndSimulators.addAll(iOSDevices);
            iOSRealDevicesAndSimulators.addAll(iOSimulators);
            return iOSRealDevicesAndSimulators;
        }else
            throw new RuntimeException("Invalid value for property 'DEVICE. " +
                    "Supported values are => 1) iOS_Simulator " +
                                            "2) iOS " +
                                            "3) Android " +
                                            "4) Any ");

    }

}
