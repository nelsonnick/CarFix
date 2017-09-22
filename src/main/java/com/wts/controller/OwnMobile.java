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
import com.wts.validator.Total;
import org.apache.log4j.Logger;

import java.util.Date;

public class OwnMobile extends Controller {
  private static Logger logger = Logger.getLogger(OwnMobile.class);

  /**
   * 页面
   */
  public void index() {
    if (getSessionAttr("User") == null) {
      redirect("/");
    } else {
      render("/static/html/mobile/mobile_own.html");
    }
  }


  /**
   * 查找
   */
  @Before(Query.class)
  public void Query() {
    renderJson(Db.paginate(
            getParaToInt("pageCurrent"),
            getParaToInt("pageSize"),
            "SELECT car.number,person.name,person.phone ",
            "FROM ((own LEFT JOIN car ON own.car_id= car.id) LEFT JOIN person ON own.person_id= person.id) WHERE car.number LIKE '%" + getPara("keyword") + "%' " +
                    "OR person.name LIKE '%" + getPara("keyword") + "%' OR person.phone LIKE '%" + getPara("keyword") + "%'").getList());
  }

  /**
   * 计数
   */
  @Before(Total.class)
  public void Total() {
    Long count = Db.queryLong("SELECT COUNT(*) FROM ((own LEFT JOIN car ON own.car_id= car.id) LEFT JOIN person ON own.person_id= person.id) WHERE car.number LIKE '%" + getPara("keyword") + "%' OR person.name LIKE '%" + getPara("keyword") + "%' OR person.phone LIKE '%" + getPara("keyword") + "%'");
    if (count % getParaToInt("pageSize") == 0) {
      renderText((count / getParaToInt("pageSize")) + "");
    } else {
      renderText((count / getParaToInt("pageSize") + 1) + "");
    }
  }

  /**
   * 保存
   */
  @Before(Own_Save.class)
  public void Save() {
    Own object = new Own();
    String pid = Person.dao.find("SELECT * FROM person WHERE phone = ?", getPara("phone")).get(0).getId().toString();
    String cid = Car.dao.find("SELECT * FROM car WHERE number = ?", getPara("number")).get(0).getId().toString();
    object.set("cid", cid)
            .set("pid", pid)
            .save();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Save;" + "cid:" + cid + ";pid:" + pid + ";time:" + new Date() + ";");
    renderText("OK");
  }

  /**
   * 删除
   */
  @Before(Own_Exist.class)
  public void Del() {
    if (Own.dao.deleteById(getPara("cid"), getPara("pid"))) {
      logger.warn("function:" + this.getClass().getSimpleName() + "/Del;" + "cid:" + getPara("cid") + ";pid:" + getPara("pid") + ";time:" + new Date() + ";");
      renderText("OK");
    } else {
      renderText("error");
    }
  }
  /**
   * 检测车牌号码
   */
  public void CheckNumber() {
    if (Db.find("SELECT * FROM car WHERE number = ?", getPara("number")).size() != 1) {
      renderText("该车牌号码有误!");
    } else {
      renderText("OK");
    }
  }
  /**
   * 检测手机号码
   */
  public void CheckPhone() {
    if (Db.find("SELECT * FROM person WHERE phone = ?", getPara("phone")).size() != 1) {
      renderText("该联系电话有误!");
    } else {
      renderText("OK");
    }
  }
}
