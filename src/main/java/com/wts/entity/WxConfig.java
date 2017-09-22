package com.wts.entity;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

public class WxConfig extends WxMpInMemoryConfigStorage {
    private volatile static WxConfig wxConfig;

    private WxConfig() {
        this.setAppId("wx8e53cd18100f8392");
        this.setSecret("48ca7feeea5582c6debe77ffc2c15d59");
        this.setToken("weixin4j");
        this.setAesKey("jQwKRUjOmWYUwAFBtEhPDtYp1pEFLoCiUviKXNdHMTW");
    }

    public static WxConfig getMe() {
        if (wxConfig == null) {
            synchronized (WxConfig.class) {
                if (wxConfig == null) {
                    wxConfig = new WxConfig();
                }
            }
        }
        return wxConfig;
    }


}