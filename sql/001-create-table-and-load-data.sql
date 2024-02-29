DROP TABLE IF EXISTS movies;

CREATE TABLE categories (
 category_id int unsigned AUTO_INCREMENT,
 category VARCHAR(20) NOT NULL,
 PRIMARY KEY (categories_id)
);

INSERT INTO categories (category) VALUES ("漫画");
INSERT INTO categories (category) VALUES ("ライトノベル");
INSERT INTO categories (category) VALUES ("小説");

CREATE TABLE books (
 book_id int unsigned AUTO_INCREMENT,
 name VARCHAR(1000) NOT NULL,
 release_date DATE NOT NULL,
 is_purchased TINYINT(1) NOT NULL DEFAULT 0,
 category_id int unsigned,
 PRIMARY KEY (book_id),
 CONSTRAINT fk_books_category_id FOREIGN KEY (category_id) REFERENCES categories (categories_id) ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO books (name, reLease_date, is_purchased, category_id) VALUES ("ノーゲーム・ノーライフ・1","2012/04/30",1,2);
INSERT INTO books (name, release_date, categories_id) VALUES ("鬼滅の刃・1", "2016/06/08", 1);
INSERT INTO books (name, release_date, is_purchased, category_id) VALUES ("ビブリア古書堂の事件手帖・1","2011/03/25",1,3);
