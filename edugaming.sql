/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.7.21 : Database - edugaming
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`edugaming` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `edugaming`;

/*Table structure for table `coaches` */

DROP TABLE IF EXISTS `coaches`;

CREATE TABLE `coaches` (
  `coach_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `summary` text COLLATE utf8mb4_unicode_ci,
  `description` text COLLATE utf8mb4_unicode_ci,
  `img_url` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'http://localhost:3000/images/standard_avatar.png',
  `price` int(11) NOT NULL DEFAULT '10',
  `game_id` bigint(20) unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`coach_id`),
  KEY `coaches_game_id_foreign` (`game_id`),
  CONSTRAINT `coaches_game_id_foreign` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `coaches` */

insert  into `coaches`(`coach_id`,`username`,`email`,`password`,`summary`,`description`,`img_url`,`price`,`game_id`,`created_at`,`updated_at`) values 
(1,'Yeet','yeet@yeet.com','$2a$08$q5hrO43MrRtnLgRcgX7LgeJatKbcggNneUec0Yea2YwbYkPyyj88i','4.4k Main Tank player. I coach individuals or teams.',NULL,'http://localhost:3000/images/standard_avatar.png',10,1,'2019-04-12 07:55:01','2019-04-30 16:27:59'),
(2,'Fortnite','fortnite@fortnite.com','$2a$08$3Tl2QFS2Ds6KdGt163VuyOxSZZ0MjmyGwfW.eXJVh/kVWKPJVpjHC',NULL,NULL,'http://localhost:3000/images/standard_avatar.png',10,2,'2019-04-12 07:57:51','2019-04-12 07:57:52');

/*Table structure for table `games` */

DROP TABLE IF EXISTS `games`;

CREATE TABLE `games` (
  `game_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `img_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `old_browser_img_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `games` */

insert  into `games`(`game_id`,`title`,`img_name`,`old_browser_img_name`) values 
(1,'Overwatch','overwatch.webp','overwatch.png'),
(2,'Fortnite','fortnite.webp','fortnite.png'),
(3,'PUBG','PUBG.webp','PUBG.png'),
(4,'Hearthstone','hearthstone.webp','hearthstone.png'),
(5,'League of Legends','lol.webp','lol.png');

/*Table structure for table `migrations` */

DROP TABLE IF EXISTS `migrations`;

CREATE TABLE `migrations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `migrations` */

insert  into `migrations`(`id`,`migration`,`batch`) values 
(124,'2019_03_19_154051_create_ratings_table',1),
(127,'2014_10_12_000000_create_users_table',2),
(128,'2014_10_12_100000_create_password_resets_table',2),
(129,'2016_06_01_000001_create_oauth_auth_codes_table',2),
(130,'2016_06_01_000002_create_oauth_access_tokens_table',2),
(131,'2016_06_01_000003_create_oauth_refresh_tokens_table',2),
(132,'2016_06_01_000004_create_oauth_clients_table',2),
(133,'2016_06_01_000005_create_oauth_personal_access_clients_table',2),
(134,'2019_03_07_113022_create_games_table',2),
(135,'2019_03_18_142116_create_coaches_table',2),
(136,'2019_03_18_143549_create_reviews_table',2),
(137,'2019_03_18_144310_create_reservations_table',2),
(138,'2019_04_12_074516_create_ratings_table',2);

/*Table structure for table `oauth_access_tokens` */

DROP TABLE IF EXISTS `oauth_access_tokens`;

CREATE TABLE `oauth_access_tokens` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `client_id` int(10) unsigned NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scopes` text COLLATE utf8mb4_unicode_ci,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `expires_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oauth_access_tokens_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `oauth_access_tokens` */

/*Table structure for table `oauth_auth_codes` */

DROP TABLE IF EXISTS `oauth_auth_codes`;

CREATE TABLE `oauth_auth_codes` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `client_id` int(10) unsigned NOT NULL,
  `scopes` text COLLATE utf8mb4_unicode_ci,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `oauth_auth_codes` */

/*Table structure for table `oauth_clients` */

DROP TABLE IF EXISTS `oauth_clients`;

CREATE TABLE `oauth_clients` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `secret` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `redirect` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `personal_access_client` tinyint(1) NOT NULL,
  `password_client` tinyint(1) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oauth_clients_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `oauth_clients` */

/*Table structure for table `oauth_personal_access_clients` */

DROP TABLE IF EXISTS `oauth_personal_access_clients`;

CREATE TABLE `oauth_personal_access_clients` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oauth_personal_access_clients_client_id_index` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `oauth_personal_access_clients` */

/*Table structure for table `oauth_refresh_tokens` */

DROP TABLE IF EXISTS `oauth_refresh_tokens`;

CREATE TABLE `oauth_refresh_tokens` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `access_token_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oauth_refresh_tokens_access_token_id_index` (`access_token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `oauth_refresh_tokens` */

/*Table structure for table `password_resets` */

DROP TABLE IF EXISTS `password_resets`;

CREATE TABLE `password_resets` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  KEY `password_resets_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `password_resets` */

/*Table structure for table `ratings` */

DROP TABLE IF EXISTS `ratings`;

CREATE TABLE `ratings` (
  `coach_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `rating` int(11) NOT NULL,
  KEY `ratings_coach_id_foreign` (`coach_id`),
  KEY `ratings_user_id_foreign` (`user_id`),
  CONSTRAINT `ratings_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`coach_id`) ON DELETE CASCADE,
  CONSTRAINT `ratings_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `ratings` */

insert  into `ratings`(`coach_id`,`user_id`,`rating`) values 
(1,1,5),
(2,1,3),
(2,1,4),
(2,1,5),
(2,1,1),
(2,1,4),
(2,1,5),
(2,1,3),
(2,1,5),
(1,3,4),
(1,3,4),
(1,3,1);

/*Table structure for table `reservations` */

DROP TABLE IF EXISTS `reservations`;

CREATE TABLE `reservations` (
  `user_id` bigint(20) unsigned NOT NULL,
  `coach_id` bigint(20) unsigned NOT NULL,
  `reservation_time` datetime NOT NULL,
  `confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  KEY `reservations_user_id_foreign` (`user_id`),
  KEY `reservations_coach_id_foreign` (`coach_id`),
  CONSTRAINT `reservations_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`coach_id`) ON DELETE CASCADE,
  CONSTRAINT `reservations_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `reservations` */

insert  into `reservations`(`user_id`,`coach_id`,`reservation_time`,`confirmed`,`created_at`,`updated_at`) values 
(3,1,'2019-04-27 16:30:00',0,'2019-04-26 12:13:30','2019-04-26 12:13:30'),
(3,1,'2019-04-29 16:30:00',0,'2019-04-26 12:13:33','2019-04-26 12:13:33'),
(3,1,'2019-04-30 16:30:00',1,'2019-04-26 12:13:34','2019-04-26 12:13:34'),
(3,1,'2019-04-28 16:30:00',1,'2019-04-26 12:13:36','2019-04-26 12:13:36'),
(3,1,'2019-05-09 16:30:00',0,'2019-04-26 12:14:41','2019-04-26 12:14:41'),
(3,1,'2019-05-15 16:30:00',0,'2019-04-26 12:14:42','2019-04-26 12:14:42'),
(3,1,'2019-05-16 16:30:00',0,'2019-04-26 12:14:43','2019-04-26 12:14:43'),
(3,1,'2019-05-14 16:30:00',0,'2019-04-26 12:14:44','2019-04-26 12:14:44');

/*Table structure for table `reviews` */

DROP TABLE IF EXISTS `reviews`;

CREATE TABLE `reviews` (
  `review_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `coach_id` bigint(20) unsigned NOT NULL,
  `review` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rating` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `reviews_user_id_foreign` (`user_id`),
  KEY `reviews_coach_id_foreign` (`coach_id`),
  CONSTRAINT `reviews_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`coach_id`) ON DELETE CASCADE,
  CONSTRAINT `reviews_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `reviews` */

insert  into `reviews`(`review_id`,`user_id`,`coach_id`,`review`,`rating`,`created_at`,`updated_at`) values 
(1,1,1,'Had a great coaching session!',5,'2019-04-12 06:38:34','2019-04-12 06:38:34'),
(2,1,2,'Average coaching session.',3,'2019-04-12 07:52:55','2019-04-12 07:52:55'),
(3,1,2,'Good coaching session.',4,'2019-04-12 07:57:56','2019-04-12 07:57:56'),
(4,1,2,'Had alot of fun :).',5,'2019-04-12 08:12:17','2019-04-12 08:12:17'),
(5,1,2,'Coach didn\'t show up...',1,'2019-04-12 08:17:44','2019-04-12 08:17:44'),
(6,1,2,'Although there was a language barrier. The coach did his best!',4,'2019-04-12 08:19:43','2019-04-12 08:19:43'),
(7,1,2,'Coach really did a good job!',5,'2019-04-12 08:25:46','2019-04-12 08:25:46'),
(8,1,2,'Meh, ok I guess.',3,'2019-04-12 08:36:57','2019-04-12 08:36:57');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`name`,`email`,`email_verified_at`,`password`,`remember_token`,`created_at`,`updated_at`) values 
(1,'tester','tester@tester.com',NULL,'$2y$10$jl6aPvRJH8EyqynU/bRIrObvtUJZV1zzEN2NXmk5yMQNvEM/br8tW',NULL,'2019-04-12 06:38:25','2019-04-12 06:38:25'),
(3,'yeet','yeet@yeet.yeet',NULL,'$2y$10$JshnR/pv0H7ikwkxzvg8Fe5ur5zR5GTdbBhB/ttD3TuGBIT9Oi1O2',NULL,'2019-04-26 12:13:17','2019-04-26 12:13:17');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
