DROP DATABASE IF EXISTS housefinder;

CREATE DATABASE housefinder;

USE housefinder;

CREATE TABLE users (
    username varchar(64) UNIQUE NOT NULL,
    password varchar(256) NOT NULL,

    PRIMARY KEY(username)
);

CREATE TABLE flats (
	_id int NOT NULL,
    _month varchar(16) NOT NULL,
	town varchar(64) NOT NULL,
    flat_type varchar(16) NOT NULL,
	_block varchar(8),
	street_name varchar(64),
	storey_range varchar(16),
	floor_area_sqm int NOT NULL,
    flat_model varchar(32),
	lease_commence_date int NOT NULL,
    resale_price int NOT NULL,

    username varchar(64),

    PRIMARY KEY(_id),

    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES users(username)
				ON DELETE CASCADE
);
