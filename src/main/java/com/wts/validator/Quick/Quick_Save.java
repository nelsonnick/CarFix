package com.wts.validator.Quick;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

public class Quick_Save implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("name"))
            && !StrKit.isBlank(inv.getController().getPara("phone"))
            && !StrKit.isBlank(inv.getController().getPara("number"))
            && !StrKit.isBlank(inv.getController().getPara("brand"))
            ) {
      String phone = inv.getController().getPara("phone");
      String number = inv.getController().getPara("number");
      if (Db.find("SELECT * FROM person WHERE phone = ?", phone).size() != 0) {
        inv.getController().renderText("该手机号码已存在!");
      } else if (Db.find("SELECT * FROM car WHERE number = ?", number).size() != 0) {
        inv.getController().renderText("该车牌号码已存在!");
      } else{
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
