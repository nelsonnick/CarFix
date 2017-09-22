package com.wts.validator.Fix;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.wts.entity.model.Fix;

public class Fix_Exist implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("id"))) {
      if (Fix.dao.findById(inv.getController().getPara("id")) == null) {
        inv.getController().renderText("指定车辆维修记录不存在!");
      } else {
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
