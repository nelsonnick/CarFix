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
import com.wts.validator.Query;
import com.wts.validator.Total;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarMobile extends Controller {
  private static Logger logger = Logger.getLogger(CarMobile.class);

  /**
   * 页面
   */
  public void index() {
    if (getSessionAttr("User") == null) {
      redirect("/");
    } else {
      render("/static/html/mobile/mobile_car.html");
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
            "FROM car WHERE number LIKE '%" + getPara("keyword") + "%' " +
                    "OR brand LIKE '%" + getPara("keyword") + "%' OR remark LIKE '%" + getPara("keyword") + "%' ORDER BY id ASC").getList());
  }

  /**
   * 计数
   */
  @Before(Total.class)
  public void Total() {
    Long count = Db.queryLong("SELECT COUNT(*) FROM car WHERE number LIKE '%" + getPara("keyword") + "%' OR brand LIKE '%" + getPara("keyword") + "%' OR remark LIKE '%" + getPara("keyword") + "%'");
    if (count % getParaToInt("pageSize") == 0) {
      renderText((count / getParaToInt("pageSize")) + "");
    } else {
      renderText((count / getParaToInt("pageSize") + 1) + "");
    }
  }

  /**
   * 读取
   */
  @Before(Car_Exist.class)
  public void Get() {
    renderJson(Car.dao.findById(getPara("id")));
  }

  /**
   * 保存
   */
  @Before(Car_Save.class)
  public void Save() {
    Car object = new Car();
    object.set("number", getPara("number").toUpperCase())
            .set("brand", getPara("brand"))
            .set("remark", getPara("remark"))
            .save();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Save;" + "number:" + getPara("number") + ";time:" + new Date() + ";");
    renderText("OK");
  }

  /**
   * 修改
   */
  @Before({Car_Exist.class, Car_Edit.class})
  public void Edit() {
    Car object = Car.dao.findById(getPara("id"));
    object.set("number", getPara("number").toUpperCase())
            .set("brand", getPara("brand"))
            .set("remark", getPara("remark"))
            .update();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Edit;" + "number:" + getPara("number") + ";time:" + new Date() + ";");
    renderText("OK");
  }

  /**
   * 检测车牌号码_新增
   */
  public void CheckNumberForAdd() {
    if (Db.find("SELECT * FROM car WHERE number = ?", getPara("number").toUpperCase()).size() != 0) {
      renderText("该车牌号码已存在!");
    } else {
      renderText("OK");
    }
  }

  /**
   * 检测车牌号码_修改
   */
  public void CheckNumberForEdit() {
    if (!Car.dao.findById(getPara("id")).get("number").equals(getPara("number").toUpperCase())
            && Db.find("SELECT * FROM car WHERE number = ?", getPara("number")).size() != 0) {
      renderText("该车牌号码已存在!");
    } else {
      renderText("OK");
    }
  }

  public static String getItem() {
    //    String item=
    //            "{\"item\":[{\"name\": \"换机油\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"}, " +
    //            "{\"name\": \"换机油滤芯\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"}, " +
    //            "{\"name\": \"换空气滤芯\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"}, " +
    //            "{\"name\": \"换汽油滤芯\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"}, " +
    //            "{\"name\": \"清洗节气门\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"}, " +
    //            "{\"name\": \"清洗喷油嘴\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"}]}";
    List<Record> lists = Db.find("SELECT * FROM item ORDER BY id ASC");
    String item = "";
    if (lists.size() != 0) {
      for (int i = 0; i < lists.size(); i++) {
        item = item + "{\"name\":\"" + lists.get(i).get("name") + "\", \"open\": false, \"money\": \"\", \"detail\": \"\", \"icon\": \"check_box_outline_blank\"},";
      }
      item = item.substring(0, item.length() - 1);
      item = "{\"item\":[" + item + "]}";
    }
    return item;
  }


  /**
   * 项目列表
   */
  public void Item() {
    renderText(getItem());
  }

  /**
   * 保存项目
   */
  public void Fix() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, 6);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd EE hh:mm:ss");
    Fix object = new Fix();
    object.set("car_id", getPara("id"))
            .set("mileage", getPara("mileage"))
            .set("money", getPara("money"))
            .set("detail", getPara("detail"))
            .set("time", new Date())
            .set("time_next", cal.getTime())
            .set("remark", getPara("remark"))
            .save();
    logger.warn("function:" + this.getClass().getSimpleName() + "/Fix;" + "car_id:" + getPara("id") + ";time:" + new Date() + ";");
    renderText("OK");
  }

}
