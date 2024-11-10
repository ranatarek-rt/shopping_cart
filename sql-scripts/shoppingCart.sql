DROP SCHEMA IF EXISTS `shopping_db`;
CREATE SCHEMA   `shopping_db`;

use `shopping_db`;

--     table creation


    CREATE TABLE `category`(
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(256) NOT NULL,
        primary key (`id`)
    );
    
    CREATE TABLE `product`(
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(256),
        `brand` VARCHAR(256),
        `description` VARCHAR(256),
        `price` DECIMAL(19, 2),
        `inventory_quantity` INT,
        `category_id` BIGINT,
         primary key (`id`),
        FOREIGN KEY (`category_id`) REFERENCES category(`id`) ON DELETE SET NULL
    );


    CREATE TABLE `image`(
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `file_name` VARCHAR(256),
        `file_path` VARCHAR(256),
        `product_image` LONGBLOB,
        `download_url` VARCHAR(256),
        `product_id` BIGINT,
        primary key (`id`),
        FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE
    );