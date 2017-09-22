package com.wts.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wts.entity.model.Car;
import com.wts.entity.model.Fix;
import com.wts.validator.Car.Car_Edit;
import com.wts.validator.Car.Car_Exist;
import com.wts.validator.Car.Car_Save;
import com.wts.validator.Fix.Fix_Edit;
import com.wts.validator.Fix.Fix_Exist;
import com.wts.validator.Query;
import com.wts.validator.Total;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FixMobile extends Controller {
  private static Logger logger = Logger.getLogger(FixMobile.class);

  /**
   * 页面
   */
  public void index() {
    if (getSessionAttr("User") == null) {
      redirect("/");
    } else {
      render("/static/html/mobile/mobile_fix.html");
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
            "SELECT fix.id AS id,car.id AS car_id,fix.mileage AS mileage,fix.money AS money,fix.detail AS detail,fix.time AS time,fix.time_next AS time_next,fix.remark AS remark,car.number AS number,car.brand AS brand",
            "FROM fix LEFT JOIN car ON fix.car_id=car.id WHERE car.number LIKE '%" + getPara("keyword") + "%' " +
                    "OR car.brand LIKE '%" + getPara("keyword") + "%' OR car.remark LIKE '%" + getPara("keyword") + "%' ORDER BY fix.id DESC").getList());
  }

  /**
   * 计数
   */
  @Before(Total.class)
  public void Total() {
    Long count = Db.queryLong("SELECT COUNT(*) FROM fix LEFT JOIN car ON fix.car_id=car.id WHERE car.number LIKE '%" + getPara("keyword") + "%' OR car.brand LIKE '%" + getPara("keyword") + "%' OR car.remark LIKE '%" + getPara("keyword") + "%'");
    if (count % getParaToInt("pageSize") == 0) {
      renderText((count / getParaToInt("pageSize")) + "");
    } else {
      renderText((count / getParaToInt("pageSize") + 1) + "");
    }
  }

  /**
   * 读取
   */
  @Before(Fix_Exist.class)
  public void Get() {
    renderJson(Fix.dao.findById(getPara("id")));
  }

  /**
   * 删除
   */
  @Before(Fix_Exist.class)
  public void Del() {
    if (Fix.dao.deleteById(getPara("id"))){
      logger.warn("function:"+this.getClass().getSimpleName()+"/Del;"+"id:"+getPara("id")+";time:"+new Date()+";");
      renderText("OK");
    } else {
      renderText("error");
    }

  }

  /**
   * 修改
   */
  @Before({Fix_Exist.class, Fix_Edit.class})
  public void Edit() {
    Fix object = Fix.dao.findById(getPara("id"));
    object.set("car_id", getPara("id"))
            .set("mileage", getPara("mileage"))
            .set("money", getPara("money"))
            .set("detail", getPara("detail"))
            .set("remark", getPara("remark"))
            .update();
    logger.warn("function:"+this.getClass().getSimpleName()+"/Edit;"+"id:"+getPara("id")+";time:"+new Date()+";");
    renderText("OK");
  }


  public static String getItem() {
    List<Record> lists = Db.find("SELECT * FROM item ORDER BY id ASC");
    String item = "";
    if (lists.size() != 0) {
      for (int i = 0; i < lists.size(); i++) {
        item = item + "{\"name\":\""+lists.get(i).get("name")+"\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"},";
      }
      item = item.substring(0, item.length() - 1);
    }
    return item;
  }
}
