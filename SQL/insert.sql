ALTER TABLE `cafe` AUTO_INCREMENT = 1;
ALTER TABLE `post` AUTO_INCREMENT = 1;
ALTER TABLE `reply` AUTO_INCREMENT = 1;

INSERT INTO `cafe` (`name`) VALUES ('test cafe1'), ('2nd cafe');
INSERT INTO `post` (`cid`, `content`) VALUES (1, '포스트'), (1, '새 글'), (1, 'NEW'), (2, 'new'), (2, '하이하이');
INSERT INTO `reply` (`pid`, `content`) VALUES (2, '댓글'), (2, '댓글2'), (4, '안녕');