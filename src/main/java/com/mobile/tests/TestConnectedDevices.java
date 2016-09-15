package com.mobile.tests;

import com.mobile.ConnectedDevices;
import com.mobile.os.android.ADB;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.Instruments;
import com.mobile.os.iOS.iOSDevice;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class TestConnectedDevices {


    @Test
    public void testConnectedAndroidDevices(){
        ConcurrentHashSet<AndroidDevice> androidDevices = ConnectedDevices.getConnectedAndroidDevices();

        Assert.assertEquals(ADB.getConnectedDevices().size(), androidDevices.size());

       for (AndroidDevice device : androidDevices)
            System.out.println(device.toString());

    }

    @Test
    public void testConnectedIOSDevices(){
        ConcurrentHashSet<iOSDevice> iOSDevices = ConnectedDevices.getConnectediOSDevicesOrSimulators();

        Assert.assertEquals(Instruments.getConnectedDevices().size(), iOSDevices.size());

        for (iOSDevice device : iOSDevices)
            System.out.println(device.toString());

    }

}
