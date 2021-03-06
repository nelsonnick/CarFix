package com.wts.validator.Car;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

import static com.wts.controller.CarMobile.getItem;

public class Car_Fix implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("id"))
            && !StrKit.isBlank(inv.getController().getPara("mileage"))
            && !StrKit.isBlank(inv.getController().getPara("money"))
            && !StrKit.isBlank(inv.getController().getPara("detail"))
            ) {
      String id = inv.getController().getPara("id");
      String detail = inv.getController().getPara("detail");
      if (Db.find("SELECT * FROM car WHERE id = ?", id).size() == 0) {
        inv.getController().renderText("该车牌序号不存在!");
      } else if(detail.equals(getItem())) {
        inv.getController().renderText("无保养内容!");
      } else{
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
