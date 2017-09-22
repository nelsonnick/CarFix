package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wts.entity.model.Person;
import com.wts.validator.Person.Person_Edit;
import com.wts.validator.Person.Person_Exist;
import com.wts.validator.Person.Person_Save;
import com.wts.validator.Query;
import com.wts.validator.Total;
import org.apache.log4j.Logger;

import java.util.Date;

public class PersonMobile extends Controller {
  private static Logger logger = Logger.getLogger(PersonMobile.class);

  /**
   * 页面
   */
  public void index() {
    if (getSessionAttr("User") == null) {
      redirect("/");
    } else {
      render("/static/html/mobile/mobile_person.html");
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
            "SELECT * ",
            "FROM person WHERE openId LIKE '%" + getPara("keyword") + "%' " +
                    "OR name LIKE '%" + getPara("keyword") + "%' OR phone LIKE '%" + getPara("keyword") + "%' ORDER BY id ASC").getList());
  }

  /**
   * 计数
   */
  @Before(Total.class)
  public void Total() {
    Long count = Db.queryLong("SELECT COUNT(*) FROM person WHERE openId LIKE '%" + getPara("keyword") + "%' OR name LIKE '%" + getPara("keyword") + "%' OR phone LIKE '%" + getPara("keyword") + "%'");
    if (count % getParaToInt("pageSize") == 0) {
      renderText((count / getParaToInt("pageSize")) + "");
    } else {
      renderText((count / getParaToInt("pageSize") + 1) + "");
    }
  }

  /**
   * 读取
   */
  @Before(Person_Exist.class)
  public void Get() {
    renderJson(Person.dao.findById(getPara("id")));
  }

  /**
   * 保存
   */
  @Before(Person_Save.class)
  public void Save() {
    Person object = new Person();
    object.set("name", getPara("name"))
            .set("phone", getPara("phone"))
            .set("openId", "")
            .save();
    logger.warn("function:"+this.getClass().getSimpleName()+"/Save;"+"phone:"+getPara("phone")+";time:"+new Date()+";");
    renderText("OK");
  }

  /**
   * 修改
   */
  @Before({Person_Exist.class, Person_Edit.class})
  public void Edit() {
    Person object = Person.dao.findById(getPara("id"));

    object.set("name", getPara("name"))
            .set("phone", getPara("phone"))
            .set("openId", getPara("openId"))
            .update();
    logger.warn("function:"+this.getClass().getSimpleName()+"/Edit;"+"phone:"+getPara("phone")+";time:"+new Date()+";");
    renderText("OK");
  }

  /**
   * 检测手机号码_新增
   */
  public void CheckPhoneForAdd() {
    if (Db.find("SELECT * FROM person WHERE phone = ?", getPara("phone")).size() != 0) {
      renderText("该手机号码已存在!");
    } else {
      renderText("OK");
    }
  }

  /**
   * 检测手机号码_修改
   */
  public void CheckPhoneForEdit() {
    if (!Person.dao.findById(getPara("id")).get("phone").equals(getPara("phone"))
            && Db.find("SELECT * FROM person WHERE phone = ?", getPara("phone")).size() != 0) {
      renderText("该手机号码已存在!");
    } else {
      renderText("OK");
    }
  }
}
