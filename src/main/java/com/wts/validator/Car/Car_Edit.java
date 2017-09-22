package com.wts.validator.Car;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.wts.entity.model.Car;

public class Car_Edit implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("number"))
            && !StrKit.isBlank(inv.getController().getPara("brand"))
            && !StrKit.isBlank(inv.getController().getPara("remark"))
            ) {
      String number = inv.getController().getPara("number").toUpperCase();
      String brand = inv.getController().getPara("brand");
      String remark = inv.getController().getPara("remark");
      String id = inv.getController().getPara("id");
      Car object = Car.dao.findById(id);
      if (!object.getStr("number").equals(number)
              && Db.find("SELECT * FROM car WHERE number = ?", number).size() != 0) {
        inv.getController().renderText("该车牌号码已存在!");
      } else if(object.getStr("number").equals(number)
              && object.getStr("brand").equals(brand)
              && object.getStr("remark").equals(remark)
              ){
        inv.getController().renderText("未发现修改内容!");
      }else {
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }

  }
}
