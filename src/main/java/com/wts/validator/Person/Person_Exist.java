package com.wts.validator.Person;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.wts.entity.model.Car;

public class Person_Exist implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("id"))) {
      if (Car.dao.findById(inv.getController().getPara("id")) == null) {
        inv.getController().renderText("指定人员不存在!");
      } else {
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
