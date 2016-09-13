package com.mobile.os.android;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class AndroidDevice {
    private String name;
    private String  version;
    private String udid;
    private boolean isAvailable;

    public AndroidDevice(String name, String version, String udid, boolean isAvailable) {
        this.name = name;
        this.version = version;
        this.udid = udid;
        this.isAvailable = isAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "AndroidDevice{" +
                "name='" + name + '\'' +
                ", version=" + version +
                ", udid='" + udid + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
