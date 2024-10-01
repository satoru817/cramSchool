insert ignore into schools(id,name) values
(1,'不明'),
(2,'七国中学校');

insert ignore into students(id,code,el1,name,status) values
(1,8888,2018,'chappy','alive');

insert ignore into school_student(id,school_id,student_id)values
(1,2,1);