CREATE TABLE `car` (
`id` int NOT NULL AUTO_INCREMENT,
`number` varchar(255) CHARACTER SET utf8 NULL COMMENT '车牌号',
`brand` varchar(255) CHARACTER SET utf8 NULL COMMENT '品牌',
`remark` varchar(255) CHARACTER SET utf8 NULL COMMENT '备注',
PRIMARY KEY (`id`) 
);

CREATE TABLE `person` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
`openId` varchar(255) CHARACTER SET utf8 NULL COMMENT '微信号标识',
`name` varchar(255) CHARACTER SET utf8 NULL COMMENT '姓名',
`phone` varchar(255) CHARACTER SET utf8 NULL COMMENT '手机号码',
PRIMARY KEY (`id`) 
);

CREATE TABLE `own` (
`person_id` int NOT NULL COMMENT '人员序号',
`car_id` int NOT NULL COMMENT '车辆序号',
PRIMARY KEY (`person_id`, `car_id`) 
);

CREATE TABLE `fix` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
`car_id` int NULL COMMENT '车辆序号',
`mileage` int NULL COMMENT '里程数',
`money` double NULL COMMENT '金额汇总',
`detail` varchar(21000) CHARACTER SET utf8 NULL COMMENT '维修记录',
`time` datetime NULL COMMENT '修理时间',
`time_next` datetime NULL COMMENT '下次保养时间',
`remark` varchar(255) CHARACTER SET utf8 NULL COMMENT '备注',
PRIMARY KEY (`id`) 
);

CREATE TABLE `item` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
`name` varchar(255) CHARACTER SET utf8 NULL COMMENT '项目名称',
PRIMARY KEY (`id`) 
);

CREATE TABLE `detail` (
`fix_id` int NOT NULL COMMENT '维修序号',
`item_id` int NOT NULL COMMENT '项目序号',
`money` double NULL COMMENT '维修金额',
`detail` varchar(255) CHARACTER SET utf8 NULL COMMENT '维修明细',
PRIMARY KEY (`fix_id`, `item_id`) 
);


ALTER TABLE `own` ADD CONSTRAINT `car` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`);
ALTER TABLE `own` ADD CONSTRAINT `person` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`);
ALTER TABLE `fix` ADD CONSTRAINT `fix_car` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`);

