package com.mobile;

import com.core.logger.CustomLogger;
import com.mobile.os.android.ADB;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.Instruments;
import com.mobile.os.iOS.iOSDDevice;

import java.util.ArrayList;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class ConnectedDevices {

    private static ArrayList<AndroidDevice> androidDevices = new ArrayList<>();
    private static ArrayList<iOSDDevice> iOSDevices = new ArrayList<>();

    public static ArrayList<AndroidDevice> getConnectedAndroidDevices(){
        ArrayList<String> listOfAndroidDevices;
        AndroidDevice device;

        listOfAndroidDevices = ADB.getConnectedDevices();

        for(String udid : listOfAndroidDevices){
            ADB adb = new ADB(udid);
            String version = adb.getAndroidVersionAsString();
            String name = adb.getDeviceName();
            boolean isAvailable = true;
            device = new AndroidDevice(name, version, udid, isAvailable);
            androidDevices.add(device);
        }
        CustomLogger.log.debug(androidDevices.toString());

        return androidDevices;
    }

    public static ArrayList<iOSDDevice> getConnectediOSDevices(){
        ArrayList<String> lisOfiOSDevices;
        iOSDDevice device;

        lisOfiOSDevices = Instruments.getConnectedDevices();

        for(String line : lisOfiOSDevices){
            String name = line.substring(0, line.indexOf("(")-1).trim();
            String version = line.substring(line.indexOf("(")+1, line.indexOf(")")).trim();
            String udid = line.substring(line.indexOf("[")+1, line.indexOf("]")).trim();
            boolean isAvailable = true;
            boolean isSimulator = false;
            if(line.contains("Simulator"))  isSimulator = true;

            device = new iOSDDevice(name, version, udid, isAvailable, isSimulator);
            iOSDevices.add(device);

        }
        CustomLogger.log.debug(iOSDevices.toString());

        return iOSDevices;

    }


}
