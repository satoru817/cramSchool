create table if not exists schools(
    id int not null primary key auto_increment,
    name varchar(255) not null );

create table if not exists students(
    id int not null primary key auto_increment,
    code int ,
    name varchar(255) not null,
    status varchar(50) not null,
    school_id int not null,
    foreign key(school_id)
    references schools(id));