use enjoytrip;

 CREATE TABLE `attraction_description` (
   `content_id` int NOT NULL,
   `homepage` varchar(100) DEFAULT NULL,
   `overview` varchar(10000) DEFAULT NULL,
   `telname` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`content_id`),
   CONSTRAINT `attraction_detail_to_attraciton_id_fk` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
 CREATE TABLE `attraction_detail` (
   `content_id` int NOT NULL,
   `cat1` varchar(3) DEFAULT NULL,
   `cat2` varchar(5) DEFAULT NULL,
   `cat3` varchar(9) DEFAULT NULL,
   `created_time` varchar(14) DEFAULT NULL,
   `modified_time` varchar(14) DEFAULT NULL,
   `booktour` varchar(5) DEFAULT NULL,
   PRIMARY KEY (`content_id`),
   CONSTRAINT `attraction_detail_to_basic_content_id_fk` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
 CREATE TABLE `attraction_info` (
   `content_id` int NOT NULL,
   `content_type_id` int DEFAULT NULL,
   `title` varchar(100) DEFAULT NULL,
   `addr1` varchar(100) DEFAULT NULL,
   `addr2` varchar(50) DEFAULT NULL,
   `zipcode` varchar(50) DEFAULT NULL,
   `tel` varchar(50) DEFAULT NULL,
   `first_image` varchar(200) DEFAULT NULL,
   `first_image2` varchar(200) DEFAULT NULL,
   `readcount` int DEFAULT NULL,
   `sido_code` int DEFAULT NULL,
   `gugun_code` int DEFAULT NULL,
   `latitude` decimal(20,17) DEFAULT NULL,
   `longitude` decimal(20,17) DEFAULT NULL,
   `mlevel` varchar(2) DEFAULT NULL,
   PRIMARY KEY (`content_id`),
   KEY `attraction_to_content_type_id_fk_idx` (`content_type_id`),
   KEY `attraction_to_sido_code_fk_idx` (`sido_code`),
   KEY `attraction_to_gugun_code_fk_idx` (`gugun_code`),
   CONSTRAINT `attraction_to_content_type_id_fk` FOREIGN KEY (`content_type_id`) REFERENCES `content_type` (`content_type_id`),
   CONSTRAINT `attraction_to_gugun_code_fk` FOREIGN KEY (`gugun_code`) REFERENCES `gugun` (`gugun_code`),
   CONSTRAINT `attraction_to_sido_code_fk` FOREIGN KEY (`sido_code`) REFERENCES `sido` (`sido_code`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
  CREATE TABLE `attraction_content_types` (
   `content_type_id` int DEFAULT NULL,
   `content_type_name` varchar(20) DEFAULT NULL,
   KEY `content_type_id` (`content_type_id`),
   CONSTRAINT `attraction_content_types_ibfk_1` FOREIGN KEY (`content_type_id`) REFERENCES `attraction_info` (`content_type_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
CREATE TABLE `sido` (
   `sido_code` int NOT NULL,
   `sido_name` varchar(30) DEFAULT NULL,
   PRIMARY KEY (`sido_code`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
 CREATE TABLE `gugun` (
   `gugun_code` int NOT NULL,
   `gugun_name` varchar(30) DEFAULT NULL,
   `sido_code` int NOT NULL,
   PRIMARY KEY (`gugun_code`,`sido_code`),
   KEY `gugun_to_sido_sido_code_fk_idx` (`sido_code`),
   CONSTRAINT `gugun_to_sido_sido_code_fk` FOREIGN KEY (`sido_code`) REFERENCES `sido` (`sido_code`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
 CREATE TABLE `members` (
   `user_id` varchar(20) NOT NULL,
   `user_password` varchar(300) NOT NULL,
   `user_name` varchar(20) NOT NULL,
   `user_nickname` varchar(20) NOT NULL,
   `user_email` varchar(45) NOT NULL,
   `user_grade` varchar(20) NOT NULL DEFAULT 'general',
   `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `token` varchar(1000) DEFAULT NULL,
   PRIMARY KEY (`user_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `board` (
   `article_no` int NOT NULL AUTO_INCREMENT,
   `user_id` varchar(20) DEFAULT NULL,
   `subject` varchar(45) NOT NULL,
   `content` varchar(500) NOT NULL,
   `hit` int NOT NULL DEFAULT '0',
   `favorite` int NOT NULL DEFAULT '0',
   `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `category_id` int NOT NULL,
   PRIMARY KEY (`article_no`),
   KEY `user_id` (`user_id`),
   CONSTRAINT `board_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `boardcategory` (
   `category_id` int NOT NULL AUTO_INCREMENT,
   `category_name` varchar(20) NOT NULL,
   PRIMARY KEY (`category_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `comments` (
   `comment_id` int NOT NULL AUTO_INCREMENT,
   `user_id` varchar(20) NOT NULL,
   `comment` varchar(500) NOT NULL,
   `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `article_no` int NOT NULL,
   PRIMARY KEY (`comment_id`),
   KEY `article_no` (`article_no`),
   CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`article_no`) REFERENCES `board` (`article_no`) ON DELETE CASCADE
 ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
  CREATE TABLE `hotplace` (
   `article_no` int NOT NULL AUTO_INCREMENT,
   `user_id` varchar(20) DEFAULT NULL,
   `content_id` int DEFAULT NULL,
   `memo` varchar(200) DEFAULT NULL,
   `hit` int DEFAULT '0',
   `regist_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`article_no`),
   KEY `user_id` (`user_id`),
   KEY `content_id` (`content_id`),
   CONSTRAINT `hotplace_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`) ON DELETE SET NULL,
   CONSTRAINT `hotplace_ibfk_2` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
CREATE TABLE `favoritehotplace` (
   `favorite_id` int NOT NULL AUTO_INCREMENT,
   `user_id` varchar(20) DEFAULT NULL,
   `article_no` int DEFAULT NULL,
   PRIMARY KEY (`favorite_id`),
   KEY `user_id` (`user_id`),
   KEY `article_no` (`article_no`),
   CONSTRAINT `favoritehotplace_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`) ON DELETE SET NULL,
   CONSTRAINT `favoritehotplace_ibfk_2` FOREIGN KEY (`article_no`) REFERENCES `hotplace` (`article_no`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `hotplaceid` (
   `content_id` int NOT NULL,
   PRIMARY KEY (`content_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 
 
CREATE TABLE `plan` (
   `plan_id` int NOT NULL AUTO_INCREMENT,
   `title` varchar(50) NOT NULL,
   `content` varchar(100) NOT NULL,
   `user_id` varchar(20) NOT NULL,
   `start_date` varchar(15) NOT NULL,
   `end_date` varchar(15) NOT NULL,
   `article_no` int DEFAULT NULL,
   PRIMARY KEY (`plan_id`),
   KEY `article_no` (`article_no`),
   CONSTRAINT `plan_ibfk_1` FOREIGN KEY (`article_no`) REFERENCES `board` (`article_no`)
 ) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
CREATE TABLE `plan_detail` (
   `idx` int NOT NULL AUTO_INCREMENT,
   `plan_id` int DEFAULT NULL,
   `plan_order` int NOT NULL,
   `content_id` int DEFAULT NULL,
   `memo` varchar(100) DEFAULT NULL,
   PRIMARY KEY (`idx`),
   KEY `plan_id` (`plan_id`),
   KEY `content_id` (`content_id`),
   CONSTRAINT `plan_detail_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`) ON DELETE CASCADE,
   CONSTRAINT `plan_detail_ibfk_2` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
CREATE TABLE `file_info` (
   `idx` int NOT NULL AUTO_INCREMENT,
   `article_no` int DEFAULT NULL,
   `save_folder` varchar(45) DEFAULT NULL,
   `original_file` varchar(50) DEFAULT NULL,
   `save_file` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`idx`),
   KEY `file_info_to_board_article_no_fk` (`article_no`),
   CONSTRAINT `file_info_to_board_article_no_fk` FOREIGN KEY (`article_no`) REFERENCES `board` (`article_no`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
insert attraction_content_types(content_type_id, content_type_name)
values(12, "관광지"),
(14, "문화시설"),
(15, "축제공연행사"),
(25, "여행코스"),
(28, "레포츠"),
(32, "숙박"),
(38, "쇼핑"),
(39, "음식점");

insert into boardcategory(category_name)
values("공지사항"),
("질문게시판"),
("자유게시판"),
("여행후기게시판");