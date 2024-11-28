USE `shopping_db`;

CREATE TABLE `order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_date` DATE,
    `total_amount` DECIMAL(19, 2),
    `order_status` VARCHAR(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE `order_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT, -- Reference to the `order` table
    `product_id` BIGINT, -- Reference to the `product` table
    `quantity` INT,
    `price` DECIMAL(19, 2),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);
