package com.core.managers;

import com.core.cli.CommandPrompt;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class OSManager {

    private static String OS;

    public static String getOS(){
        if(OS == null) OS = System.getenv("os.name");
        return OS;
    }

    public static boolean isWindows(){
        return getOS().startsWith("Windows");
    }

    public static boolean isMac(){
        return getOS().startsWith("Mac");
    }

    public static String getWorkingDir(){
        return System.getProperty("user.dir");
    }

    public static String getOSDetails(){
        if(isMac()) return CommandPrompt.run("sw_vers");
        else if(isWindows()) return CommandPrompt.run("systeminfo | findstr /C:\"OS\"");
        return "";
    }
}
