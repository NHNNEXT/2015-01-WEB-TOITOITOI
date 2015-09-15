--cafe list, order by reply num -> changed to post num
--SELECT c.cid AS cid, c.name AS name, count(r.reid) AS replies
--	FROM cafe c
--	JOIN post p ON p.cid = c.cid
--	JOIN reply r ON r.pid = p.pid
--	GROUP BY c.cid
--	ORDER BY replies DESC;
	
--cafe list, order by post num
SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts
	FROM cafe c
	JOIN post p ON p.cid = c.cid
	GROUP BY c.cid
	ORDER BY posts DESC;