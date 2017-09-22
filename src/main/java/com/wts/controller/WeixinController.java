package com.wts.controller;

import com.jfinal.core.Controller;
import com.wts.entity.WxConfig;
import com.wts.entity.WxService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static com.wts.controller.weixin.Service.getOutMessage;

public class WeixinController extends Controller {

  public void index() throws IOException {
    if (getRequest().getMethod() == "GET") {
      PrintWriter out = this.getResponse().getWriter();
      String result;
      if (!WxService.getMe().checkSignature(getPara("timestamp"), getPara("nonce"), getPara("signature"))) {
        // 消息签名不正确，说明不是公众平台发过来的消息
        result = "非法请求";
      } else {
        if (StringUtils.isNotBlank(getPara("echostr"))) {
          // 说明是一个仅仅用来验证的请求，回显echostr
          result = getPara("echostr");
        } else {
          result = "";
        }
      }
      out.print(result);
      out.close();
    } else if (getRequest().getMethod() == "POST") {
      this.getRequest().setCharacterEncoding("UTF-8");
      this.getResponse().setCharacterEncoding("UTF-8");
      InputStream inputStream = this.getRequest().getInputStream();
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(inputStream, WxConfig.getMe(), getPara("timestamp"), getPara("nonce"), getPara("msg_signature"));
      this.getResponse().getWriter().write(getOutMessage(inMessage).toEncryptedXml(WxConfig.getMe()));
    } else {
      System.out.println("错误请求");
    }
    renderNull();
  }


}
