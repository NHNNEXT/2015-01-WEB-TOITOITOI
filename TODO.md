[x] remove Post.name and use Dear
[x] or id.integer -> _id.ObjectId
[x] page --;
[x] INDEX - dear.unique(placeId,name), dear.sort(placeId,postNum), post.sort(likes,replyNum)
[x] 브라우저 주소 바꾸기. id 말고 name!
[ ] imagefile.postId -> post.imageIds, reply.imageIds

* CREATE post
  * newPost를 하나 만든다.
  * 파일이 있으면
    * 성공해야한다. newPost.파일id = 성공한파일.id;
    * 실패했으면 return Result.failed();
  * 그 외에는(파일 없거나, 있는데 성공했으면)
    * newPost.dearId = findDearbyName;
      * 없으면 새로 insert한 뒤 return;
    * insert(newPost);
    * return Result.success();
   * 추가로, filedao.updatePostId(); 과정이 없어졌으니
    * getFile에서 반영해야한다.
* imagefileS to post, reply
* replyList at APIReplyController, not postView(not in use now)


* 이미지 메타데이터 저장하기. [참고](http://johnbokma.com/java/obtaining-image-metadata.html)
* controller 나누기
* package 나누기
* Page. 여러 메소드 - isLast 등 - 활용
* placeid jsp에 넣지말고 js에서 새로 요청받기. 둘다 string형이므로 서버에서 try-and-fail 식으로 응답줘야한다.
* Post.replyList 없애고 요청 나눠서.
* Image.도 .jsp말고 ajax로 요청 나눠서.
