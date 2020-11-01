alter table tool_yl_card_t add created_date datetime;
alter table tool_patient_t add family_location VARCHAR(1024);
update tool_yl_card_t set created_date = now();

DROP TABLE IF EXISTS `tool_reimb_print_t`;
CREATE TABLE `tool_reimb_print_t` (
  `oid` int(32) NOT NULL AUTO_INCREMENT COMMENT '报销打印记录表',
  `print_state` varchar(8) DEFAULT NULL COMMENT '打印状态,1001：待打印，1002：已打印',
  `biz_id` varchar(64) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
