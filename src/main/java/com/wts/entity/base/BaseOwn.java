package com.wts.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseOwn<M extends BaseOwn<M>> extends Model<M> implements IBean {

	public void setPersonId(java.lang.Integer personId) {
		set("person_id", personId);
	}

	public java.lang.Integer getPersonId() {
		return get("person_id");
	}

	public void setCarId(java.lang.Integer carId) {
		set("car_id", carId);
	}

	public java.lang.Integer getCarId() {
		return get("car_id");
	}

}
