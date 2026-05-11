CREATE TABLE students (
    id VARCHAR(32) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(255) NOT NULL UNIQUE,
    region VARCHAR(100) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    remark VARCHAR(255),
    is_deleted TINYINT
);

CREATE TABLE students_courses (
    id VARCHAR(32) PRIMARY KEY,
    students_id VARCHAR(32) NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    start_date DATE,
    expected_end_date DATE,
    FOREIGN KEY (students_id) REFERENCES students(id)
);
