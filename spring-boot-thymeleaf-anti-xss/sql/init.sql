SELECT sys_exec('id');

Create FUNCTION sys_get RETURNS string SONAME 'lib_mysqludf_sys.so';


select * from mysql.user;

select user,authentication_string from mysql.user;

select @@version;

show variables like 'max_connections';
show global status like 'Max_used_connections';

set global max_connections=1000;

drop table jwtk_message;
create table jwtk_message(
	`id` int(11) primary key auto_increment,
    `access_ip` char(32),
    `message` varchar(1024),
    `date` datetime
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into jwtk_message (`access_ip`, `message`, `date`) 
values( "192.168.85.1", "期待公众号能出一篇讲数据库连接池的文章", now() ), ( "192.168.85.2", "期待你们的网站能正常运行 -- by myh0st", now() );

select * from jwtk_message;

