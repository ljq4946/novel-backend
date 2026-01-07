-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: novel_rolinsf
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author_info`
--

DROP TABLE IF EXISTS `author_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `pen_name` varchar(20) NOT NULL COMMENT '笔名',
  `tel_phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `chat_account` varchar(50) DEFAULT NULL COMMENT 'QQ或微信账号',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '0：正常;1-封禁',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId` (`user_id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作者信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author_info`
--

LOCK TABLES `author_info` WRITE;
/*!40000 ALTER TABLE `author_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `author_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_category`
--

DROP TABLE IF EXISTS `book_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `work_direction` tinyint unsigned NOT NULL COMMENT '作品方向;0-投稿 1-征文 2-接龙',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名',
  `sort` tinyint unsigned NOT NULL DEFAULT '10' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `pk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说类别';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_category`
--

LOCK TABLES `book_category` WRITE;
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_chapter`
--

DROP TABLE IF EXISTS `book_chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_chapter` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `book_id` bigint unsigned NOT NULL COMMENT '小说ID',
  `chapter_num` smallint unsigned NOT NULL COMMENT '章节号',
  `chapter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '章节名',
  `word_count` int unsigned NOT NULL COMMENT '章节字数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_bookId_chapterNum` (`book_id`,`chapter_num`) USING BTREE,
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_bookId` (`book_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1445988184596992001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说章节';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_chapter`
--

LOCK TABLES `book_chapter` WRITE;
/*!40000 ALTER TABLE `book_chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_comment`
--

DROP TABLE IF EXISTS `book_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_comment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_id` bigint unsigned NOT NULL COMMENT '评论小说ID',
  `user_id` bigint unsigned NOT NULL COMMENT '评论用户ID',
  `comment_content` varchar(512) NOT NULL COMMENT '评价内容',
  `reply_count` int unsigned NOT NULL DEFAULT '0' COMMENT '回复数量',
  `audit_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bookId_userId` (`book_id`,`user_id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_comment`
--

LOCK TABLES `book_comment` WRITE;
/*!40000 ALTER TABLE `book_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_comment_copy1`
--

DROP TABLE IF EXISTS `book_comment_copy1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_comment_copy1` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_id` bigint unsigned NOT NULL COMMENT '评论小说ID',
  `user_id` bigint unsigned NOT NULL COMMENT '评论用户ID',
  `comment_content` varchar(512) NOT NULL COMMENT '评价内容',
  `reply_count` int unsigned NOT NULL DEFAULT '0' COMMENT '回复数量',
  `audit_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bookId_userId` (`book_id`,`user_id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_comment_copy1`
--

LOCK TABLES `book_comment_copy1` WRITE;
/*!40000 ALTER TABLE `book_comment_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_comment_copy1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_comment_reply`
--

DROP TABLE IF EXISTS `book_comment_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_comment_reply` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `comment_id` bigint unsigned NOT NULL COMMENT '评论ID',
  `user_id` bigint unsigned NOT NULL COMMENT '回复用户ID',
  `reply_content` varchar(512) NOT NULL COMMENT '回复内容',
  `audit_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说评论回复';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_comment_reply`
--

LOCK TABLES `book_comment_reply` WRITE;
/*!40000 ALTER TABLE `book_comment_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_comment_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_content`
--

DROP TABLE IF EXISTS `book_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_content` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chapter_id` bigint unsigned NOT NULL COMMENT '章节ID',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说章节内容',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_chapterId` (`chapter_id`) USING BTREE,
  UNIQUE KEY `pk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4256332 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_content`
--

LOCK TABLES `book_content` WRITE;
/*!40000 ALTER TABLE `book_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_info`
--

DROP TABLE IF EXISTS `book_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint unsigned DEFAULT NULL COMMENT '类别ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类别名',
  `pic_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说封面地址',
  `book_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说名',
  `author_id` bigint unsigned NOT NULL COMMENT '作家id',
  `author_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作家名',
  `book_desc` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍描述',
  `score` tinyint unsigned NOT NULL COMMENT '评分;总分:10 ，真实评分 = score/10',
  `book_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '书籍状态;0-连载中 1-已完结',
  `visit_count` bigint unsigned NOT NULL DEFAULT '103' COMMENT '点击量',
  `word_count` int unsigned NOT NULL DEFAULT '0' COMMENT '总字数',
  `comment_count` int unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
  `last_chapter_id` bigint unsigned DEFAULT NULL COMMENT '最新章节ID',
  `last_chapter_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最新章节名',
  `last_chapter_update_time` datetime DEFAULT NULL COMMENT '最新章节更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_bookName_authorName` (`book_name`,`author_name`) USING BTREE,
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_createTime` (`create_time`) USING BTREE,
  KEY `idx_lastChapterUpdateTime` (`last_chapter_update_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1431630596354977793 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_info`
--

LOCK TABLES `book_info` WRITE;
/*!40000 ALTER TABLE `book_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `home_book`
--

DROP TABLE IF EXISTS `home_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `home_book` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint unsigned NOT NULL COMMENT '推荐类型;0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐',
  `sort` tinyint unsigned NOT NULL COMMENT '推荐排序',
  `book_id` bigint unsigned NOT NULL COMMENT '推荐小说ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说推荐';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `home_book`
--

LOCK TABLES `home_book` WRITE;
/*!40000 ALTER TABLE `home_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `home_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_category`
--

DROP TABLE IF EXISTS `news_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news_category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '类别名',
  `sort` tinyint unsigned NOT NULL DEFAULT '10' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻类别';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_category`
--

LOCK TABLES `news_category` WRITE;
/*!40000 ALTER TABLE `news_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `news_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_content`
--

DROP TABLE IF EXISTS `news_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news_content` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `news_id` bigint unsigned NOT NULL COMMENT '新闻ID',
  `content` mediumtext NOT NULL COMMENT '新闻内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_newsId` (`news_id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_content`
--

LOCK TABLES `news_content` WRITE;
/*!40000 ALTER TABLE `news_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `news_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_info`
--

DROP TABLE IF EXISTS `news_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint unsigned NOT NULL COMMENT '类别ID',
  `category_name` varchar(50) NOT NULL COMMENT '类别名',
  `source_name` varchar(50) NOT NULL COMMENT '新闻来源',
  `title` varchar(100) NOT NULL COMMENT '新闻标题',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_info`
--

LOCK TABLES `news_info` WRITE;
/*!40000 ALTER TABLE `news_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `news_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int unsigned DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父菜单ID;一级菜单为0',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `type` tinyint unsigned NOT NULL COMMENT '类型;0-目录   1-菜单',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `sort` tinyint unsigned DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  `menu_id` bigint unsigned NOT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色与菜单对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `sex` tinyint unsigned DEFAULT NULL COMMENT '性别;0-男 1-女',
  `birth` datetime DEFAULT NULL COMMENT '出身日期',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态;0-禁用 1-正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';
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
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与角色对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bookshelf`
--

DROP TABLE IF EXISTS `user_bookshelf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bookshelf` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `book_id` bigint unsigned NOT NULL COMMENT '小说ID',
  `pre_content_id` bigint unsigned DEFAULT NULL COMMENT '上一次阅读的章节内容表ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间;',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间;',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_bookId` (`user_id`,`book_id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户书架';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bookshelf`
--

LOCK TABLES `user_bookshelf` WRITE;
/*!40000 ALTER TABLE `user_bookshelf` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_bookshelf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '登录密码-加密',
  `salt` varchar(8) NOT NULL COMMENT '加密盐值',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `user_photo` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `user_sex` tinyint unsigned DEFAULT NULL COMMENT '用户性别;0-男 1-女',
  `status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '用户状态;0-正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_read_history`
--

DROP TABLE IF EXISTS `user_read_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_read_history` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `book_id` bigint unsigned NOT NULL COMMENT '小说ID',
  `pre_content_id` bigint unsigned NOT NULL COMMENT '上一次阅读的章节内容表ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间;',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间;',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_bookId` (`user_id`,`book_id`),
  UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户阅读历史';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_read_history`
--

LOCK TABLES `user_read_history` WRITE;
/*!40000 ALTER TABLE `user_read_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_read_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'novel_rolinsf'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-07 12:10:45
