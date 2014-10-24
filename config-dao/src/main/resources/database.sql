create database if not exists webconfig ;
create table if not exists webconfig.user(
  id bigint not null primary key auto_increment,
  userName nvarchar(20)
);