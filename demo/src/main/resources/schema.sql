create table if not exists schools(
    id int not null primary key auto_increment,
    name varchar(255) not null unique);

create table if not exists regular_exam_set(
id int not null primary key auto_increment,
term INT NOT NULL,
grade INT NOT NULL,
semester INT NOT NULL,
isMid TINYINT(1) NOT NULL);

create table if not exists status(
    status_id int not null primary key auto_increment,
    name varchar(50) not null UNIQUE);

create table if not exists classes(
class_id int not null primary key auto_increment,
subject varchar(50) not null,
name varchar(50) not null);

create table if not exists students(
    id int not null primary key auto_increment,
    el1 int not null,
    code int  unique,
    name varchar(255) not null
    );

create table if not exists class_students(
class_student_id int not null primary key auto_increment,
student_id int not null,
class_id int not null,
created_at DATE NOT NULL,
changed_at DATE NOT NULL,
FOREIGN KEY(student_id) references students(id),
foreign key (class_id) references classes(class_id));



create table if not exists school_student(
id int not null primary key auto_increment,
school_id int not null,
student_id int not null,
created_at DATE NOT NULL ,
changed_at DATE  NOT NULL ,
 FOREIGN KEY (school_id) REFERENCES schools(id),
 FOREIGN KEY (student_id) REFERENCES students(id)
);

create table if not exists status_students(
status_student_id int not null primary key auto_increment,
status_id int not null,
student_id int not null,
created_at DATE NOT NULL ,
changed_at DATE  NOT NULL ,
 FOREIGN KEY (status_id) REFERENCES status(status_id),
 FOREIGN KEY (student_id) REFERENCES students(id)
);

create table if not exists regular_exam(
id int not null primary key auto_increment,
regular_test_set_id int not null,
school_id int not null,
date Date ,
grade int not null,
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
foreign key (school_id) references schools(id),
foreign key(regular_test_set_id) references regular_exam_set(id));

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

