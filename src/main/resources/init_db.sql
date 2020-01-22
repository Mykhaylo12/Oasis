CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

CREATE TABLE `internet_shop`.`items` (
  `item_id` INT NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

ALTER TABLE `internet_shop`.`items`
CHANGE COLUMN `item_id` `item_id` INT NOT NULL AUTO_INCREMENT ;

INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('iPhone 10', '950');

SELECT * FROM internet_shop.items where item_id=1;

update internet_shop.items set name='Samsung Galaxy 9' ,price=960 where item_id=1;

insert into internet_shop.items (name,  price) value( 'Xciomi', 550);

delete from items where item_id=12;
