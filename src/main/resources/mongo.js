// mysqldump -uroot -p dearhere place > data.sql
/* mongoimport --db dearhere --collection place --drop --file dump-place.json
\((\d+),'(\\.|[^']++)*'\)(,*)
*
{ "_id" : $1, "name" : "$2" }

*/

/* mongoimport --db dearhere --collection dear --drop --file dump-dear.json
\((\d+),'(\\.|[^']++)*'\)(,*)
*
{ "_id" : $1, "name" : "$2" }

*/

/* mongoimport --db dearhere --collection post --drop --file dump-post.json
\((\d+),'([\d\-]+) ([\d\:]+)','(\\.|[^']++)*',NULL,(\d+),(\d+),(\d+)\)(,*)
*
{ "_id" : $1, "dearId" : $7, "likes" : $5, "content" : "$4", "createdtime" : ISODate("$2T$3Z"), "placeId" : $6 }

*/

/* mongoimport --db dearhere --collection reply --drop --file dump-reply.json
\((\d+),'([\d\-]+) ([\d\:]+)','(\\.|[^']++)*',(\d+),(\d+)\)(,*)
*
{ "_id" : $1, "postId" : $6, "createdtime" : ISODate("$2T$3Z"), "likes" : $5, "content" : "$4" }

*/

// ' 살리려고 한건데 실패.
/* mongoimport --db dearhere --collection imageFile --drop --file dump-imagefile.json
\((\d+),'(\\.|[^']++)*','(\\.|[^']++)*','([\d\-]+) ([\d\:]+)',(\d+)\)(,*)
*
{ "_id" : $1, "attatchedTo" : {"collection" : "post", "_id" : $6}, "storedFilename" : "$3", "originalFilename" : "$2", "createdtime" : ISODate("$4T$5Z") }

*
(?:"attatchedTo" : \{"collection" : "post", "_id" : )(\d+)\}
*
"postId" : $1
*
createdtime
*
storedTime
*/


//function getNextId(tableName) {
//   var ret = db.counters.findAndModify(
//          {
//            query: { _id: ""+tableName+"Id" },
//            update: { $inc: { seq: 1 } },
//            new: true
//          }
//   );
//   return ret.seq;
//}
//
//db.system.js.save(
//   {
//     _id: "getNextId",
//     value : getNextId
//   }
//)
//
//db.users.insert(
//   {
//     _id: getNextSequence("replyId"),
//     name: "Sarah C."
//   }
//)

//db.post.find().snapshot().forEach(function (item) {
//	var prevId = item._id;
//	db.post.update( { _id: prevId }, { $set: { postId: prevId, _id: ObjectId() } } );
//});

db.place.renameCollection("prevPlace");
db.prevPlace.find().forEach(function (item) {
	item.placeId = item._id;
	item._id = ObjectId();
	item.uri = item.name;
	item.totalDearNum = 0;
	db.place.insert(item);
});
db.place.createIndex( { uri:1 } );
db.prevPlace.drop();
//1. unset placeId 2. count totalDearNum

db.dear.update({}, {$set:{ placeId : 1}}, {multi:true});
db.dear.renameCollection("prevDear");
db.prevDear.find().forEach(function (item) {
	item.dearId = item._id;
	item._id = ObjectId();
	item.placeId = db.place.findOne({"placeId" : item.placeId})._id;
	item.uri = item.name;
	item.totalPostNum = 0;
	db.dear.insert(item);
});
//db.dear.createIndex( { placeId:1, "name":1 }, { unique: true } );
db.dear.createIndex( { uri:1 } );
db.prevDear.drop();
//1. unset dearId 2. count totalPostNum

db.post.renameCollection("prevPost");
db.prevPost.find().forEach(function (item) {
	item.postId = item._id;
	item.uri = item.postId;
	item._id = ObjectId();
	item.dearId = db.dear.findOne({"dearId" : item.dearId})._id;
	item.createdTime = item.createdtime;
	item.imageIds = [];
	delete item.placeId;
	delete item.createdtime;
	db.post.insert(item);
});
db.prevPost.drop();
db.post.createIndex( { dearId:1 } ); // 뒤에 like, replyNum 붙을수도.
//1. unset postId 2. count totalReplyNum 3. update imageIds

db.imageFile.renameCollection("prevImage");
db.prevImage.find().forEach(function (item) {
	if (!item.postId) return;
	item.imageId = item._id;
	item._id = ObjectId();
	item.storedName = item.storedFilename;
	item.originalName = item.originalFilename;
	db.post.update({"postId" : item.postId}, {$push : {imageIds: item._id}});
	delete item.postId;
	delete item.storedFilename;
	delete item.originalFilename;
	db.image.insert(item);
});
db.prevImage.drop();
// 그 전 repository 코드 이상하게 들어가고 있음.
// { "_id" : ObjectId("5697fcaf269b0ae7f13cc379"), "_class" : "cafein.file.ImageFile", "originalFilename" : "erd.png", "storedFilename" : "21891d1c6fe24b6db6aac63c1c886baf.png", "imageId" : ObjectId("5694ba57a826c3e2c440aaca") }

db.reply.renameCollection("prevReply");
db.prevReply.find().forEach(function (item) {
	item.replyId = item._id;
	item._id = ObjectId();
	item.postId = db.post.findOne({"postId" : item.postId})._id;
	item.createdTime = item.createdtime;
	item.imageIds = [];
	delete item.createdtime;
	db.reply.insert(item);
});
db.prevReply.drop();
db.reply.createIndex( { postId:1 } ); // 뒤에 like 붙을수도.

db.place.update({}, {$unset:{ placeId : ""}}, {multi:true});
db.dear.update({}, {$unset:{ dearId : ""}}, {multi:true});
db.post.update({}, {$unset:{ postId : ""}}, {multi:true});
db.image.update({}, {$unset:{ imageId : ""}}, {multi:true});
db.reply.update({}, {$unset:{ replyId : ""}}, {multi:true});


function updateChildNum (parentName, childName) {
	db[parentName].find().forEach(function (item) {
		var findOption = {};
		findOption[parentName+"Id"] = item["_id"];
		var num = db[childName].find(findOption).length();

		var setOption = {};
		setOption["total"+ (childName.charAt(0).toUpperCase() + childName.slice(1)) +"Num"] = num;
		db[parentName].update({"_id":item["_id"]}, {$set:setOption});
	});
	// mapReduce 로 updatedNum 친절하게 알려주면.
}

db.system.js.save({_id: "updateChildNum",value : updateChildNum});
updateChildNum("place", "dear");
updateChildNum("dear", "post");
updateChildNum("post", "reply");
