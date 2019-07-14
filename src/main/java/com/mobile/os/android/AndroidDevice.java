package com.mobile.os.android;

import com.mobile.MobileDevice;

public class AndroidDevice implements MobileDevice{
    private String name;
    private String  version;
    private String udid;
    private boolean isAvailable;
    private boolean isSimulator;

    public AndroidDevice(String name, String version, String udid, boolean isAvailable, boolean isSimulator) {
        this.name = name;
        this.version = version;
        this.udid = udid;
        this.isAvailable = isAvailable;
        this.isSimulator=isSimulator;
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

    @Override
    public boolean isSimulator() {
        return isSimulator;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public void setSimulator(boolean simulator) {
        this.isSimulator=simulator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndroidDevice device = (AndroidDevice) o;

        if (!name.equals(device.name)) return false;
        if (!version.equals(device.version)) return false;
        return udid.equals(device.udid);

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
        return "AndroidDevice{" +
                "name='" + name + '\'' +
                ", version=" + version +
                ", udid='" + udid + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
