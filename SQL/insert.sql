ALTER TABLE `cafe` AUTO_INCREMENT = 1;
ALTER TABLE `post` AUTO_INCREMENT = 1;
ALTER TABLE `reply` AUTO_INCREMENT = 1;

INSERT INTO `cafe` (`name`) VALUES ('test cafe1'), ('2nd cafe');
INSERT INTO `post` (`cid`, `content`) VALUES (1, '포스트'), (1, '새 글'), (1, 'NEW'), (2, 'new'), (2, '하이하이');
INSERT INTO `reply` (`pid`, `content`) VALUES (2, '댓글'), (2, '댓글2'), (4, '안녕');
INSERT INTO `nudge` (`cid`, `contents`) VALUES (1, '가을가을한 오늘, 당신의 추천곡은?'), (1, 'coffea에 가끔 나타나는 여신을 본 적이 있나요?'), (1, '나만 아는 우리동네 깨알꿀팁이 있나요?'), (1, '커피는 언제 마셔야 제맛일까요?');