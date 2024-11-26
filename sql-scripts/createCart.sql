use `shopping_db`;


    CREATE TABLE  `cart`(
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `total_amount` DECIMAL(19,2),
         PRIMARY KEY (`id`)
    );

    CREATE TABLE `cart_item`(
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `quantity` INT ,
        `unit_price` DECIMAL(19,2),
        `total_price` DECIMAL(19,2),
        `cart_id` BIGINT,
        `product_id` BIGINT,
        PRIMARY KEY (`id`),
        FOREIGN KEY (`cart_id`) REFERENCES cart(`id`),
        FOREIGN KEY (`product_id`) REFERENCES product(`id`)

    );