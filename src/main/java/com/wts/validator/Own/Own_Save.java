package com.wts.validator.Own;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.wts.entity.model.Car;
import com.wts.entity.model.Person;

public class Own_Save implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("phone"))
            && !StrKit.isBlank(inv.getController().getPara("number"))
            ) {
      String phone = inv.getController().getPara("phone");
      String number = inv.getController().getPara("number");
      if (Db.find("SELECT * FROM person WHERE phone = ?", phone).size() != 1) {
        inv.getController().renderText("该联系电话有误!");
      } else if (Db.find("SELECT * FROM car WHERE number = ?", number).size() != 1) {
        inv.getController().renderText("该车牌号码有误!");
      } else {
        String pid = Person.dao.find("SELECT * FROM person WHERE phone = ?", phone).get(0).getId().toString();
        String cid = Car.dao.find("SELECT * FROM car WHERE number = ?", number).get(0).getId().toString();
        if (Db.find("SELECT * FROM own WHERE cid = ? AND pid = ?", cid, pid).size() != 0) {
          inv.getController().renderText("该关系已存在!");
        } else {
          inv.invoke();
        }
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
