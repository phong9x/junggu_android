# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.25)
# Database: junggu
# Generation Time: 2015-10-30 08:58:24 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table accessibility
# ------------------------------------------------------------

DROP TABLE IF EXISTS `accessibility`;

CREATE TABLE `accessibility` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(500) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `catName` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `parentId` int(11) NOT NULL DEFAULT '0',
  `image` varchar(500) DEFAULT NULL,
  `etc` text,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table facility
# ------------------------------------------------------------

DROP TABLE IF EXISTS `facility`;

CREATE TABLE `facility` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(500) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table field
# ------------------------------------------------------------

DROP TABLE IF EXISTS `field`;

CREATE TABLE `field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(500) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table notice
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `title` varchar(300) NOT NULL DEFAULT '',
  `content` text,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `noticeType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table store
# ------------------------------------------------------------

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `name` varchar(500) NOT NULL,
  `catId` int(11) NOT NULL,
  `tag` varchar(1000) NOT NULL,
  `serviceHours` varchar(200) NOT NULL,
  `holiday` varchar(200) NOT NULL,
  `buildingForm` varchar(200) NOT NULL,
  `floor` varchar(200) NOT NULL,
  `facilityList` varchar(200) NOT NULL DEFAULT '',
  `representative` varchar(200) NOT NULL,
  `phone` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `monitoring_date` datetime NOT NULL,
  `monitoring_man` varchar(200) NOT NULL,
  `monitoring_man_phone` varchar(200) NOT NULL,
  `fieldList` varchar(200) DEFAULT '',
  `imageBaseAttach` text COMMENT '{\\n  "list": [\\n    "/image/2.jpg",\\n    "/image/2.jpg",\\n    "/image/2.jpg",\\n    "/image/2.jpg",\\n    "/image/2.jpg"\\n  ]\\n}',
  `imageExtendAttach` text COMMENT '{\n  "list": [\n      {\n      "title extra image": [\n        "/image/2.jpg",\n        "/image/2.jpg",\n        "/image/2.jpg",\n        "/image/2.jpg",\n        "/image/2.jpg"\n      ]\n    },\n     {\n      "title extra image": [\n        "/image/2.jpg",\n        "/image/2.jpg",\n        "/image/2.jpg",\n        "/image/2.jpg",\n        "/image/2.jpg"\n      ]\n    }\n  ]\n}',
  `grade` varchar(1000) NOT NULL DEFAULT '',
  `accessibilityList` varchar(200) NOT NULL,
  `longitude` varchar(100) NOT NULL DEFAULT '',
  `latitude` varchar(100) NOT NULL DEFAULT '',
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nickname` varchar(200) DEFAULT '',
  `fullname` varchar(400) DEFAULT NULL,
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '0 is member - 1 is admin',
  `sex` varchar(50) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `email` varchar(400) DEFAULT '',
  `age` int(3) DEFAULT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table version
# ------------------------------------------------------------

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `status` varchar(100) NOT NULL DEFAULT '0',
  `etc` text,
  `updatedDate` datetime DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
