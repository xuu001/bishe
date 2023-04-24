-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: eepc
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acc_role`
--

DROP TABLE IF EXISTS `acc_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountId` int NOT NULL COMMENT '账号id',
  `roleId` int NOT NULL COMMENT '权限id',
  `createDate` datetime NOT NULL,
  `createUserId` varchar(100) DEFAULT NULL,
  `createUserName` char(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `acc_role_FK` (`accountId`),
  KEY `acc_role_FK_1` (`roleId`),
  CONSTRAINT `acc_role_FK_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账号菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_role`
--

LOCK TABLES `acc_role` WRITE;
/*!40000 ALTER TABLE `acc_role` DISABLE KEYS */;
INSERT INTO `acc_role` VALUES (1,1,1,'2022-04-09 00:00:00','1','HHANG'),(2,2,3,'2022-04-09 00:00:00','1','HHANG'),(3,3,3,'2022-04-09 00:00:00','1','HHANG'),(13,19,3,'2022-05-17 22:44:54','1','贾航航');
/*!40000 ALTER TABLE `acc_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` char(50) NOT NULL COMMENT '账号',
  `password` char(100) NOT NULL COMMENT '密码',
  `name` char(50) NOT NULL COMMENT '名字',
  `status` tinyint NOT NULL COMMENT '状态: 1启用   0禁用',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `createName` char(50) NOT NULL COMMENT '创建人',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyName` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人名称',
  `isDel` tinyint NOT NULL COMMENT '乐观锁  0在职未删  1离职删除',
  `version` int NOT NULL COMMENT '版本号',
  `gender` int DEFAULT NULL COMMENT '性别 0男 1女',
  `age` int DEFAULT NULL COMMENT '年龄',
  `address` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '家庭住址',
  `phone` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `isAdmin` int NOT NULL COMMENT '是否管理员 0否 1是',
  `mail` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin','A8173BA7A0D0E62328152E24566F36DF1F10632ABABFCA30A404C741BD334FE4','HHANG',1,'2022-04-09 13:23:26','管理员','2022-05-14 16:05:19',NULL,0,3,0,21,'吉林省长春市','2959175211',1,'hhang52@qq.com'),(2,'10001','A8173BA7A0D0E62328152E24566F36DF1F10632ABABFCA30A404C741BD334FE4','张艺馨',1,'2022-04-09 13:23:26','管理员','2022-05-16 16:30:29','HHANG',0,20,0,21,'河南省驻马店市','308476787',0,'hhang52@vip.qq.com'),(3,'10002','A8173BA7A0D0E62328152E24566F36DF1F10632ABABFCA30A404C741BD334FE4','贾小航',1,'2022-04-09 13:23:26','管理员','2022-05-16 16:30:25','HHANG',0,17,0,20,'河南省驻马店市','2959175211',0,'hhang52@vip.qq.com'),(19,'202205175403','A8173BA7A0D0E62328152E24566F36DF1F10632ABABFCA30A404C741BD334FE4','刘德华',1,'2022-05-17 22:44:48','HHANG',NULL,NULL,0,0,NULL,NULL,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_role`
--

DROP TABLE IF EXISTS `auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `authId` int NOT NULL,
  `roleId` int NOT NULL,
  `createDate` date NOT NULL,
  `createUserId` int NOT NULL,
  `createUserName` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_role_FK_1` (`roleId`),
  KEY `auth_role_FK` (`authId`),
  CONSTRAINT `auth_role_FK` FOREIGN KEY (`authId`) REFERENCES `authority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `auth_role_FK_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role`
--

LOCK TABLES `auth_role` WRITE;
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role` VALUES (1,1,1,'2022-05-03',1,'HHANG'),(2,2,1,'2022-05-03',1,'HHANG'),(3,3,1,'2022-05-03',1,'HHANG'),(4,4,1,'2022-05-03',1,'HHANG'),(5,5,1,'2022-05-03',1,'HHANG'),(6,6,1,'2022-05-03',1,'HHANG'),(7,7,1,'2022-05-03',1,'HHANG'),(8,8,1,'2022-05-03',1,'HHANG'),(9,9,1,'2022-05-03',1,'HHANG'),(10,10,1,'2022-05-03',1,'HHANG'),(11,11,1,'2022-05-03',1,'HHANG'),(12,12,1,'2022-05-03',1,'HHANG'),(13,13,1,'2022-05-03',1,'HHANG'),(14,14,1,'2022-05-03',1,'HHANG'),(15,15,1,'2022-05-03',1,'HHANG'),(16,16,1,'2022-05-03',1,'HHANG'),(17,17,1,'2022-05-03',1,'HHANG'),(18,18,1,'2022-05-03',1,'HHANG'),(19,19,1,'2022-05-03',1,'HHANG'),(20,20,1,'2022-05-03',1,'HHANG'),(21,21,1,'2022-05-03',1,'HHANG'),(22,22,1,'2022-05-03',1,'HHANG'),(24,1,3,'2022-05-16',1,'HHANG'),(25,2,3,'2022-05-16',1,'HHANG'),(26,7,3,'2022-05-16',1,'HHANG'),(27,8,3,'2022-05-16',1,'HHANG'),(28,9,3,'2022-05-16',1,'HHANG'),(29,11,3,'2022-05-16',1,'HHANG'),(30,12,3,'2022-05-16',1,'HHANG'),(31,13,3,'2022-05-16',1,'HHANG'),(32,15,3,'2022-05-16',1,'HHANG'),(33,16,3,'2022-05-16',1,'HHANG'),(34,17,3,'2022-05-16',1,'HHANG'),(35,19,3,'2022-05-16',1,'HHANG'),(36,20,3,'2022-05-16',1,'HHANG'),(37,21,3,'2022-05-16',1,'HHANG');
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL,
  `type` int NOT NULL COMMENT '菜单类型',
  `text` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称文字',
  `url` char(100) DEFAULT NULL COMMENT '路径',
  `icon` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=558 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,NULL,1,'首页','/home/data','el-icon-s-home'),(2,NULL,1,'疫情服务','/home/epidemic','el-icon-s-marketing'),(3,NULL,1,'部门管理','/home/department','el-icon-menu'),(4,NULL,0,'员工管理','','el-icon-user-solid'),(5,4,1,'员工管理','/home/user','el-icon-user'),(6,4,1,'员工权限','/home/authority','el-icon-setting'),(7,NULL,0,'打卡管理','','el-icon-s-claim'),(8,7,1,'每日打卡','/home/daka','el-icon-document-add'),(9,7,1,'我的记录','/home/dakalist','el-icon-document'),(10,7,1,'所有记录','/home/dakaAllList','el-icon-document'),(11,NULL,0,'行程管理','','el-icon-location'),(12,11,1,'行程上报','/home/trip','el-icon-add-location'),(13,11,1,'我的记录','/home/triplist','el-icon-map-location'),(14,11,1,'员工记录','/home/tripAllList','el-icon-location-information'),(15,NULL,0,'外出管理','','el-icon-s-cooperation'),(16,15,1,'外出申请','/home/outApply','el-icon-first-aid-kit'),(17,15,1,'申请记录','/home/outlist','el-icon-suitcase-1'),(18,15,1,'申请审批','/home/applications','el-icon-finished'),(19,NULL,0,'复工管理','','el-icon-s-check'),(20,19,1,'复工申请','/home/rework','el-icon-chat-dot-square'),(21,19,1,'申请记录','/home/reworklist','el-icon-chat-line-square'),(22,19,1,'申请审批','/home/reworkApplication','el-icon-finished');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `depa_acc`
--

DROP TABLE IF EXISTS `depa_acc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `depa_acc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `departmentId` int NOT NULL,
  `accountId` int NOT NULL,
  `createDate` datetime NOT NULL COMMENT '创建时间/入职时间',
  PRIMARY KEY (`id`),
  KEY `depa_acc_FK` (`accountId`),
  KEY `depa_acc_FK_1` (`departmentId`),
  CONSTRAINT `depa_acc_FK` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `depa_acc_FK_1` FOREIGN KEY (`departmentId`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depa_acc`
--

LOCK TABLES `depa_acc` WRITE;
/*!40000 ALTER TABLE `depa_acc` DISABLE KEYS */;
INSERT INTO `depa_acc` VALUES (1,1,1,'2022-04-15 00:00:00'),(2,2,2,'2022-04-15 00:00:00'),(3,2,3,'2022-04-15 00:00:00'),(4,3,19,'2022-05-17 22:44:53');
/*!40000 ALTER TABLE `depa_acc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department` char(50) NOT NULL,
  `count` int NOT NULL COMMENT '部门人数',
  `version` int NOT NULL,
  `createDate` date NOT NULL,
  `createUserId` int NOT NULL,
  `createUserName` char(50) NOT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'技术部',1,0,'2022-04-15',1,'贾航航',NULL,'01'),(2,'财务部',2,0,'2022-04-16',1,'贾航航',NULL,'02'),(3,'市场部',1,0,'2022-05-16',1,'贾航航',NULL,'03');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `go_out`
--

DROP TABLE IF EXISTS `go_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `go_out` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountId` int NOT NULL,
  `destination` char(100) NOT NULL COMMENT '目的地',
  `departureTime` date NOT NULL COMMENT '预计出发日',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `createUserName` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `transport` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '预乘交通工具',
  `outTime` char(50) NOT NULL COMMENT '外出几天',
  `remarks` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `version` int NOT NULL,
  `statue` tinyint NOT NULL COMMENT '状态 0未审批 1通过审批  2审核驳回',
  `modifyDate` date DEFAULT NULL,
  `modifyUserName` varchar(50) DEFAULT NULL,
  `reason` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`id`),
  KEY `go_out_FK` (`accountId`),
  CONSTRAINT `go_out_FK` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外出申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `go_out`
--

LOCK TABLES `go_out` WRITE;
/*!40000 ALTER TABLE `go_out` DISABLE KEYS */;
INSERT INTO `go_out` VALUES (1,1,'北京市通州区九棵树东路115站','2022-04-24','2022-04-23 21:46:02','贾航航','火车','1天','',0,1,'2022-04-23','航管理','可以'),(2,1,'苍南QW36路神宫村(神宫桥)','2022-04-26','2022-04-25 17:51:52','贾航航','高铁','一周','',4,0,NULL,'',''),(3,1,'南关区二道街','2022-04-25','2022-04-25 17:54:36','贾航航','自驾','1天','',2,0,NULL,'',''),(4,1,'北京市朝阳区阜通西大街望京西园三区314号','2022-04-25','2022-04-25 17:57:47','贾航航','飞机','7天','',1,2,'2022-05-20','贾航航',''),(5,1,'吉林省长春市南关区世光路197号c区','2022-05-06','2022-05-04 15:53:59','贾航航','自行车','一天','',1,1,'2022-05-15','贾航航',''),(6,19,'我要去北京出差','2022-05-19','2022-05-18 14:31:30','刘德华','高铁','外出大概2天','我会注意安全',1,1,'2022-05-18','贾航航',''),(7,3,'长春市朝阳区西安大路2008号典石广场','2022-05-23','2022-05-20 14:39:02','贾小航','飞机Aeel','三四天','',1,1,'2022-05-20','贾航航','');
/*!40000 ALTER TABLE `go_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountId` int NOT NULL,
  `time` datetime NOT NULL COMMENT '打卡时间',
  `healthy` int NOT NULL COMMENT '健康状况 0健康 1不健康',
  `temperature` char(30) NOT NULL COMMENT '体温',
  `quarantine` int NOT NULL COMMENT '是否隔离  0否 1是',
  `isLive` int NOT NULL COMMENT '是否离开本市 0否 1是',
  `remarks` char(100) DEFAULT NULL COMMENT '备注',
  `version` int NOT NULL,
  `address` char(100) NOT NULL COMMENT '当前地址',
  PRIMARY KEY (`id`),
  KEY `report_FK` (`accountId`),
  CONSTRAINT `report_FK` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='每日打卡上报';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (11,2,'2022-04-14 21:46:45',0,'99',1,1,'',0,'一汽48街区48街区'),(12,1,'2022-04-13 23:29:02',0,'36.5',1,0,'写',0,'吉林省长春市二道区东荣大路2670号'),(13,2,'2022-04-16 23:29:40',0,'99',1,1,'写不写呢',0,'长春市绿园区青荫路302号长春市第八十八中学'),(14,1,'2022-04-14 17:09:32',0,'36.5',1,1,'可可不屑',0,'吉林省长春市二道区东荣大路2670号'),(15,1,'2022-04-15 19:28:16',0,'35',1,1,'科协',0,'吉林市昌邑区新邑西区65栋新邑西区-65栋'),(19,1,'2022-04-16 22:54:29',1,'38',1,1,'春春加油',0,'吉林省长春市二道区东荣大路2670号隔离'),(20,1,'2022-04-17 01:32:55',0,'35.6',0,0,'凌晨你好',0,'北京市东城区新中街12号北京市第五十五中学'),(21,1,'2022-04-19 00:25:13',0,'33',0,0,'2',0,'吉林省长春市重庆路1255号卓展购物中心(重庆路店)F323区iCB'),(22,1,'2022-04-19 00:26:20',0,'36.5',0,0,'',0,'吉林省长春市重庆路1255号卓展购物中心(重庆路店)F323区iCB'),(23,1,'2022-04-20 13:40:57',0,'36.5',0,0,'今天吃了辣椒炒肉',0,'吉林省长春市二道区东荣大路2670号'),(24,1,'2022-04-20 19:53:51',0,'41',1,1,'',0,'长春市朝阳区西康路巴比伦快捷酒店(桂林路店)西南侧12day'),(25,1,'2022-04-21 18:55:31',0,'36',0,0,'nIHAO',0,'吉林省长春市二道区东荣大路2670号'),(26,1,'2022-04-21 19:21:54',0,'36.5',1,0,'可不写',0,'吉林省长春市二道区东荣大路2670号'),(27,1,'2022-04-22 00:08:48',0,'36.5',0,0,'晚安呐 ♥宝',0,'吉林省吉林市龙潭区十六中学北50-65栋'),(28,1,'2022-04-22 23:46:43',0,'33.6',0,0,'今天健康啊吉林人感染少了啊',0,'吉林省长春市二道区东荣大路2670号'),(29,2,'2022-05-03 20:25:42',0,'36.5',0,0,'今天吃了烤串啊！',0,'吉林省长春市二道区东荣大路2670号'),(30,1,'2022-05-03 19:30:37',0,'36.5',0,0,'今天写了啥呢？新功能吧',0,'吉林省长春市二道区广德街318号'),(31,1,'2022-05-03 19:30:37',0,'36',0,0,'今天写了啥呢？新功能吧',0,'吉林省长春市二道区广德街318号'),(32,1,'2022-05-12 12:38:09',0,'36.5',0,0,'',0,'长春朝阳区银联商务吉林分公司'),(33,3,'2022-05-13 12:38:09',0,'36.5',1,0,'',0,'长春朝阳区银联商务吉林分公司'),(34,1,'2022-05-13 08:51:30',0,'36.6',1,0,'',0,'长春二道区我的家'),(35,2,'2022-05-13 08:51:30',0,'36.6',0,0,'',0,'长春二道区我的家'),(37,1,'2022-05-15 00:14:05',0,'36.3',0,0,'',0,'吉林省长春市二道区东荣大路2670号'),(38,1,'2022-05-16 14:20:20',0,'36.5',0,0,'测试，想要上线部署',0,'长春朝阳区典石广场'),(39,19,'2022-05-17 23:33:58',0,'36.5',0,0,'新增员工打卡！！',0,'吉林省长春市'),(40,1,'2022-05-17 23:34:47',0,'36.4',0,0,'完善新增员工登录！！',0,'吉林省长春市二道区东荣大路2670号'),(41,1,'2022-05-18 11:02:29',0,'35.6',0,0,'',0,'吉林省长春市朝阳区安达街88号'),(42,19,'2022-05-18 14:44:12',0,'35.6',0,1,'已经外出申请',0,'现在正要出差去北京'),(43,1,'2022-05-20 10:49:28',0,'36.5',0,0,'111',0,'吉林省长春市朝阳区安达街88号'),(48,3,'2022-05-20 14:15:34',0,'36',0,0,'',0,'黑龙江省哈尔滨市南岗区文昌街151号阿萨帝ktv(文昌店)');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_work`
--

DROP TABLE IF EXISTS `return_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_work` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountId` int NOT NULL,
  `createUserName` char(50) NOT NULL,
  `createDate` datetime NOT NULL,
  `quarantine` tinyint NOT NULL COMMENT '是否隔离过 0否 1是',
  `status` tinyint NOT NULL COMMENT '状态 0未审核 1审核通过 2审核未通过',
  `returnPlace` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '返回地点',
  `nucleicAcid` tinyint DEFAULT NULL COMMENT '核酸结果 0阴性 1阳性',
  `file` char(100) DEFAULT NULL COMMENT '核酸证明报告',
  `remakes` char(100) DEFAULT NULL COMMENT '备注',
  `version` int NOT NULL,
  `returnDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `return_work_FK` (`accountId`),
  CONSTRAINT `return_work_FK` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='复工申请';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_work`
--

LOCK TABLES `return_work` WRITE;
/*!40000 ALTER TABLE `return_work` DISABLE KEYS */;
INSERT INTO `return_work` VALUES (1,1,'贾航航','2022-05-21 00:54:31',0,1,'长春啊',0,'0a28215e-b570-400a-bd57-5b2e7286c37d.png',NULL,1,'2022-05-21 08:38:06');
/*!40000 ALTER TABLE `return_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL,
  `type` int NOT NULL,
  `createDate` date NOT NULL,
  `createUserId` int NOT NULL,
  `createUserName` char(50) NOT NULL,
  `modifyDate` date DEFAULT NULL,
  `nidifyUserId` int DEFAULT NULL,
  `modifyUserName` char(50) DEFAULT NULL,
  `isDel` tinyint NOT NULL,
  `verdion` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员',0,'2022-04-09',1,'HHANG','2022-04-09',1,'HHANG',0,0),(2,'部长',0,'2022-04-09',1,'HHANG','2022-04-09',1,'HHANG',0,0),(3,'员工',0,'2022-04-09',1,'HHANG','2022-04-09',1,'HHANG',0,0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `destination` char(100) NOT NULL COMMENT '目的地',
  `departureTime` datetime NOT NULL COMMENT '出发时间',
  `accountId` int NOT NULL COMMENT '关联账号表',
  `transport` char(50) NOT NULL COMMENT '交通工具',
  `arrivalTime` datetime NOT NULL COMMENT '达到时间',
  `returnTime` datetime NOT NULL COMMENT '回来时间',
  `returnTransport` char(50) NOT NULL COMMENT '回来所用交通工具',
  `isRisk` int NOT NULL COMMENT '风险区 0不是 1中级 2高级',
  `version` int NOT NULL,
  `remarks` char(100) DEFAULT NULL COMMENT '备注',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyUser` char(50) DEFAULT NULL COMMENT '修改人姓名',
  `modifyUserId` int DEFAULT NULL,
  `isDel` tinyint NOT NULL COMMENT '0未删 1删',
  PRIMARY KEY (`id`),
  KEY `trip_FK` (`accountId`),
  CONSTRAINT `trip_FK` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='历史行程表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
INSERT INTO `trip` VALUES (7,'吉林省长春市宽城区丹东路小区234号楼丹东路小区-234号楼','2022-04-06 01:00:00',1,'234','2022-04-06 00:00:00','2022-03-30 00:00:00','火车',0,0,'234','2022-04-19 00:22:10',NULL,NULL,NULL,0),(8,'吉林省长春市路','2022-04-19 02:00:00',1,'21','2022-04-05 00:00:00','2022-04-12 00:00:00','火车',0,0,NULL,'2022-04-19 00:24:29',NULL,NULL,NULL,0),(9,'吉林省长春市九台区101路','2022-04-06 06:00:00',2,'21','2022-04-05 00:00:00','2022-04-12 00:00:00','火车',0,0,NULL,'2022-04-19 00:24:29',NULL,NULL,NULL,0),(10,'吉林省长春市宽城区丹东区-234号楼','2022-04-06 00:50:00',2,'飞机','2022-04-06 00:00:00','2022-03-30 00:00:00','234',0,0,'234','2022-04-19 00:22:10',NULL,NULL,NULL,0),(11,'吉林省长春市绿园区红领巾路677号南校区八十七中小学南校区','2022-04-01 10:00:00',1,'高铁','2022-04-06 00:00:00','2022-04-13 00:00:00','火车',1,0,'路程劳累','2022-04-20 16:53:45',NULL,NULL,NULL,0),(12,'北京市北京市','2022-04-07 08:00:01',1,'飞机+公交车','2022-04-07 00:00:00','2022-04-21 00:00:00','高铁',0,0,NULL,'2022-04-21 19:24:12',NULL,NULL,NULL,0),(13,'吉林省长春市九台区101路','2022-05-04 00:00:00',1,'火车','2022-05-05 00:00:00','2022-05-06 00:00:00','火车',0,0,NULL,'2022-05-06 21:43:37',NULL,NULL,NULL,0),(14,'吉林省吉林市船营区紫晶城34栋紫晶城-34栋','2022-05-02 00:00:00',1,'3','2022-05-03 00:00:00','2022-05-04 00:00:00','高铁',1,0,'34','2022-05-06 21:43:57',NULL,NULL,NULL,0),(15,'吉林市船营区三道沟','2022-05-01 00:00:00',1,'火车','2022-05-03 00:00:00','2022-05-05 00:00:00','高铁',0,0,'34','2022-05-06 21:44:13',NULL,NULL,NULL,0),(16,'长春市二道区长春市二道','2022-05-02 00:00:00',1,'高铁1111','2022-05-04 00:00:00','2022-05-10 00:00:00','自行车11111',2,0,NULL,'2022-05-15 00:16:11',NULL,NULL,NULL,0),(17,'北京','2022-05-18 00:00:00',19,'高铁','2022-05-18 00:00:00','2022-05-18 00:00:00','高铁',1,0,'报备一下','2022-05-18 14:43:14',NULL,NULL,NULL,0),(18,'吉林市吉林市','2022-05-04 00:00:00',3,'火车','2022-05-05 00:00:00','2022-05-07 00:00:00','火车',1,0,'保护安全','2022-05-20 14:57:41',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'eepc'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-07 13:36:12
