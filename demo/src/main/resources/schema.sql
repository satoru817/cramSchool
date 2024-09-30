create table if not exists schools(
    id int not null primary key auto_increment,
    name varchar(255) not null unique);

create table if not exists students(
    id int not null primary key auto_increment,
    code int  unique,
    name varchar(255) not null,
    status varchar(50) not null);

create table if not exists school_student(
id int not null primary key auto_increment,
school_id int not null,
student_id int not null,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
changed_at DATETIME  NOT NULL DEFAULT '9999-12-31 23:59:59' ON UPDATE CURRENT_TIMESTAMP ,
)