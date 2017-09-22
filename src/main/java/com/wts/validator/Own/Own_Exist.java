package com.wts.validator.Own;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.wts.entity.model.Own;

public class Own_Exist implements Interceptor {
  public void intercept(Invocation inv) {
    if (!StrKit.isBlank(inv.getController().getPara("cid"))
            && !StrKit.isBlank(inv.getController().getPara("pid"))
            ) {
      if (Own.dao.findById(inv.getController().getPara("cid"),inv.getController().getPara("pid")) == null) {
        inv.getController().renderText("指定关系不存在!");
      } else {
        inv.invoke();
      }
    } else {
      inv.getController().renderText("缺少参数!");
    }
  }
}
