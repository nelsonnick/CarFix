package com.wts.controller.weixin;

import com.wts.entity.model.Car;
import com.wts.entity.model.Own;
import com.wts.entity.model.Person;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

public class Service {
  public static WxMpXmlOutMessage getOutMessage(WxMpXmlMessage inMessage) {
    if (inMessage.getMsgType().equals("text")) {
      String msg = inMessage.getContent();
      if (msg.length() > 4) {
        if (msg.substring(0, 4).equals("绑定手机") && msg.length() == 15) {
          String phone = msg.substring(4, 15);
          if (Person.dao.find("SELECT * FROM person WHERE phone = ?", phone).size() == 1) {
            Person person = Person.dao.find("SELECT * FROM person WHERE phone = ?", phone).get(0);
            if (!person.getOpenId().equals("")) {
              return WxMpXmlOutMessage
                      .TEXT()
                      .content("该手机号码已绑定，请重新输入手机号码！")
                      .fromUser(inMessage.getToUser())
                      .toUser(inMessage.getFromUser())
                      .build();
            } else {
              person.set("openId", inMessage.getFromUser()).update();
              return WxMpXmlOutMessage
                      .TEXT()
                      .content("您好，" + person.getName() + "！手机号码绑定成功！")
                      .fromUser(inMessage.getToUser())
                      .toUser(inMessage.getFromUser())
                      .build();
            }
          } else if (Person.dao.find("SELECT * FROM person WHERE phone = ?", phone).size() == 0) {
            return WxMpXmlOutMessage
                    .TEXT()
                    .content("该手机号码不存在！")
                    .fromUser(inMessage.getToUser())
                    .toUser(inMessage.getFromUser())
                    .build();
          } else {
            return WxMpXmlOutMessage
                    .TEXT()
                    .content("该手机号码存在多条记录，请联系管理人员核实！")
                    .fromUser(inMessage.getToUser())
                    .toUser(inMessage.getFromUser())
                    .build();
          }
        } else if (msg.substring(0, 4).equals("绑定车辆") && msg.length() == 11) {
          String number = msg.substring(4, 11);
          String openId = inMessage.getFromUser();
          if (Person.dao.find("SELECT * FROM person WHERE openId = ?", openId).size() == 1) {
            Person person = Person.dao.find("SELECT * FROM person WHERE openId = ?", openId).get(0);
            if (Car.dao.find("SELECT * FROM car WHERE number = ?", number).size() == 0) {
              return WxMpXmlOutMessage
                      .TEXT()
                      .content("该车牌号码不存在！")
                      .fromUser(inMessage.getToUser())
                      .toUser(inMessage.getFromUser())
                      .build();
            } else if (Car.dao.find("SELECT * FROM car WHERE number = ?", number).size() == 1) {
              Car car = Car.dao.find("SELECT * FROM car WHERE number = ?", number).get(0);
              if (Own.dao.find("SELECT * FROM own WHERE cid = ? AND pid = ?", car.getId(), person.getId()).size() != 0) {
                return WxMpXmlOutMessage
                        .TEXT()
                        .content("您已绑定该车辆！")
                        .fromUser(inMessage.getToUser())
                        .toUser(inMessage.getFromUser())
                        .build();
              } else if (Own.dao.find("SELECT * FROM own WHERE cid = ? ", car.getId()).size() == 1) {
                return WxMpXmlOutMessage
                        .TEXT()
                        .content("该车辆已被他人绑定！")
                        .fromUser(inMessage.getToUser())
                        .toUser(inMessage.getFromUser())
                        .build();
              } else {
                Own object = new Own();
                object.set("cid", car.getId())
                        .set("pid", person.getId())
                        .save();
                return WxMpXmlOutMessage
                        .TEXT()
                        .content("车辆绑定成功！")
                        .fromUser(inMessage.getToUser())
                        .toUser(inMessage.getFromUser())
                        .build();
              }
            } else {
              return WxMpXmlOutMessage
                      .TEXT()
                      .content("该车牌号码存在多条记录，请联系管理人员核实！")
                      .fromUser(inMessage.getToUser())
                      .toUser(inMessage.getFromUser())
                      .build();
            }
          } else {
            return WxMpXmlOutMessage
                    .TEXT()
                    .content("请先绑定您的手机号码！")
                    .fromUser(inMessage.getToUser())
                    .toUser(inMessage.getFromUser())
                    .build();
          }
        } else {
          return WxMpXmlOutMessage
                  .TEXT()
                  .content(msg)
                  .fromUser(inMessage.getToUser())
                  .toUser(inMessage.getFromUser())
                  .build();
        }
      } else {
        return WxMpXmlOutMessage
                .TEXT()
                .content(msg)
                .fromUser(inMessage.getToUser())
                .toUser(inMessage.getFromUser())
                .build();
      }
    } else {
      return WxMpXmlOutMessage
              .TEXT()
              .content("请发送文本消息")
              .fromUser(inMessage.getToUser())
              .toUser(inMessage.getFromUser())
              .build();
    }
  }
}