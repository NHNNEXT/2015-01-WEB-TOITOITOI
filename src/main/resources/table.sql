SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dearhere
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dearhere
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dearhere` DEFAULT CHARACTER SET utf8 ;
USE `dearhere` ;

-- -----------------------------------------------------
-- Table `dearhere`.`place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dearhere`.`place` (
  `id` INT unsigned NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `dearhere`.`dear`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dearhere`.`dear` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `dearIdx` (`name` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `dearhere`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dearhere`.`post` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT 'UNSIGNED',
  `createdtime` TIMESTAMP NOT NULL,
  `content` TEXT NOT NULL,
  `imageurl` VARCHAR(255) NULL,
  `likes` INT NOT NULL DEFAULT '0' COMMENT 'UNSIGNED\n',
  `place_id` INT unsigned NOT NULL,
  `dear_id` INT unsigned NOT NULL,
  PRIMARY KEY (`id`, `dear_id`),
  INDEX `fk_post_place_idx` (`place_id` ASC),
  INDEX `fk_post_ dear1_idx` (`dear_id` ASC),
  CONSTRAINT `fk_post_place`
    FOREIGN KEY (`place_id`)
    REFERENCES `dearhere`.`place` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_ dear1`
    FOREIGN KEY (`dear_id`)
    REFERENCES `dearhere`.`dear` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `dearhere`.`reply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dearhere`.`reply` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT 'unsigned',
  `createdtime` TIMESTAMP NOT NULL,
  `content` TEXT NOT NULL,
  `likes` INT NOT NULL DEFAULT '0',
  `post_id` INT NULL,
  PRIMARY KEY (`id`, `post_id`),
  INDEX `fk_reply_post1_idx` (`post_id` ASC),
  CONSTRAINT `fk_reply_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `dearhere`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `dearhere`.`candidate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dearhere`.`candidate` (
  `name` VARCHAR(25) NOT NULL,
  `place_id` INT unsigned NOT NULL,
  PRIMARY KEY (`name`, `place_id`),
  INDEX `fk_recommendation_place1_idx` (`place_id` ASC),
  CONSTRAINT `fk_recommendation_place1`
    FOREIGN KEY (`place_id`)
    REFERENCES `dearhere`.`place` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
select * from post;
select * from dear;
insert into place (name) values ('test');
insert into dear (name) values ('toitoi');
insert into dear (name) values ('깃토이');
insert into dear (name) values ('플토이');
insert into dear (name) values ('뷰티토이');
insert into dear (name) values ('소토이');
insert into dear (name) values ('산타');
insert into dear (name) values ('산타할아버지는 알고 계신대 누가 착한앤지');

insert into post (content, place_id, dear_id) values ('토이토이토이',1,1);
insert into post (content, place_id, dear_id) values ('merry christmas',1,1);
insert into post (content, place_id, dear_id) values ('울지 마라
외로우니까 사람이다
살아간다는 것은 외로움을 견디는 일이다
공연히 오지 않는 전화를 기다리지 마라
눈이 오면 눈길을 걸어가고
비가 오면 빗길을 걸어가라
갈대숲에서 가슴검은도요새도 너를 보고 있다
가끔은 하느님도 외로워서 눈물을 흘리신다
새들이 나뭇가지에 앉아 있는 것도 외로움 때문이고
네가 물가에 앉아 있는 것도 외로움 때문이다
산 그림자도 외로워서 하루에 한 번씩 마을로 내려온다
종소리도 외로워서 울려퍼진다',1,1);
insert into post (content, place_id, dear_id) values ('깃토이짱짱맨',1,2);
insert into post (content, place_id, dear_id) values ('깃토이깃마스터',1,2);
insert into post (content, place_id, dear_id) values ('깃토이는 누구게요?!?!',1,2);
insert into post (content, place_id, dear_id) values ('플토이는 무슨뜻이지...',1,3);
insert into post (content, place_id, dear_id) values ('븉븉 븉티토이!!',1,4);
insert into post (content, place_id, dear_id) values ('소토이는 무슨뜻이게',1,5);
insert into post (content, place_id, dear_id) values ('산타할아버지는 어디살아요?한국살아요? 어떻게와요?',1,6);
insert into post (content, place_id, dear_id) values ('긴 이름한테 편지쓰면 어떻게 되요?',1,7);
