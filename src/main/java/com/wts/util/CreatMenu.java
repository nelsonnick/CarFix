package com.wts.util;//package com.wts.util;
//
//import com.foxinmy.weixin4j.exception.WeixinException;
//import com.foxinmy.weixin4j.http.weixin.ApiResult;
//import com.foxinmy.weixin4j.model.Button;
//import com.foxinmy.weixin4j.qy.WeixinProxy;
//import com.foxinmy.weixin4j.type.ButtonType;
//import org.apache.log4j.Logger;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CreatMenu {
//  private static Logger logger = Logger.getLogger(CreatMenu.class);
//
//  private static String BASIC = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri=http%3a%2f%2f"+ParamesAPI.URL+"%2f"+"XXXXX"+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
//
//
//  public static void CreateManager (){
//    // 校园管理入口菜单
//    String TEACHER = BASIC.replaceAll("XXXXX","Teacher%2fMobile_Manager_Teacher%2f");
//    String ROOM = BASIC.replaceAll("XXXXX","Room%2fMobile_Manager_Room%2f");
//    String COURSE = BASIC.replaceAll("XXXXX","Car%2fMobile_Manager_Course%2f");
//    String STUDENT = BASIC.replaceAll("XXXXX","Student%2fMobile_Manager_Student%2f");
//    String PARENT = BASIC.replaceAll("XXXXX","Parent%2fMobile_Manager_Parent%2f");
//    String SEMESTER = BASIC.replaceAll("XXXXX","Semester%2fMobile_Manager_Semester%2f");
//    String TEACHERMESSAGE = BASIC.replaceAll("XXXXX","teacherMessage%2fMobile_Manager_TeacherMessage%2f");
//
//    Button manager_1 = new Button("教师", TEACHER, ButtonType.view);
//    Button manager_2 = new Button("学生", STUDENT, ButtonType.view);
//    Button manager_3 = new Button("家长", PARENT, ButtonType.view);
//    Button manager_4 = new Button("班级", ROOM, ButtonType.view);
//    Button manager_5 = new Button("课程", COURSE, ButtonType.view);
//    Button manager_6 = new Button("学期", SEMESTER, ButtonType.view);
//    Button manager_7 = new Button("教师消息", TEACHERMESSAGE, ButtonType.view);
//
//
//
//    Button managerButtonA = new Button("相关人员", manager_1, manager_2, manager_3);
//    Button managerButtonB = new Button("基础设定", manager_4, manager_5, manager_6);
//    Button managerButtonC = new Button("消息管理", manager_7);
//    List<Button> managers = new ArrayList<Button>();
//    Button manager_g = new Button("消息管理", TEACHERMESSAGE, ButtonType.view);
//
//    managers.add(manager_g);
////    managers.add(managerButtonA);
////    managers.add(managerButtonB);
////    managers.add(managerButtonC);
//    try {
//      ApiResult ret = new WeixinProxy().createMenu(ParamesAPI.managerId, managers);
//      System.out.printf("创建菜单返回结果："+ret.getReturnMsg());
//    } catch (WeixinException e){
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//
//  private static String PICTURE = BASIC.replaceAll("XXXXX","user%2ftokenSignature%2f");
//
//  public static void CreateTeacher (){
//    // 班级管理入口菜单
//    String COURSE = BASIC.replaceAll("XXXXX","Car%2fMobile_Teacher_Course%2f");
//    String TEACHERMESSAGE = BASIC.replaceAll("XXXXX","teacherMessageRead%2fMobile_Teacher_TeacherMessage%2f");
//
//
//    Button teacher_1 = new Button("课程设定", COURSE, ButtonType.view);
//    Button teacher_2 = new Button("教师消息", TEACHERMESSAGE, ButtonType.view);
//
//    Button managerButtonA = new Button("基础管理A", teacher_1, teacher_2);
////    Button managerButtonB = new Button("基础管理B", teacher_1, teacher_2);
////    Button managerButtonC = new Button("基础管理C", teacher_1, teacher_2);
//
//    List<Button> managers = new ArrayList<Button>();
//    Button teacher_t = new Button("我的消息", TEACHERMESSAGE, ButtonType.view);
//    managers.add(teacher_t);
////    managers.add(managerButtonA);
////    managers.add(managerButtonB);
////    managers.add(managerButtonC);
//    try {
//      ApiResult ret = new WeixinProxy().createMenu(ParamesAPI.teacherId, managers);
//      System.out.printf("创建菜单返回结果："+ret.getReturnMsg());
//    } catch (WeixinException e){
//      System.out.println(e.getMessage());
//    }
//
//  }
//  // 班级园地入口菜单
//  private static String NOTICEZ = BASIC.replaceAll("XXXXX","notice%2fforParent%2f");
//  private static String ROOMWORKS = BASIC.replaceAll("XXXXX","roomwork%2fforParent%2f");
//  private static String TEAMWORKS = BASIC.replaceAll("XXXXX","teamwork%2fforParent%2f");
//  private static String PARENTSTUDENT = BASIC.replaceAll("XXXXX","Student%2fforParent%2f");
//  private static String PERSONALB = BASIC.replaceAll("XXXXX","Parent%2fforParentPersonal%2f");
//
//  public static void CreateParent (){
//    Button parent_1 = new Button("校园公告", NOTICEZ, ButtonType.view);
//    Button parent_2 = new Button("班级消息", ROOMWORKS, ButtonType.view);
//    Button parent_3 = new Button("社团消息", TEAMWORKS, ButtonType.view);
//    Button parent_4 = new Button("学生绑定", PARENTSTUDENT, ButtonType.view);
//    Button parent_5 = new Button("用户信息", PERSONALB, ButtonType.view);
//
//    Button managerButtonA = new Button("校园消息", parent_1, parent_2, parent_3);
//    Button managerButtonB = new Button("其他", parent_4, parent_5);
//
//    List<Button> managers = new ArrayList<Button>();
//    managers.add(managerButtonA);
//    managers.add(managerButtonB);
//    try {
//      ApiResult ret = new WeixinProxy().createMenu(ParamesAPI.parentId, managers);
//      System.out.printf("创建菜单返回结果："+ret.getReturnMsg());
//    } catch (WeixinException e){
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//
//  public static void main(String[] args) {
//    CreateManager();
//    CreateTeacher();
////    CreateParent();
////    try{
////      Button School = new Button("管理系统", All, ButtonType.view);
////      // 学校的相关功能
////      Button School1_1 = new Button("教师管理", TEACHER, ButtonType.view);
////      Button School1_2 = new Button("班级管理", ROOM, ButtonType.view);
////      Button School1_3 = new Button("家长管理", CONNECT, ButtonType.view);
////      Button School1_4 = new Button("学生管理", CONNECT, ButtonType.view);
////      Button School1_5 = new Button("公告管理", CONNECT, ButtonType.view);
////
////      Button School2_1 = new Button("校长信箱", CONNECT, ButtonType.view);
////      Button School2_2 = new Button("", CONNECT, ButtonType.view);
////      Button School2_3 = new Button("", CONNECT, ButtonType.view);
////      Button School2_4 = new Button("", CONNECT, ButtonType.view);
////      Button School2_5 = new Button("", CONNECT, ButtonType.view);
////
////      Button School3_1 = new Button("班级公告", CONNECT, ButtonType.view);
////      Button School3_2 = new Button("班级作业", CONNECT, ButtonType.view);
////      Button School3_3 = new Button("班级红榜", CONNECT, ButtonType.view);
////      Button School3_4 = new Button("班级成绩", CONNECT, ButtonType.view);
////      Button School3_5 = new Button("班级考勤", CONNECT, ButtonType.view);
////
////      Button School1 = new Button("综合管理", School1_1, School1_2, School1_3, School1_4, School1_5);
////      Button School2 = new Button("公告信息", School2_1);
////      Button School3 = new Button("班级情况", School3_1, School3_2, School3_3, School3_4, School3_5);
////
////      List<Button> schools = new ArrayList<Button>();
////      schools.add(School1);
////      schools.add(School2);
////      schools.add(School3);
////
////      List<Button> schoolz = new ArrayList<Button>();
////      schoolz.add(School);
////
////      ApiResult ret = new WeixinProxy().createMenu(26, schools);
////      System.out.printf("创建菜单返回结果："+ret.getReturnMsg());
////      System.exit(0);
////    } catch (WeixinException e){
////      logger.error(e.getMessage());
////      System.out.println(e.getMessage());
////      System.exit(0);
////    }
////    // 家长的相关功能
////    Button Parent1_1 = new Button("关联学生", CONNECT, ButtonType.view);
////    Button Parent1_2 = new Button("取消关联", CONNECT, ButtonType.view);
////    Button Parent1_3 = new Button("信息修改", CONNECT, ButtonType.view);
////    Button Parent1_4 = new Button("请销假", CONNECT, ButtonType.view);
////    Button Parent1_5 = new Button("学生成绩", CONNECT, ButtonType.view);
////
////    Button Parent2_1 = new Button("班级作业", CONNECT, ButtonType.view);
////    Button Parent2_2 = new Button("班级公告", CONNECT, ButtonType.view);
////    Button Parent2_3 = new Button("班级投票", CONNECT, ButtonType.view);
////    Button Parent2_4 = new Button("每日红榜", CONNECT, ButtonType.view);
////    Button Parent2_5 = new Button("班级课程", CONNECT, ButtonType.view);
////
////    Button Parent3_1 = new Button("班级缴费", CONNECT, ButtonType.view);
////    Button Parent3_2 = new Button("缴费查询", CONNECT, ButtonType.view);
////    Button Parent3_3 = new Button("校园联络", CONNECT, ButtonType.view);
////    Button Parent3_4 = new Button("学校官网", CONNECT, ButtonType.view);
////    Button Parent3_5 = new Button("校长信箱", CONNECT, ButtonType.view);
////
////    Button Parent1 = new Button("基本信息", Parent1_1, Parent1_2, Parent1_3, Parent1_4, Parent1_5);
////    Button Parent2 = new Button("班级信息", Parent2_1, Parent2_2, Parent2_3, Parent2_4, Parent2_5);
////    Button Parent3 = new Button("综合信息", Parent3_1, Parent3_2, Parent3_3, Parent3_4, Parent3_5);
////
////    List<Button> parents = new ArrayList<Button>();
////    parents.add(Parent1);
////    parents.add(Parent2);
////    parents.add(Parent3);
////    //new WeixinProxy().createMenu(26, parents);
////
////    // 教师的相关功能
////    Button Teacher1_1 = new Button("绑定班级", CONNECT, ButtonType.view);
////    Button Teacher1_2 = new Button("绑定课程", CONNECT, ButtonType.view);
////    Button Teacher1_3 = new Button("信息修改", CONNECT, ButtonType.view);
////    Button Teacher1_4 = new Button("学生信息", CONNECT, ButtonType.view);
////    Button Teacher1_5 = new Button("家长信息", CONNECT, ButtonType.view);
////
////    Button Teacher2_1 = new Button("发布作业", CONNECT, ButtonType.view);
////    Button Teacher2_2 = new Button("发布公告", CONNECT, ButtonType.view);
////    Button Teacher2_3 = new Button("发布投票", CONNECT, ButtonType.view);
////    Button Teacher2_4 = new Button("发布红榜", CONNECT, ButtonType.view);
////    Button Teacher2_5 = new Button("发布成绩", CONNECT, ButtonType.view);
////
////    Button Teacher3_1 = new Button("发起缴费", CONNECT, ButtonType.view);
////    Button Teacher3_2 = new Button("缴费详情", CONNECT, ButtonType.view);
////    Button Teacher3_3 = new Button("请假信息", CONNECT, ButtonType.view);
////    Button Teacher3_4 = new Button("测试菜单9", CONNECT, ButtonType.view);
////    Button Teacher3_5 = new Button("测试菜单0", CONNECT, ButtonType.view);
////
////    Button Teacher1 = new Button("基本信息", Teacher1_1, Teacher1_2, Teacher1_3, Teacher1_4, Teacher1_5);
////    Button Teacher2 = new Button("信息发布", Teacher2_1, Teacher2_2, Teacher2_3, Teacher2_4, Teacher2_5);
////    Button Teacher3 = new Button("综合信息", Teacher3_1, Teacher3_2, Teacher3_3, Teacher3_4, Teacher3_5);
////
////    List<Button> teachers = new ArrayList<Button>();
////    teachers.add(Teacher1);
////    teachers.add(Teacher2);
////    teachers.add(Teacher3);
////
////    //new WeixinProxy().createMenu(27, teachers);
//
//  }
//
//}
