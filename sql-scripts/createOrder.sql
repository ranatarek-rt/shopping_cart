USE `shopping_db`;
drop table if exists `order_item`;
drop table if exists `orders`;

CREATE TABLE `orders` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_date` DATE,
    `total_amount` DECIMAL(19, 2),
    `order_status` VARCHAR(255),
    `user_id` BIGINT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)

);

CREATE TABLE `order_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `quantity` INT,
    `price` DECIMAL(19, 2),
	`order_id` BIGINT, -- Reference to the `order` table
    `product_id` BIGINT, -- Reference to the `product` table
    PRIMARY KEY (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);
