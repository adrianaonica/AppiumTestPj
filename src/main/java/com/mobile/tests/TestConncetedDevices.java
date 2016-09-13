package com.mobile.tests;

import com.mobile.ConnectedDevices;
import com.mobile.os.android.ADB;
import com.mobile.os.android.AndroidDevice;
import com.mobile.os.iOS.Instruments;
import com.mobile.os.iOS.iOSDDevice;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.mobile.ConnectedDevices.androidDevices;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class TestConncetedDevices {

    @Test
    public void testConncetedAndroidDevices(){
        ArrayList<AndroidDevice> androidDevices = ConnectedDevices.getConnectedAndroidDevices();

        Assert.assertEquals(ADB.getConnectedDevices().size(), androidDevices.size());

        for (AndroidDevice device : androidDevices)
            System.out.println(device.toString());

    }

    @Test
    public void testConncetediOSDevices(){
        ArrayList<iOSDDevice> iOSDevices = ConnectedDevices.getConnectediOSDevices();

        Assert.assertEquals(Instruments.getConnectedDevices().size(), iOSDevices.size());

        for (iOSDDevice device : iOSDevices)
            System.out.println(device.toString());

    }

}
