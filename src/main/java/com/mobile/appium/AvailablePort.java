package com.mobile.appium;

import com.core.logger.CustomLogger;

import java.net.ServerSocket;

public class AvailablePort {

    public int getPort() throws Exception {

        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();

        CustomLogger.log.debug("Available port found : " + port);
        return port;
    }
}
