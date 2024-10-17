create table if not exists schools(
    school_id int not null primary key auto_increment,
    name varchar(255) not null unique);

create table if not exists regular_test_set(
regular_test_set_id int not null primary key auto_increment,
term INT NOT NULL,
grade INT NOT NULL,
semester INT NOT NULL,
is_mid INT NOT NULL);

create table if not exists status(
    status_id int not null primary key auto_increment,
    name varchar(50) not null UNIQUE);

create table if not exists classes(
class_id int not null primary key auto_increment,
subject varchar(50) not null,
name varchar(50) not null);

create table if not exists students(
    student_id int not null primary key auto_increment,
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
FOREIGN KEY(student_id) references students(student_id),
foreign key (class_id) references classes(class_id));



create table if not exists school_student(
school_student_id int not null primary key auto_increment,
school_id int not null,
student_id int not null,
created_at DATE NOT NULL ,
changed_at DATE  NOT NULL ,
 FOREIGN KEY (school_id) REFERENCES schools(school_id),
 FOREIGN KEY (student_id) REFERENCES students(student_id)
);

create table if not exists status_students(
status_student_id int not null primary key auto_increment,
status_id int not null,
student_id int not null,
created_at DATE NOT NULL ,
changed_at DATE  NOT NULL ,
 FOREIGN KEY (status_id) REFERENCES status(status_id),
 FOREIGN KEY (student_id) REFERENCES students(student_id)
);

create table if not exists regular_test(
regular_test_id int not null primary key auto_increment,
regular_test_set_id int not null,
school_id int not null,
date Date ,--this column is used for monitoring school change
japanese int ,
math int,
english int,
science int,
social int,
music int,
art int,
tech int,
pe int,
foreign key (school_id) references schools(school_id),
foreign key(regular_test_set_id) references regular_test_set(regular_test_set_id));

create table if not exists regular_test_result(
regular_test_result_id int not null primary key auto_increment,
regular_test_id int not null,
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
foreign key (regular_test_id) references regular_test(regular_test_id),
foreign key(student_id) references students(student_id));

create table if not exists mock_tests(
    mock_test_id int not null primary key auto_increment,
    name varchar(255) unique,
    date DATE
);

CREATE TABLE IF NOT EXISTS mock_test_results (
    mock_test_id INT NOT NULL,
    student_id INT NOT NULL,
    japanese INT,
    japanese_ss INT,
    math INT,
    math_ss INT,
    english INT,
    english_ss INT,
    science INT,
    science_ss INT,
    social INT,
    social_ss INT,
    jme_ss INT,        -- 3科目偏差値
    jmess_ss INT,      -- 5科目偏差値
    dream_school1 VARCHAR(100),
    dream_school1_probability INT,
    dream_school2 VARCHAR(100),
    dream_school2_probability INT,
    dream_school3 VARCHAR(100),
    dream_school3_probability INT,
    dream_school4 VARCHAR(100),
    dream_school4_probability INT,
    dream_school5 VARCHAR(100),
    dream_school5_probability INT,
    dream_school6 VARCHAR(100),
    dream_school6_probability INT,
    PRIMARY KEY (mock_test_id, student_id), -- 複合主キーの設定
    FOREIGN KEY (mock_test_id) REFERENCES mock_tests(mock_test_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

create table if not exists school_record (
    school_id int not null,
    term int not null,
    grade int not null,
    semester int not null,
    primary key(school_id,term,grade,semester),
    foreign key(school_id) references school(school_id)
);

create table if not exists school_record_result(
    student_id int not null,
    school_record_id int not null,
    japanese tinyint(1),
    math tinyint(1),
    english tinyint(1),
    science tinyint(1),
    social tinyint(1),
    music tinyint(1),
    art tinyint(1),
    technology tinyint(1),
    pe tinyint(1),
    primary key(student_id,school_record_id),
    foreign key(student_id) references student(student_id),
    foreign key(school_record_id) references school_record(school_record_id)
)