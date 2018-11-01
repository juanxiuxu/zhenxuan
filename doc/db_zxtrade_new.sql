DROP DATABASE `db_zxtrade`;
CREATE DATABASE  `db_zxtrade` DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

USE `db_zxtrade`;

DROP TABLE IF EXISTS `tbl_coupon_code`;
CREATE TABLE `tbl_coupon_code` (
  `id` varchar(255) NOT NULL,
  `coupon_code` varchar(255) NOT NULL,
  `coupon_id` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `active_at` varchar(255) NOT NULL,
  `expired_at` varchar(255) NOT NULL,
  `num` bigint(20) NOT NULL,
  `active_num` bigint(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `tbl_coupon_template`;
CREATE TABLE `tbl_coupon_template` (
  `coupon_id` varchar(255) NOT NULL,
  `limit` bigint(20) NOT NULL,
  `num` bigint(20) NOT NULL,
  `active_num` bigint(20) NOT NULL,
  `mode` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `active_at` varchar(255) NOT NULL,
  `expired_type` tinyint(4) NOT NULL,
  `expired_info` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `background` varchar(255) NOT NULL,
  `condition` bigint(20) NOT NULL,
  `fit` varchar(255) NOT NULL,
  `information` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_coupon_wallet`;
CREATE TABLE `tbl_coupon_wallet` (
  `owner_id` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `coupon_id` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `active_at` datetime NOT NULL,
  `expired_at` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `condition` bigint(20) NOT NULL,
  `fit` varchar(255) NOT NULL,
  `information` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`owner_id`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_draw_back`;
CREATE TABLE `tbl_draw_back` (
  `trade_id` varchar(255) NOT NULL,
  `auth_uid` varchar(64) NOT NULL,
  `code` tinyint(4) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `paid_at` datetime NOT NULL,
  `card_id` varchar(255) NOT NULL,
  `bank_name` varchar(255) NOT NULL,
  `bank_code` varchar(255) NOT NULL,
  `real_name` varchar(255) NOT NULL,
  `fail_code` varchar(255) NOT NULL,
  `fail_reason` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_order`;
CREATE TABLE `tbl_order` (
  `oid` varchar(32) NOT NULL,
  `auth_uid` varchar(64) NOT NULL,
  `tid` varchar(255) NOT NULL DEFAULT '0',
  `iuid` varchar(255) NOT NULL DEFAULT '0',
  `vip` tinyint(4) NOT NULL DEFAULT '0',
  `order_status` int(11) unsigned NOT NULL,
  `code` varchar(255) NOT NULL DEFAULT 'INIT',
  `op` varchar(255) NOT NULL,
  `recheck` varchar(255) NOT NULL DEFAULT 'ACCESS',
  `total` int(11) NOT NULL COMMENT '商品价格减去优惠折扣加上运费减去运费折扣后的价格',
  `goods_total` int(11) NOT NULL COMMENT '价格起始',
  `cashback` int(11) NOT NULL COMMENT 'member=2的用户需要从total直接减它得到最终价格',
  `bonus` int(11) NOT NULL COMMENT '暂无用',
  `coupon_code` varchar(255) NOT NULL,
  `coupon_discount` int(11) NOT NULL,
  `freight` int(11) NOT NULL,
  `freight_discount` int(11) NOT NULL,
  `payment` int(11) NOT NULL COMMENT '微信支付的价格，相当于pay_order amount',
  `user_balance_consume` int(11) NOT NULL COMMENT '余额消耗',
  `discount` int(11) NOT NULL,
  `actual_payment` int(11) NOT NULL COMMENT '暂无用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';

DROP TABLE IF EXISTS `tbl_order_express`;
CREATE TABLE `tbl_order_express` (
  `order_express_id` bigint(20) unsigned NOT NULL,
  `oid` varchar(32) NOT NULL,
  `success_time` datetime NOT NULL COMMENT '签收时间',
  `success_type` tinyint(4) NOT NULL COMMENT '主动或自动签收',
  `paid_at` datetime NOT NULL,
  `pay_oid` varchar(255) NOT NULL,
  `express_id` varchar(255) NOT NULL,
  `express_brand` varchar(255) NOT NULL,
  `delivered_at` datetime NOT NULL,
  `reverser_id` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `county` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_express_id`),
  UNIQUE KEY `uk_oid` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单快递表';

DROP TABLE IF EXISTS `tbl_order_item`;
CREATE TABLE `tbl_order_item` (
  `order_item_id` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `auth_uid` varchar(64) NOT NULL,
  `op` varchar(255) NOT NULL,
  `sid` varchar(255) NOT NULL COMMENT 'sku_id唯一',
  `barcode` varchar(255) NOT NULL,
  `spu_id` varchar(255) NOT NULL,
  `spu_name` varchar(255) NOT NULL,
  `sku_name` varchar(255) NOT NULL,
  `cover` varchar(255) NOT NULL,
  `num` int(11) NOT NULL,
  `unit_price` int(11) NOT NULL,
  `code` tinyint(4) NOT NULL,
  `r_num` int(11) NOT NULL,
  `tid` varchar(255) NOT NULL,
  `iuid` varchar(255) NOT NULL,
  `eva_status` tinyint(4) NOT NULL,
  `comment_id` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_product`;
CREATE TABLE `tbl_product` (
  `sku_id` varchar(255) NOT NULL,
  `spu_id` varchar(255) NOT NULL,
  `sales_count` int(11) unsigned NOT NULL DEFAULT 0,
  `stock_count` int(11) unsigned NOT NULL DEFAULT 0,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_pay_trade`;
CREATE TABLE `tbl_pay_trade` (
  `pay_trade_id` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `auth_uid` varchar(64) NOT NULL,
  `amount` bigint(20) NOT NULL COMMENT '统一下单原始总金额',
  `prepay_id` varchar(255) NOT NULL COMMENT '微信发通知用',
  `ip` varchar(255) NOT NULL,
  `result_code` varchar(255) NOT NULL DEFAULT 'INIT' COMMENT '微信支付业务结果',
  `transaction_id` varchar(255) NOT NULL DEFAULT '' COMMENT '微信交易单号',
  `total_fee` bigint(20) NOT NULL DEFAULT 0 COMMENT '微信订单总金额',
  `fee_type` varchar(128) NOT NULL DEFAULT 'CNY' COMMENT '微信货币类型',
  `cash_fee` bigint(20) NOT NULL DEFAULT 0 COMMENT '微信订单总金额',
  `settlement_total_fee` bigint(20) NOT NULL DEFAULT 0 COMMENT '应结订单金额',
  `is_subscribe` varchar(16) NOT NULL DEFAULT '' COMMENT '是否关注公众号',
  `wx_open_id` varchar(255) NOT NULL DEFAULT '' COMMENT '支付时需要',
  `product_desc` varchar(255) NOT NULL DEFAULT '',
  `trade_type` varchar(255) NOT NULL DEFAULT '' COMMENT '微信交易类型',
  `bank_type` varchar(255) NOT NULL DEFAULT '' COMMENT '微信付款银行',
  `paid_at` varchar(64) NOT NULL DEFAULT '' COMMENT '微信交易完成时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pay_trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='微信支付交易表';

DROP TABLE IF EXISTS `tbl_reverse_order`;
CREATE TABLE `tbl_reverse_order` (
  `reverse_id` varchar(255) NOT NULL,
  `auth_uid` varchar(64) NOT NULL,
  `wx_open_idop` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `item` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `messages` varchar(255) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `pics` varchar(255) NOT NULL,
  `tel` varchar(255) NOT NULL,
  `reverse_detail` varchar(255) NOT NULL,
  `logistics_pic` varchar(255) NOT NULL,
  `express_id` varchar(255) NOT NULL,
  `express_brand` varchar(255) NOT NULL,
  `return_msg` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`reverse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_user_balance_bill`;
CREATE TABLE `tbl_user_balance_bill` (
  `bill_id` varchar(255) NOT NULL,
  `auth_uid` varchar(64) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `income` tinyint(4) NOT NULL COMMENT '1加 0减',
  `init_balance` bigint(20) NOT NULL,
  `final_balance` bigint(20) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `complete_status` tinyint(4) NOT NULL,
  `balance_status` tinyint(4) NOT NULL,
  `cancel_status` tinyint(4) NOT NULL,
  `completed_at` datetime NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT '哪种账单',
  `oid` varchar(255) NOT NULL,
  `pay_oid` varchar(255) NOT NULL,
  `describe` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户余额流水表';

DROP TABLE IF EXISTS `tbl_user_login`;
CREATE TABLE `tbl_user_login` (
  `login_uid` varchar(64) NOT NULL COMMENT '登陆临时uid',
  `wx_app_type` varchar(64) NOT NULL COMMENT '小程序type',
  `wx_open_id` varchar(255) NOT NULL,
  `balance` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `unionid` varchar(255) NOT NULL DEFAULT '', 
  `fwh_open_id` varchar(255) NOT NULL DEFAULT '',
  `tid` varchar(255) NOT NULL DEFAULT '',
  `iuid` varchar(255) NOT NULL DEFAULT '',
  `wx_session_key` varchar(255) NOT NULL,
  `last_login` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`login_uid`),
  UNIQUE KEY `uk_wxapptype_wxopenid` (`wx_app_type`,`wx_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='浏览用户表';

DROP TABLE IF EXISTS `tbl_user_auth`;
CREATE TABLE `tbl_user_auth` (
  `auth_uid` varchar(64) NOT NULL,
  `unionid` varchar(255) NOT NULL,
  `wx_app_type` varchar(64) NOT NULL COMMENT '小程序type',
  `wx_open_id` varchar(255) NOT NULL,
  `balance` bigint(20) unsigned NOT NULL DEFAULT 0,
  `member` tinyint(4) NOT NULL DEFAULT 0,
  `fwh_open_id` varchar(255) NOT NULL DEFAULT '' COMMENT '服务号openid，可能多个',
  `expired` bigint(20) NOT NULL DEFAULT 0 COMMENT '身份过期时间',
  `tid` varchar(255) NOT NULL DEFAULT 0 COMMENT '进店id',
  `iuid` varchar(255) NOT NULL COMMENT '介绍人id ',
  `card_id` varchar(255) NOT NULL DEFAULT '',
  `bank_name` varchar(255) NOT NULL DEFAULT '',
  `bank_code` varchar(255) NOT NULL DEFAULT '',
  `real_name` varchar(255) NOT NULL DEFAULT '',
  `phone` varchar(255) NOT NULL DEFAULT '',
  `avatar` varchar(255) NOT NULL DEFAULT '',
  `nick_name` varchar(255) NOT NULL DEFAULT '',
  `gender` tinyint(4) NOT NULL DEFAULT 100,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`auth_uid`),
  UNIQUE KEY `uk_unionid` (`unionid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='认证用户表';


DROP TABLE IF EXISTS `tbl_z_temp_balance`;
CREATE TABLE `tbl_z_temp_balance` (
  `id` varchar(255) NOT NULL,
  `balance` BIGINT(20) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_z_temp_user`;
CREATE TABLE `tbl_z_temp_user` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `cust_nickname` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `cust_openid` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `parentid` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `balance` double NOT NULL,
  `sys_id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
