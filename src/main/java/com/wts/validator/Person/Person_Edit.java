package com.wts.validator.Person;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.wts.entity.model.Person;

public class Person_Edit implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("name"))
            && !StrKit.isBlank(inv.getController().getPara("phone"))
            ) {
      String name = inv.getController().getPara("name");
      String phone = inv.getController().getPara("phone");
      String openId = inv.getController().getPara("openId");
      String id = inv.getController().getPara("id");
      Person object = Person.dao.findById(id);
      if (!object.getStr("phone").equals(phone)
              && Db.find("SELECT * FROM person WHERE phone = ?", phone).size() != 0) {
        inv.getController().renderText("该手机号码已存在!");
      } else if(object.getStr("name").equals(name)
              && object.getStr("phone").equals(phone)
              && object.getStr("openId").equals(openId)
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
