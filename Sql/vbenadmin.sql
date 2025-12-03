-- MySQL dump 10.13  Distrib 5.7.44, for Linux (x86_64)
--
-- Host: localhost    Database: vbenadmin
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `type` varchar(20) NOT NULL COMMENT '资源类型: catalog/page/button/embedded/link',
  `name` varchar(100) NOT NULL COMMENT '资源名称（唯一）',
  `path` varchar(200) DEFAULT NULL COMMENT '路由路径',
  `component` varchar(200) DEFAULT NULL COMMENT '前端组件路径',
  `redirect` varchar(200) DEFAULT NULL COMMENT '重定向路径',
  `auth_code` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `meta` json DEFAULT NULL COMMENT 'meta 配置(JSON 格式)',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1启用 0禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `idx_pid` (`pid`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=20404 DEFAULT CHARSET=utf8mb4 COMMENT='系统资源（菜单/页面/按钮/内嵌/外链）表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES (1,0,'page','Workspace','/workspace','/dashboard/workspace/index',NULL,NULL,'{\"icon\": \"carbon:workspace\", \"order\": 0, \"title\": \"page.dashboard.workspace\", \"affixTab\": true}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(2,0,'catalog','System','/system',NULL,NULL,NULL,'{\"icon\": \"carbon:settings\", \"badge\": \"new\", \"order\": 9997, \"title\": \"system.title\", \"badgeType\": \"normal\", \"badgeVariants\": \"primary\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(9,0,'catalog','Project','/vben-admin',NULL,NULL,NULL,'{\"icon\": \"carbon:data-center\", \"order\": 9998, \"title\": \"demos.vben.title\", \"badgeType\": \"dot\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(10,0,'page','About','/about','_core/about/index',NULL,NULL,'{\"icon\": \"lucide:copyright\", \"order\": 9999, \"title\": \"demos.vben.about\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(201,2,'page','SystemResource','/system/resource','/system/resource/list',NULL,'System:Resource:List','{\"icon\": \"carbon:menu\", \"title\": \"system.resource.title\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(202,2,'page','SystemDept','/system/dept','/system/dept/list',NULL,'System:Dept:List','{\"icon\": \"carbon:container-services\", \"title\": \"system.dept.title\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(901,9,'embedded','VbenDocument','/vben-admin/document','IFrameView',NULL,NULL,'{\"icon\": \"carbon:book\", \"title\": \"demos.vben.document\", \"iframeSrc\": \"https://doc.vben.pro\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(902,9,'link','VbenGithub','/vben-admin/github','IFrameView',NULL,NULL,'{\"icon\": \"carbon:logo-github\", \"link\": \"https://github.com/vbenjs/vue-vben-admin\", \"title\": \"Github\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(903,9,'link','VbenAntdv','/vben-admin/antdv','IFrameView',NULL,NULL,'{\"icon\": \"carbon:hexagon-vertical-solid\", \"link\": \"https://ant.vben.pro\", \"title\": \"demos.vben.antdv\", \"badgeType\": \"dot\"}',0,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(20101,201,'button','SystemResourceCreate',NULL,NULL,NULL,'System:Resource:Create','{\"title\": \"common.create\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(20102,201,'button','SystemResourceEdit',NULL,NULL,NULL,'System:Resource:Edit','{\"title\": \"common.edit\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(20103,201,'button','SystemResourceDelete',NULL,NULL,NULL,'System:Resource:Delete','{\"title\": \"common.delete\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(20401,202,'button','SystemDeptCreate',NULL,NULL,NULL,'System:Dept:Create','{\"title\": \"common.create\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(20402,202,'button','SystemDeptEdit',NULL,NULL,NULL,'System:Dept:Edit','{\"title\": \"common.edit\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36'),(20403,202,'button','SystemDeptDelete',NULL,NULL,NULL,'System:Dept:Delete','{\"title\": \"common.delete\"}',1,'2025-11-21 14:46:36','2025-11-21 14:46:36');
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` char(36) NOT NULL COMMENT '角色ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '角色备注',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1 启用，0 禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `uk_role_name` (`name`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('0a67b4d5-0f48-4e29-8706-8a1cf0f52e11','Role007','测试角色 007',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('1cda7f88-c18f-4573-bb3d-79c30d30fadc','Role015','测试角色 015',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('27d08a2f-15d3-409e-922e-4571435ff53c','Role001','测试角色 001',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('2b14d9f7-78bb-4fa5-a14a-f38f2e82a3fb','Role029','测试角色 029',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('2b94f577-33e9-4dc7-b8bd-8ef4964d4a34','Role010','测试角色 010',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('2d34e73d-2c92-49ac-8942-9f1d76e0d3a7','Role014','测试角色 014',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('333858ba-be2b-4e34-aa3b-8814dad7b0df','Role013','测试角色 013',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('33704696-a82f-41c7-8993-27a3e2c6e7ee','Role025','测试角色 025',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('3fc79734-1b77-4ed5-8d5b-51ea4c16c38c','Role004','测试角色 004',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('45d1e0c0-5425-4c47-af7e-d28a8432b6ce','Role012','测试角色 012',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('4a812acf-9286-4dab-a14c-b751a92e3f67','Role038','测试角色 038',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('4ab2456d-b1fd-4c8e-9adb-89d7f3b8cd94','Role006','测试角色 006',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('6690fc03-4bab-4b4b-8c0f-988f1c54322b','Role011','测试角色 011',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('6f07c5fa-49ba-46a3-a861-678d2335f5d3','Role036','测试角色 036',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('7573b8fd-89af-4678-a53e-4139be32f247','Role022','测试角色 022',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('7575a55d-5884-4928-9610-82e76a383830','Role019','测试角色 019',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('793e6280-78e2-4dd9-a80d-e29e1d9327a2','Role040','测试角色 040',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('8b3cf4cf-adfa-4fde-a7de-5200b9cf2875','Role037','测试角色 037',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('8ce4a7de-6c91-4d89-8e03-2f943df96b31','Role003','测试角色 003',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('98321990-aaf3-4f52-af58-52cf26c1543c','Role034','测试角色 034',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('9d5dd9a8-2d34-48ad-96b7-980a28dfccfb','Role020','测试角色 020',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('a0327277-5817-4c62-b185-793c4d92e37f','Role030','测试角色 030',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('a0c03902-fc4b-47f2-ae8d-ab00cc09da04','Role018','测试角色 018',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('a2d61a35-26e2-4bfc-92d6-d8e67dbb4ad8','Role031','测试角色 031',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('a477307d-aa1b-4f63-bc13-78be1e6dfb67','Role021','测试角色 021',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('a48de25c-9f49-4dfb-9c92-3c0f3e406a7b','Role026','测试角色 026',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('a8cb74cb-3a87-453f-b73b-a67d3435ef73','Role027','测试角色 027',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('ab9c01fa-6866-48c3-9ec5-4a1b5dfe9e8f','Role032','测试角色 032',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('b2127664-731e-47bf-b6e5-c0c6b2ac6fa5','Role035','测试角色 035',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('b872c3bf-2c53-4c95-9a90-b2c7e833500e','Role039','测试角色 039',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('becd7f7b-01eb-4b3a-8907-c801ca8353fc','Role016','测试角色 016',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('d2f7c917-4b8b-4d3d-a5df-5aa79b7b4be0','Role002','测试角色 002',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('d7ecb20b-ef04-4fae-bb88-8c646a8f7e84','Role023','测试角色 023',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('dd5f2022-4f97-41b9-adea-f1446da6d131','Role017','测试角色 017',0,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('e6cfed85-dcce-4090-ad0b-f62f98af2cdf','Role024','测试角色 024',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('eb5f7bcb-da4b-468f-a630-bdccb6d02e08','Role028','测试角色 028',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('f66a18e6-aadf-4f7c-8a52-e0cc06cfdf12','Role005','测试角色 005',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('f6a1e823-6633-4fb0-9c02-8f6fb4c4cb20','Role033','测试角色 033',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('fa5c2553-9f26-4db8-bd58-3db30eb43e63','Role008','测试角色 008',1,'2025-11-21 15:50:34','2025-11-21 15:50:34'),('fb3ee615-fd4f-4228-9c77-455ab72fa9b4','Role009','测试角色 009',1,'2025-11-21 15:50:34','2025-11-21 15:50:34');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_resource`
--

DROP TABLE IF EXISTS `sys_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_resource` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) NOT NULL COMMENT '资源节点ID',
  UNIQUE KEY `role_id` (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-资源节点关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_resource`
--

LOCK TABLES `sys_role_resource` WRITE;
/*!40000 ALTER TABLE `sys_role_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(200) NOT NULL COMMENT '密码hash',
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `desc_` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `home_path` varchar(200) DEFAULT NULL COMMENT '默认首页路径',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1启用 0禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-03 11:11:18
