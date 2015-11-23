use cafein;

DROP TABLE IF EXISTS `reply`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `cafe`;
DROP TABLE IF EXISTS `nudge`;

CREATE TABLE `cafe` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `post` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(20) NOT NULL,
  `postingtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cid` int(11) DEFAULT NULL,
  `liked` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pid`),
  KEY `cid` (`cid`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `cafe` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `reply` (
  `reid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `content` varchar(200) NOT NULL,
  `liked` int(11) NOT NULL DEFAULT '0',
  `postingtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`reid`),
  KEY `pid` (`pid`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `nudge` (
  `nudgeid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `contents` varchar(200) NOT NULL,
  PRIMARY KEY (`nudgeid`),
  KEY `cid` (`cid`),
  CONSTRAINT `nudge_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `cafe` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;