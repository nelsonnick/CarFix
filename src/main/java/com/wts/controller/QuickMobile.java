package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wts.entity.model.Car;
import com.wts.entity.model.Own;
import com.wts.entity.model.Person;
import com.wts.validator.Own.Own_Exist;
import com.wts.validator.Own.Own_Save;
import com.wts.validator.Query;
import com.wts.validator.Quick.Quick_Save;
import com.wts.validator.Total;
import org.apache.log4j.Logger;

import java.util.Date;

public class QuickMobile extends Controller {
  private static Logger logger = Logger.getLogger(QuickMobile.class);

  /**
   * 页面
   */
  public void index() {
    if (getSessionAttr("User") == null) {
      redirect("/");
    } else {
      render("/static/html/mobile/mobile_quick.html");
    }
  }

  /**
   * 保存
   */
  @Before(Quick_Save.class)
  public void Save() {
    Person person = new Person();
    person.set("name", getPara("name"))
            .set("phone", getPara("phone"))
            .set("openId", "")
            .save();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Save;" + "phone:" + getPara("phone") + ";time:" + new Date() + ";");
    System.out.println("111");
    Car car = new Car();
    car.set("number", getPara("number").toUpperCase())
            .set("brand", getPara("brand"))
            .set("remark", getPara("remark"))
            .save();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Save;" + "number:" + getPara("number") + ";time:" + new Date() + ";");
    System.out.println("222");
    Own own = new Own();
    own.set("car_id", car.getId())
            .set("person_id", person.getId())
            .save();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Save;" + "cid:" + car.getId() + ";pid:" + person.getId() + ";time:" + new Date() + ";");
    renderText("OK");
  }


}
