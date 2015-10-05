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
	LEFT JOIN post p ON p.cid = c.cid
	GROUP BY c.cid
	ORDER BY posts DESC;

-- get cafe list sort by distance
-- http://blog.asamaru.net/2015/09/14/calculate-distance-between-two-wgs84-points/
DROP FUNCTION IF EXISTS distance_between;
CREATE FUNCTION distance_between (from_lat DECIMAL(18, 15), from_lng DECIMAL(18, 15), to_lat DECIMAL(18, 15), to_lng DECIMAL(18, 15)) RETURNS DECIMAL(18, 15)
  RETURN 6371 * 2 * ATAN2(SQRT(POW(SIN(RADIANS(to_lat - from_lat)/2), 2) + POW(SIN(RADIANS(to_lng - from_lng)/2), 2) * COS(RADIANS(from_lat)) * COS(RADIANS(to_lat))), SQRT(1 - POW(SIN(RADIANS(to_lat - from_lat)/2), 2) + POW(SIN(RADIANS(to_lng - from_lng)/2), 2) * COS(RADIANS(from_lat)) * COS(RADIANS(to_lat))));
 
SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts, distance_between(c.latitude, c.longitude, 37.51145336, 127.081237) AS distance
	FROM cafe c
	LEFT JOIN post p ON p.cid = c.cid
  WHERE c.latitude IS NOT NULL
	GROUP BY c.cid
	ORDER BY distance;