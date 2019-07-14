package com.mobile.os.iOS;

import com.core.cli.CommandPrompt;
import com.core.logger.CustomLogger;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Instruments {

    public static ArrayList<String> getConnectedDevices(){
        ArrayList<String> devices = new ArrayList<>();
        CustomLogger.log.debug("Getting list of connected iOS devices and simulators.");

        String cmd = "instruments -s devices";
        String output = CommandPrompt.run(cmd);

        for (String line : output.split("\n")){
            if(line.contains("(")){
                devices.add(line);
            }
        }

        CustomLogger.log.debug("Results of " + cmd + " => " +
                devices.toString());

        return devices;
    }

    @Test
    public void testConnectedDevices(){
        ArrayList<String> devices = getConnectedDevices();
        System.out.println(devices.toString());
    }
}
