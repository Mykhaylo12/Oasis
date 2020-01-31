CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE TABLE `internet_shop`.`items`
(
    `item_id` INT           NOT NULL,
    `name`    VARCHAR(256)  NOT NULL,
    `price`   DECIMAL(6, 2) NOT NULL,
    PRIMARY KEY (`item_id`)
);

ALTER TABLE `internet_shop`.`items`
    CHANGE COLUMN `item_id` `item_id` INT NOT NULL AUTO_INCREMENT;

SELECT *
FROM internet_shop.items
where item_id = 1;

CREATE TABLE `internet_shop`.`orders`
(
    `order_id` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`order_id`)
);

CREATE TABLE `internet_shop`.`orders_items_id`
(
    `orders_items_id` INT NOT NULL AUTO_INCREMENT,
    `order_id`        INT NOT NULL,
    `items_id`        INT NOT NULL,
    PRIMARY KEY (`orders_items_id`),
    INDEX `orders_items_order_id_idx` (`order_id` ASC) VISIBLE,
    INDEX `orders_items_items_id_idx` (`items_id` ASC) VISIBLE,
    CONSTRAINT `orders_items_order_id`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `orders_items_items_id`
        FOREIGN KEY (`items_id`)
            REFERENCES `internet_shop`.`items` (`item_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`users`
(
    `user_id`  INT          NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(45)  NOT NULL,
    `password` VARCHAR(45)  NOT NULL,
    `email`    VARCHAR(256) NULL,
    `name`     VARCHAR(256) NULL,
    `token`    VARCHAR(256) NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE
);

ALTER TABLE `internet_shop`.`orders`
    ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
    ADD INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`orders`
    ADD CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `internet_shop`.`users_roles`
(
    `id`      INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    INDEX `ui_idx` (`user_id` ASC) VISIBLE,
    INDEX `ri_idx` (`role_id` ASC) VISIBLE,
    PRIMARY KEY (`id`),
    CONSTRAINT `ui`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `ri`
        FOREIGN KEY (`role_id`)
            REFERENCES `internet_shop`.`roles` (`role_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE `internet_shop`.`bukets`
(
    `user_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `item_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`items` (`item_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`buckets`
(
    `user_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    INDEX `item_id_idx` (`item_id` ASC) VISIBLE,
    CONSTRAINT `user_user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `item_item_id`
        FOREIGN KEY (`item_id`)
            REFERENCES `internet_shop`.`items` (`item_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`orders`
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `user_id`  INT NOT NULL,
    `item_id`  INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `uo`
        FOREIGN KEY (`id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `io`
        FOREIGN KEY (`id`)
            REFERENCES `internet_shop`.`items` (`item_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE `internet_shop`.`buckets_items`
(
    `id`        INT NOT NULL AUTO_INCREMENT,
    `bucket_id` INT NOT NULL,
    `item_id`   INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `bibi_idx` (`bucket_id` ASC) VISIBLE,
    INDEX `biii_idx` (`item_id` ASC) VISIBLE,
    CONSTRAINT `bibi`
        FOREIGN KEY (`bucket_id`)
            REFERENCES `internet_shop`.`buckets` (`bucket_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `biii`
        FOREIGN KEY (`item_id`)
            REFERENCES `internet_shop`.`items` (`item_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_bin;