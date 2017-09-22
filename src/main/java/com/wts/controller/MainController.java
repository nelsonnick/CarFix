package com.wts.controller;

import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainController extends Controller {
  private static Logger logger = Logger.getLogger(MainController.class);

  public void index() throws IOException {
    render("/static/html/mobile/mobile_login.html");
  }

  /**
   * 登录
   */
  public void Login() throws IOException {
    if (getPara("name").equals("1") && getPara("pass").equals("1")) {
      setSessionAttr("User", "");
      logger.warn("function:" + this.getClass().getSimpleName() + "/Login;");
      renderText("OK");
    } else {
      renderText("error");
    }
  }

}
