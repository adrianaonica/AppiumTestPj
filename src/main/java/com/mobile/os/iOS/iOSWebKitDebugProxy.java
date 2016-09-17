package com.mobile.os.iOS;

import com.core.cli.CommandPrompt;
import com.core.logger.CustomLogger;
import com.core.utils.PropertiesReader;
import com.mobile.appium.AvailablePort;
import java.io.IOException;


/**
 * Created by pritamkadam on 15/09/16.
 */
public class iOSWebKitDebugProxy {


    public static void  start(iOSDevice iOSDevice, String port) throws IOException, InterruptedException {

        String udid = iOSDevice.getUdid();

        String APPIUM_JS_PATH = PropertiesReader.config.getValue("APPIUM_JS_PATH");

        String iOSWebKitDebugProxyRunnerPath = APPIUM_JS_PATH.substring(0, APPIUM_JS_PATH.indexOf("build"))
                                            + "/bin/ios-webkit-debug-proxy-launcher.js";

        String runWebKitCmd = iOSWebKitDebugProxyRunnerPath + " -c " + udid + ":" + port + " -d";

        Runtime.getRuntime().exec(runWebKitCmd);

        CustomLogger.log.info("Webkit Debug Proxy Started on Device : " + udid +
                " And Port : " + port);

    }

    public static void kill(String port){
        String cmd = "lsof -t -i:" + port;
        String PID = CommandPrompt.run(cmd);
        CommandPrompt.run("kill " + PID);
    }

}
