# oaktrees

this code is for MySql to create database and tables:

create database oaktrees;
use oaktrees;

create table users(
	id  bigint primary key not null auto_increment,
    login varchar(255),
	name varchar(255),
    password varchar(255),
    role varchar(255)
);

insert into users values(1, "root", "ROOT", "$2y$12$FLqk2k6wZguR06KPbYl2T.kwXoWzyteJCMLKXFfbzkFJFYoBfZrZm", "BOSS");

create table chats(
	id bigint primary key not null auto_increment,
	is_group bit(1),
	title varchar(255),
	last_message varchar(255),
	last_writer varchar(255),
	last_time datetime
);

CREATE TABLE messages (
	id bigint primary key NOT NULL AUTO_INCREMENT,
	date datetime DEFAULT NULL,
	from_login varchar(255) DEFAULT NULL,
    from_name varchar(255) DEFAULT NULL,
	text varchar(255) DEFAULT NULL,
    chat_id bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE chat_message( 
	chat_id bigint NOT NULL KEY,
	message_id bigint NOT NULL UNIQUE KEY
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE chat_user(
	chat_id bigint NOT NULL,
	user_id bigint NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


