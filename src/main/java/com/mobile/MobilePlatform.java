package com.mobile;

/**
 * Created by pritamkadam on 10/09/16.
 */
public enum MobilePlatform {


    Android("Android"),
    iOS("iOS"),
    Web_Android("Web_Android"),
    Web_iOS("Web_iOS"),
    Firefox("Firefox"),
    Chrome("Chrome"),
    IE("IE"),
    Safari("Safari");

    private String value;

    MobilePlatform(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
