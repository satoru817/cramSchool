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
created_at DATE NOT NULL DEFAULT CURDATE(),
changed_at DATE  NOT NULL DEFAULT '9999-12-31',
 FOREIGN KEY (school_id) REFERENCES schools(id),
 FOREIGN KEY (student_id) REFERENCES students(id)
)