* page --;
* imagefile.columnName - createdtime, attachedTo:{collection, id}
* INDEX - dear.unique(placeId,name)
* INDEX - dear.sort(placeId,postNum)
* INDEX - post.sort(likes,replyNum)
* "_id" 바꾸기
* 브라우저 주소 바꾸기. id 말고 name!
* 이미지 메타데이터 저장하기. [참고](http://johnbokma.com/java/obtaining-image-metadata.html)
* controller 나누기
* package 나누기
* id들 Integer -> Long
* Page. 여러 메소드 - isLast 등 - 활용
