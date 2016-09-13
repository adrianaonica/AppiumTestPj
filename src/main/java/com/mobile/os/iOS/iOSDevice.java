package com.mobile.os.iOS;

import com.mobile.MobileDevice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pritamkadam on 13/09/16.
 */

public class iOSDevice implements MobileDevice{
    private String name;
    private String version;
    private String udid;
    private boolean isAvailable;
    private boolean isSimulator;

    public iOSDevice(String name, String version, String udid, boolean isAvailable, boolean isSimulator) {
        this.name = name;
        this.version = version;
        this.udid = udid;
        this.isAvailable = isAvailable;
        this.isSimulator = isSimulator;
    }

    public String getName() {
        return name;
    }

    public String getVersion(){
        return this.version;
    }

    public String getUdid() {
        return this.udid;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public boolean isSimulator() {
        return isSimulator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setSimulator(boolean simulator) {
        isSimulator = simulator;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        iOSDevice iOSDevice = (iOSDevice) o;

        if (!name.equals(iOSDevice.name)) return false;
        if (!version.equals(iOSDevice.version)) return false;
        return udid.equals(iOSDevice.udid);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + udid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "iOSDevice{" +
                "name='" + name + '\'' +
                ", version=" + version +
                ", udid='" + udid + '\'' +
                ", isAvailable=" + isAvailable +
                ", isSimulator=" + isSimulator +
                '}';
    }
}
