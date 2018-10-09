SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_role`;
CREATE TABLE `rel_user_role` (
  `id`      int(10) unsigned                        NOT NULL AUTO_INCREMENT,
  `user_id` char(32) COLLATE utf8mb4_unicode_ci     NOT NULL,
  `role`    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id_role` (`user_id`, `role`) USING HASH,
  KEY `idx_user_id` (`user_id`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`         char(32) COLLATE utf8mb4_unicode_ci     NOT NULL,
  `username`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `salt`       varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `points`     int(10) unsigned                        NOT NULL DEFAULT '0',
  `created_at` datetime                                NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
