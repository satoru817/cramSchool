create table if not exists schools(
    id int not null primary key auto_increment,
    name varchar(255) not null unique);

create table if not exists students(
    id int not null primary key auto_increment,
    el1 int not null,
    code int  unique,
    name varchar(255) not null,
    status varchar(50) not null);

create table if not exists school_student(
id int not null primary key auto_increment,
school_id int not null,
student_id int not null,
created_at DATE NOT NULL DEFAULT CURDATE(),
changed_at DATE  NOT NULL DEFAULT '9999-12-31',
 FOREIGN KEY (school_id) REFERENCES schools(id),
 FOREIGN KEY (student_id) REFERENCES students(id)
);

create table if not exists regular_exam(
id int not null primary key auto_increment,
school_id int not null,
year int not null,
semester int not null,
is_mid tinyint(1) not null,
japanese int ,
math int,
english int,
science int,
social int,
music int,
art int,
tech int,
pe int,
foreign key (school_id) references schools(id));

create table if not exists regular_exam_result(
id int not null primary key auto_increment,
regular_exam_id int not null,
student_id int not null,
japanese int ,
math int,
english int,
science int,
social int,
music int,
art int,
tech int,
pe int,
foreign key (regular_exam_id) references regular_exam(id),
foreign key(student_id) references students(id));