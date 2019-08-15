drop database if exists rideshare;
create database rideshare;
use rideshare;

create table users (
	userID INT(3) PRIMARY KEY auto_increment,
    username VARCHAR(30) not null,
    password VARCHAR(30) not null,
    frequest1 VARCHAR(30),
    frequest2 VARCHAR(30),
    frequest3 VARCHAR(30),
    frequest4 VARCHAR(30)
);

create table rides (
	rideID INT(3) PRIMARY KEY auto_increment,
    company VARCHAR(20),
    ridetime VARCHAR(20),
	startLocation VARCHAR(100),
    endLocation VARCHAR(100),
    price Decimal(6,2) not null,
    userID INT(3) not null,
    foreign key (userID) references users(userID)
);

create table friends (
	friendID INT(3) PRIMARY KEY auto_increment,
    user1ID INT(3) not null,
    user2ID INT(3) not null,
    foreign key (user1ID) references users(userID),
    foreign key (user2ID) references users(userID)
);