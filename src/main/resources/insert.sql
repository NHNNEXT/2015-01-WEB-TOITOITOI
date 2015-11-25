ALTER TABLE `cafe` AUTO_INCREMENT = 1;
ALTER TABLE `post` AUTO_INCREMENT = 1;
ALTER TABLE `reply` AUTO_INCREMENT = 1;

INSERT INTO `cafe` (`name`) VALUES ('test cafe1'), ('2nd cafe');
INSERT INTO `post` (`cid`, `content`) VALUES (1, '포스트'), (1, '새 글'), (1, 'NEW'), (2, 'new'), (2, '하이하이');
INSERT INTO `reply` (`pid`, `content`) VALUES (2, '댓글'), (2, '댓글2'), (4, '안녕');
INSERT INTO `nudge` (`cid`, `contents`) VALUES (1, '가을가을한 오늘, 당신의 추천곡은?'), (1, 'coffea에 가끔 나타나는 여신을 본 적이 있나요?'), (1, '나만 아는 우리동네 깨알꿀팁이 있나요?'), (1, '커피는 언제 마셔야 제맛일까요?');

----- modified
-- alter table cafe add latitude decimal(18,15);
-- alter table cafe add longitude decimal(18,15);

-- H스퀘어 주변 카페 찾기 : // 우리가 생각하는 '카페(커피!)'만 나오는 건 아니라는 거.. ㅜㅠ
-- https://apis.daum.net/local/v1/search/category.json?apikey={APIkey}&code=CE7&location=37.4012699485866,127.10865382542464&sort=2

INSERT INTO cafe (`name`, `longitude`, `latitude`) VALUES 
("스타벅스 신천역점","127.08123789246142","37.51145336236047"),
("탐앤탐스 신천점","127.07991645166017","37.51112269426271"),
("커피빈 신천역점","127.08169939171292","37.511474668159266"),
("커피니 신천점","127.08226942632798","37.51145264841276");

INSERT INTO cafe (`name`, `longitude`, `latitude`) VALUES 
("오렌지티 본점","127.10865382542464","37.4012699485866"),
("코페아커피","127.10869003938184","37.40132037246726"),
("로뚜뚜커피","127.10873069812676","37.401320335085806"),
("스타벅스 판교H스퀘어","127.10831498721187","37.40125944721568"),
("코페아커피 동판교점","127.1086412975187","37.40197996443511"),
("버블톡","127.10871366367118","37.402037563244804"),
("투썸플레이스 판교테크노밸리점","127.10864594007738","37.40206645815382");