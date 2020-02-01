CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `login` varchar(45) COLLATE utf8_bin NOT NULL,
                         `password` varchar(500) COLLATE utf8_bin NOT NULL,
                         `salt` blob NOT NULL,
                         `email` varchar(256) COLLATE utf8_bin DEFAULT NULL,
                         `name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
                         `token` varchar(256) COLLATE utf8_bin DEFAULT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `buckets` (
                           `bucket_id` int NOT NULL AUTO_INCREMENT,
                           `user_id` int NOT NULL,
                           PRIMARY KEY (`bucket_id`),
                           KEY `bui_idx` (`user_id`),
                           CONSTRAINT `bui` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `items` (
                         `item_id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(256) COLLATE utf8_bin NOT NULL,
                         `price` decimal(6,2) NOT NULL,
                         PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `roles` (
                         `role_id` int NOT NULL,
                         `role_name` varchar(45) COLLATE utf8_bin NOT NULL,
                         PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `buckets_items` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `bucket_id` int NOT NULL,
                                 `item_id` int NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `bibi_idx` (`bucket_id`),
                                 KEY `biii_idx` (`item_id`),
                                 CONSTRAINT `bibi` FOREIGN KEY (`bucket_id`) REFERENCES `buckets` (`bucket_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT `biii` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `users_roles` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `user_id` int NOT NULL,
                               `role_id` int NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `ui_idx` (`user_id`),
                               KEY `ri_idx` (`role_id`),
                               CONSTRAINT `ri` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT `ui` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `orders` (
                          `order_id` int NOT NULL AUTO_INCREMENT,
                          `user_id` int NOT NULL,
                          `total_price` decimal(8,2) NOT NULL,
                          PRIMARY KEY (`order_id`),
                          KEY `orders_users_fk_idx` (`user_id`),
                          CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `orders_items_id` (
                                   `orders_items_id` int NOT NULL AUTO_INCREMENT,
                                   `order_id` int NOT NULL,
                                   `item_id` int NOT NULL,
                                   PRIMARY KEY (`orders_items_id`),
                                   KEY `orders_items_order_id_idx` (`order_id`),
                                   KEY `orders_items_items_id_idx` (`item_id`),
                                   CONSTRAINT `orders_items_items_id` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT `orders_items_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `internet_shop`.`items` (`item_id`, `name`, `price`) VALUES ('1', 'iPhone 10', '1000');
INSERT INTO `internet_shop`.`items` (`item_id`, `name`, `price`) VALUES ('2', 'Samsung Fold', '2100');
INSERT INTO `internet_shop`.`items` (`item_id`, `name`, `price`) VALUES ('3', 'LG K500', '450');
INSERT INTO `internet_shop`.`items` (`item_id`, `name`, `price`) VALUES ('4', 'Xciomi Pro 5', '480');
INSERT INTO `internet_shop`.`items` (`item_id`, `name`, `price`) VALUES ('5', 'Huawei p30', '320');

INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('1', 'ADMIN');
INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('2', 'USER');