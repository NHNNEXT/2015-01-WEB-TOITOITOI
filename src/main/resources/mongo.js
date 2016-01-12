/* mongoimport --db dearhere --collection place --drop --file dump-place.json
\((\d+),'([^']+)'\)(,*)
*
{ "_id" : $1, "name" : "$2" }

*/

/* mongoimport --db dearhere --collection dear --drop --file dump-dear.json
\((\d+),'([^']+)'\)(,*)
*
{ "_id" : $1, "name" : "$2" }

*/

/* mongoimport --db dearhere --collection post --drop --file dump-post.json
\((\d+),'([\d\-]+) ([\d\:]+)','([^']+)',NULL,(\d+),(\d+),(\d+)\)(,*)
*
{ "_id" : $1, "dearId" : $7, "likes" : $5, "content" : "$4", "createdtime" : ISODate("$2T$3Z"), "placeId" : $6 }

*/

/* mongoimport --db dearhere --collection reply --drop --file dump-reply.json
\((\d+),'([\d\-]+) ([\d\:]+)','([^']+)',(\d+),(\d+)\)(,*)
*
{ "_id" : $1, "postId" : $6, "createdtime" : ISODate("$2T$3Z"), "likes" : $5, "content" : "$4" }

*/
// m:n -> 1:n 으로 바뀜. 근데 이전 실제 데이터도 1:n이어서 가능한 코드. 같은 dear가 여러 place에 있는 적 x.
db.post.find().forEach(function(item) {
	db.dear.update({"_id":item["dearId"]}, {$set: {placeId:item["placeId"]}});
});

function updateChildNum (parentName, childName) {
	db[parentName].find().forEach(function (item) {
		var findOption = {};
		findOption[parentName+"Id"] = item["_id"];
		var num = db[childName].find(findOption).length();

		var setOption = {};
		setOption["total"+ (childName.charAt(0).toUpperCase() + childName.slice(1)) +"Num"] = num;
		db[parentName].update({"_id":item["_id"]}, {$set:setOption});
	});
}

// updateChildNum("place", "dear");
updateChildNum("dear", "post");
