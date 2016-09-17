package com.mobile.os;

import com.core.logger.CustomLogger;
import com.mobile.os.iOS.iOSWebKitDebugProxy;
import org.apache.log4j.Level;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by pritamkadam on 17/09/16.
 */
public class TestOS {

    {
        String usrDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        System.setProperty("my.log", usrDir + "/target/");
        CustomLogger.log.setLevel(Level.ALL);
    }

    @Test
    public void testWebkitKill(){



        iOSWebKitDebugProxy.kill(9221 + "");
    }
}
