package com.wts.entity;

import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

public class WxService extends WxMpServiceImpl {
  private volatile static WxService wxService;

  private WxService() {
    this.setWxMpConfigStorage(WxConfig.getMe());
  }

  public static WxService getMe() {
    if (wxService == null) {
      synchronized (WxService.class) {
        if (wxService == null) {
          wxService = new WxService();
        }
      }
    }
    return wxService;
  }


}