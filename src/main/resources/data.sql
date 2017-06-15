   INSERT INTO `boot`.`user` (`user_id`, `email`) VALUES (1, 'user@wp.pl');
   INSERT INTO `boot`.`account` (`account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`,  `password`, `account_name`, `user_user_id`)
     VALUES (1, 1, 1 ,1 , '$2a$06$/C7d8OVdFRXhJP6N7cP0SuK7WfIxAAlJaUjicmzNFkMzCUlMl5YMW', 'user', 1);
   INSERT INTO `boot`.`authority_roles` (`ACCOUNT_ID`, `roles`) VALUES (1, 'USER');



   INSERT INTO `boot`.`user` (`user_id`, `email`) VALUES (2, 'admin@wp.pl');
   INSERT INTO `boot`.`account` (`account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`, `password`, `account_name`, `user_user_id`)
    VALUES (1, 1, 1 ,1 , '$2a$06$hFrTBlsnFsZhiZfK2mPnLe0.9c0zsDH.jNcUGiIN28hik7l0FcrOy','admin', 2);
   INSERT INTO `boot`.`authority_roles` (`ACCOUNT_ID`, `roles`) VALUES (2, 'ADMIN');