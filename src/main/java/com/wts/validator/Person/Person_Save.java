package com.wts.validator.Person;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

public class Person_Save implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("name"))
            && !StrKit.isBlank(inv.getController().getPara("phone"))
            ) {
      String phone = inv.getController().getPara("phone");
      if (Db.find("SELECT * FROM person WHERE phone = ?", phone).size() != 0) {
        inv.getController().renderText("该手机号码已存在!");
      } else {
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
