create database aquareminder;
use aquareminder;
create table user(
                     id int primary key auto_increment,
                     name varchar(30),
                     pwd varchar(30),
                     mailbox varchar(30),
                     water int,
                     energy int,
                     planted varchar(100),
                     day int,
                     average_water int,
                     average_time int,
                     total_water int,
                     total_time int
);
show tables;
insert into user values (1,'user1','123','123@qq.com',0,100,'0000',0,0,0,0,0);
insert into user values (2,'user2','1234','1234@qq.com',0,5,'0000',0,0,0,0,0);
insert into user values (3,'user3','12345','12345@qq.com',0,0,'0000',0,0,0,0,0);
select * from user; 
