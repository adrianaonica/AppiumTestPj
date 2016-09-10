package com.mobile.appium;

import java.net.ServerSocket;

/**
 * Created by pritamkadam on 10/09/16.
 */
public class AvailablePort {

    public int getPort() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }
}
