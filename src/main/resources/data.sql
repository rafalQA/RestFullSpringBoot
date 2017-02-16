   INSERT INTO `boot`.`account` (`account_id`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`,  `account_name`, `password`)
   VALUES (1, 1, 1, 1 ,1 ,'user', '$2a$06$/C7d8OVdFRXhJP6N7cP0SuK7WfIxAAlJaUjicmzNFkMzCUlMl5YMW');

   INSERT INTO `boot`.`user` (`user_id`, `email`, `account_id`) VALUES (1, 'user@wp.pl', 1);
   INSERT INTO `boot`.`authority_roles` (`account_id`, `roles`) VALUES (1, 'USER');


   INSERT INTO `boot`.`account` (`account_id`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`,  `account_name`, `password`)
   VALUES (2, 1, 1, 1 ,1 ,'admin', '$2a$06$hFrTBlsnFsZhiZfK2mPnLe0.9c0zsDH.jNcUGiIN28hik7l0FcrOy');

   INSERT INTO `boot`.`user` (`user_id`, `email`, `account_id`) VALUES (2, 'admin@wp.pl', 2);
   INSERT INTO `boot`.`authority_roles` (`account_id`, `roles`) VALUES (2, 'ADMIN');