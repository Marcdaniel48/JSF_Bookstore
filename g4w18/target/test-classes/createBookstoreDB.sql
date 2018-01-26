/**
 * Authors:  Jephtia, Sebastian
 * Created: Jan 25, 2018
 */
DROP DATABASE IF EXISTS BOOKSTORE;
CREATE DATABASE BOOKSTORE;

USE BOOKSTORE;

DROP USER IF EXISTS fish@localhost;
CREATE USER booktopia@'localhost' IDENTIFIED WITH mysql_native_password BY 'g4w18booktopia' REQUIRE NONE;
GRANT ALL ON BOOKSTORE.* TO booktopia@'localhost';

FLUSH PRIVILEGES;