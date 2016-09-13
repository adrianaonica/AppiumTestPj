package com.mobile.os.iOS;

import com.core.cli.CommandPrompt;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class Instruments {

    public static ArrayList<String> getConnectedDevices(){
        ArrayList<String> devices = new ArrayList<>();

        String cmd = "instruments -s devices";
        String output = CommandPrompt.run(cmd);

        for (String line : output.split("\n")){
            if(line.contains("(")){
                devices.add(line);
            }
        }

        return devices;
    }

    @Test
    public void testConnectedDevices(){
        ArrayList<String> devices = getConnectedDevices();
        System.out.println(devices.toString());
    }
}
