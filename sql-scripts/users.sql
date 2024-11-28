use `shopping_db`;

    CREATE TABLE `user`(
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `first_name` varchar(255),
        `last_name` varchar(255),
        `email` varchar(255) unique ,
        `password` varchar(255) ,
         PRIMARY KEY (`id`)
    );

