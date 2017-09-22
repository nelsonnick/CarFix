package com.wts.validator.Car;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

public class Car_Save implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("number"))
            && !StrKit.isBlank(inv.getController().getPara("brand"))
            ) {
      String number = inv.getController().getPara("number").toUpperCase();
      if (Db.find("SELECT * FROM car WHERE number = ?", number).size() != 0) {
        inv.getController().renderText("该车牌号码已存在!");
      } else {
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
