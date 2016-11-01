CREATE TABLE MAP_GENRE(
	GENRE_ID VARCHAR(5) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	CONSTRAINT PK_MAP_GENRE PRIMARY KEY(GENRE_ID));

CREATE TABLE MAP_MOVIE(
	MOVIE_ID VARCHAR(8) NOT NULL,
	GENRE_ID VARCHAR(5) NOT NULL,
	TITLE VARCHAR(50) NOT NULL,
	DIRECTOR VARCHAR(50) NOT NULL,
	ACTORS VARCHAR(100) NOT NULL,
	RUNTIME NUMERIC(3) NULL,
	RELEASE_DATE DATE default GETDATE(),
	TICKET_PRICE NUMERIC(6,2) NULL,
	SIMPLE_UPLOAD_FILE_PATH VARCHAR(1000) NULL,
	NOW_PLAYING CHAR(1) NULL,
	CONSTRAINT PK_MAP_MOVIE PRIMARY KEY(MOVIE_ID),
	CONSTRAINT FK_MAP_MOVIE FOREIGN KEY(GENRE_ID) REFERENCES MAP_GENRE(GENRE_ID));
	
CREATE TABLE MAP_IDS(
	TABLE_NAME VARCHAR(16) NOT NULL PRIMARY KEY,
	NEXT_ID DECIMAL(30) NOT NULL);
	
INSERT INTO MAP_GENRE VALUES('GR-01','Action');
INSERT INTO MAP_GENRE VALUES('GR-02','Adventure');
INSERT INTO MAP_GENRE VALUES('GR-03','Animation');
INSERT INTO MAP_GENRE VALUES('GR-04','Comedy');
INSERT INTO MAP_GENRE VALUES('GR-05','Crime');
INSERT INTO MAP_GENRE VALUES('GR-06','Drama');
INSERT INTO MAP_GENRE VALUES('GR-07','Fantasy');
INSERT INTO MAP_GENRE VALUES('GR-08','Romance');
INSERT INTO MAP_GENRE VALUES('GR-09','Sci-Fi');
INSERT INTO MAP_GENRE VALUES('GR-10','Thriller');

INSERT INTO MAP_MOVIE VALUES('MV-00001','GR-02','Alice in Wonderland','Tim Burton','Johnny Depp',110,'2010-03-04',8000,'sample/images/posters/aliceinwonderland.jpg','Y');
INSERT INTO MAP_MOVIE VALUES('MV-00002','GR-09','Avatar','James Cameron','Sigourney Weaver',100,'2010-02-16',7000,'sample/images/posters/avatar.jpg','Y');
INSERT INTO MAP_MOVIE VALUES('MV-00003','GR-01','Green Zone','Paul Greengrass','Yigal Naor',90,'2010-03-25',7000,'sample/images/posters/greenzone.jpg','Y');
INSERT INTO MAP_MOVIE VALUES('MV-00004','GR-06','Remember Me','Allen Coulter','Caitlyn Rund',115,'2010-03-12',8000,'sample/images/posters/rememberme.jpg','Y');
INSERT INTO MAP_MOVIE VALUES('MV-00005','GR-04','She is Out of My League','Jim Field Smith','Jay Baruchel',118,'2010-03-12',7500,'sample/images/posters/shesoutof.jpg','Y');
INSERT INTO MAP_MOVIE VALUES('MV-00006','GR-05','Shutter Island','Martin Scorsese','Leonardo DiCaprio',95,'2010-03-18',8000,'sample/images/posters/shutterisland.jpg','Y');

INSERT INTO MAP_IDS VALUES('MAP_MOVIE',7);

commit;

