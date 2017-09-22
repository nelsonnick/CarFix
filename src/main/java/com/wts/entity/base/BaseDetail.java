package com.wts.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDetail<M extends BaseDetail<M>> extends Model<M> implements IBean {

	public void setFixId(java.lang.Integer fixId) {
		set("fix_id", fixId);
	}

	public java.lang.Integer getFixId() {
		return get("fix_id");
	}

	public void setItemId(java.lang.Integer itemId) {
		set("item_id", itemId);
	}

	public java.lang.Integer getItemId() {
		return get("item_id");
	}

	public void setMoney(java.lang.Double money) {
		set("money", money);
	}

	public java.lang.Double getMoney() {
		return get("money");
	}

	public void setDetail(java.lang.String detail) {
		set("detail", detail);
	}

	public java.lang.String getDetail() {
		return get("detail");
	}

}
