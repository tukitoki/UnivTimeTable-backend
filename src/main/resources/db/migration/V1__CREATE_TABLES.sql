CREATE TABLE university
(
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(70) NOT NULL
);
CREATE TABLE faculty
(
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    university_id BIGINT NOT NULL references university (id)
);
CREATE TABLE system_user
(
    id SERIAL NOT NULL PRIMARY KEY,
    city VARCHAR(80) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(80) NOT NULL,
    password VARCHAR(200) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    ROLE VARCHAR(50) NOT NULL,
    faculty_id BIGINT REFERENCES faculty (id),
    university_id BIGINT REFERENCES university (id)
);
CREATE TABLE university_group
(
    id SERIAL NOT NULL PRIMARY KEY,
    students_amount INT NOT NULL,
    course_number INT NOT NULL,
    group_number INT NOT NULL,
    headman_id BIGINT,
    faculty_id BIGINT NOT NULL REFERENCES faculty (id)
);
CREATE TABLE timetable
(
    id SERIAL NOT NULL PRIMARY KEY,
    day_of_week VARCHAR(15) NOT NULL
);
CREATE TABLE equipment
(
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL
);
CREATE TABLE request
(
    id SERIAL NOT NULL PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    subject_hour_per_week INT NOT NULL,
    type_class VARCHAR(10) NOT NULL,
    lecturer_id BIGINT NOT NULL REFERENCES system_user (id),
    group_id BIGINT NOT NULL REFERENCES university_group (id)
);
CREATE TABLE request_equipment
(
    request_id BIGINT NOT NULL REFERENCES request (id),
    equipment_id BIGINT NOT NULL REFERENCES equipment (id),
    PRIMARY KEY (request_id, equipment_id)
);
CREATE TABLE impossible_time
(
    id SERIAL NOT NULL PRIMARY KEY,
    day_of_week VARCHAR(15) NOT NULL,
    request_id BIGINT NOT NULL REFERENCES request (id),
    time_from TIME NOT NULL
);
CREATE TABLE audience
(
    id SERIAL NOT NULL PRIMARY KEY,
    audience_number INT NOT NULL,
    capacity INT NOT NULL,
    faculty_id BIGINT NOT NULL REFERENCES faculty (id),
    university_id BIGINT NOT NULL REFERENCES university (id)
);
CREATE TABLE class
(
    id SERIAL NOT NULL PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    start_time TIME NOT NULL,
    type_class VARCHAR(10) NOT NULL,
    day_of_week VARCHAR(15) NOT NULL,
    lecturer_id BIGINT NOT NULL REFERENCES system_user(id),
    audience_id BIGINT NOT NULL REFERENCES audience (id),
    timetable_id BIGINT NOT NULL REFERENCES timetable (id)
);
CREATE TABLE audience_equipment
(
    audience_id BIGINT NOT NULL REFERENCES audience (id),
    equipment_id BIGINT NOT NULL REFERENCES equipment (id),
    PRIMARY KEY (audience_id, equipment_id)
);
CREATE TABLE group_class
(
    class_id BIGINT NOT NULL REFERENCES class (id),
    group_id BIGINT NOT NULL REFERENCES university_group (id),
    PRIMARY KEY (group_id, class_id)
);
