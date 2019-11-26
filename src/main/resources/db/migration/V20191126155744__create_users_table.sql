create table users (
    id integer primary key ,
    username varchar(100) unique not null ,
    password varchar(128) not null
);