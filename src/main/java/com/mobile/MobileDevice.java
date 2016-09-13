package com.mobile;

/**
 * Created by pritamkadam on 13/09/16.
 */
public interface MobileDevice {
    String getName();

    String getVersion();

    String getUdid();

    boolean isAvailable();

    boolean isSimulator();

    void setName(String name);

    void setVersion(String version);

    void setUdid(String udid);

    void setAvailable(boolean available);

    void setSimulator(boolean simulator);

    String toString();


}
