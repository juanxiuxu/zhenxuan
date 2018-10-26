CREATE DATABASE  `db_zxtrade`
DEFAULT CHARACTER SET 'utf8'
COLLATE 'utf8_general_ci';

USE `db_zxtrade`;

DROP TABLE IF EXISTS `coupon_code`;
CREATE TABLE `coupon_code` (
  `id` varchar(255) NOT NULL,
  `coupon_code` varchar(255) NOT NULL,
  `coupon_id` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `activeAt` varchar(255) NOT NULL,
  `expiredAt` varchar(255) NOT NULL,
  `num` bigint(20) NOT NULL,
  `active_num` bigint(20) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `coupon_template`;
CREATE TABLE `coupon_template` (
  `coupon_id` varchar(255) NOT NULL,
  `limit` bigint(20) NOT NULL,
  `num` bigint(20) NOT NULL,
  `active_num` bigint(20) NOT NULL,
  `mode` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `activeAt` varchar(255) NOT NULL,
  `expiredType` tinyint(4) NOT NULL,
  `expiredInfo` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `background` varchar(255) NOT NULL,
  `condition` bigint(20) NOT NULL,
  `fit` varchar(255) NOT NULL,
  `information` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `coupon_wallet`;
CREATE TABLE `coupon_wallet` (
  `owner_id` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `coupon_id` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `activeAt` datetime NOT NULL,
  `expiredAt` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `condition` bigint(20) NOT NULL,
  `fit` varchar(255) NOT NULL,
  `information` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`owner_id`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `draw_back`;
CREATE TABLE `draw_back` (
  `tradeId` varchar(255) NOT NULL,
  `code` tinyint(4) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `wa_open_id` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `paidAt` datetime NOT NULL,
  `cardId` varchar(255) NOT NULL,
  `bankName` varchar(255) NOT NULL,
  `bankCode` varchar(255) NOT NULL,
  `realName` varchar(255) NOT NULL,
  `fail_code` varchar(255) NOT NULL,
  `fail_reason` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`tradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `oid` varchar(255) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `tid` varchar(255) NOT NULL DEFAULT '0',
  `iuid` varchar(255) NOT NULL DEFAULT '0',
  `vip` tinyint(4) NOT NULL DEFAULT '0',
  `code` varchar(255) NOT NULL DEFAULT 'INIT',
  `op` varchar(255) NOT NULL,
  `check` varchar(255) NOT NULL DEFAULT 'ACCESS',
  `total` int(11) NOT NULL,
  `goods_total` int(11) NOT NULL,
  `cashback` int(11) NOT NULL,
  `bonus` int(11) NOT NULL,
  `coupon_code` varchar(255) NOT NULL,
  `coupon_discount` int(11) NOT NULL,
  `freight` int(11) NOT NULL,
  `freight_discount` int(11) NOT NULL,
  `payment` int(11) NOT NULL,
  `user_balance_consume` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  `actual_payment` int(11) NOT NULL,
  `success_time` datetime NOT NULL,
  `success_type` tinyint(4) NOT NULL,
  `paidAt` datetime NOT NULL,
  `pay_oid` varchar(255) NOT NULL,
  `express_id` varchar(255) NOT NULL,
  `express_brand` varchar(255) NOT NULL,
  `deliveredAt` datetime NOT NULL,
  `reverser_id` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `county` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`oid`,`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `op` varchar(255) NOT NULL,
  `sid` varchar(255) NOT NULL,
  `barcode` varchar(255) NOT NULL,
  `spu_id` varchar(255) NOT NULL,
  `spu_name` varchar(255) NOT NULL,
  `sku_name` varchar(255) NOT NULL,
  `cover` varchar(255) NOT NULL,
  `num` int(11) NOT NULL,
  `unit_price` int(11) NOT NULL,
  `cashback` int(11) NOT NULL,
  `freight` int(11) NOT NULL,
  `cashback_type` tinyint(4) NOT NULL,
  `bonus` int(11) NOT NULL,
  `code` tinyint(4) NOT NULL,
  `r_num` int(11) NOT NULL,
  `tid` varchar(255) NOT NULL,
  `iuid` varchar(255) NOT NULL,
  `eva_status` tinyint(4) NOT NULL,
  `comment_id` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `tradeId` varchar(255) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `wa_open_id` varchar(255) NOT NULL,
  `productDescription` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `tradeType` varchar(255) NOT NULL,
  `prepayId` varchar(255) NOT NULL,
  `errorCode` varchar(255) NOT NULL,
  `errorCodeDes` varchar(255) NOT NULL,
  `paidAt` datetime NOT NULL,
  `transactionId` varchar(255) NOT NULL,
  `bankType` varchar(255) NOT NULL,
  `prop` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`tradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reverse_order`;
CREATE TABLE `reverse_order` (
  `uid` varchar(255) NOT NULL,
  `reverse_id` varchar(255) NOT NULL,
  `op` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `item` json NOT NULL,
  `code` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `messages` json NOT NULL,
  `reason` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `pics` json NOT NULL,
  `tel` varchar(255) NOT NULL,
  `reverse_detail` varchar(255) NOT NULL,
  `logistics_pic` varchar(255) NOT NULL,
  `express_id` varchar(255) NOT NULL,
  `express_brand` varchar(255) NOT NULL,
  `return_msg` json NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`uid`,`reverse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_bill`;
CREATE TABLE `user_bill` (
  `uid` varchar(255) NOT NULL,
  `bill_id` varchar(255) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `income` tinyint(4) NOT NULL,
  `init_balance` bigint(20) NOT NULL,
  `final_balance` bigint(20) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `complete_status` tinyint(4) NOT NULL,
  `balance_status` tinyint(4) NOT NULL,
  `cancel_status` tinyint(4) NOT NULL,
  `completedAt` datetime NOT NULL,
  `type` tinyint(4) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `pay_oid` varchar(255) NOT NULL,
  `describe` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`uid`,`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_core`;
CREATE TABLE `user_core` (
  `uid` varchar(255) NOT NULL,
  `balance` bigint(20) NOT NULL,
  `member` tinyint(4) NOT NULL,
  `wa_open_id` varchar(255) NOT NULL,
  `union_id` varchar(255) NOT NULL,
  `fwh_open_id` varchar(255) NOT NULL,
  `expired` bigint(20) NOT NULL,
  `tid` varchar(255) NOT NULL,
  `iuid` varchar(255) NOT NULL,
  `cardId` varchar(255) NOT NULL,
  `bankName` varchar(255) NOT NULL,
  `bankCode` varchar(255) NOT NULL,
  `realName` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `gender` tinyint(4) NOT NULL,
  `wa_session_key` varchar(255) NOT NULL,
  `last_login` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `waOpenIdIndex` (`wa_open_id`) USING BTREE COMMENT '小程序openid索引，每次登录都会调用'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `z_temp_balance`;
CREATE TABLE `z_temp_balance` (
  `id` varchar(255) NOT NULL,
  `balance` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `z_temp_user`;
CREATE TABLE `z_temp_user` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `cust_nickname` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `cust_openid` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `parentid` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `balance` double NOT NULL,
  `sys_id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
