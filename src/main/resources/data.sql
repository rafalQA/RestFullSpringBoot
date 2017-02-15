   INSERT INTO `boot`.`account` (`account_id`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`,  `account_name`, `password`)
   VALUES (1, 1, 1, 1 ,1 ,'spring', 'spring');

   INSERT INTO `boot`.`user` (`user_id`, `email`, `account_id`) VALUES (1, 'wp@wp.pl', 1);
   INSERT INTO `boot`.`authority_roles` (`account_id`, `roles`) VALUES (1, 'ADMIN');