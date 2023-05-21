INSERT INTO
    university(name, city)
VALUES ('Воронежский государственный университет', 'Воронеж');
INSERT INTO
    university(name, city)
VALUES ('Воронежский государственный технический университет', 'Воронеж');

INSERT INTO
    faculty(name, university_id)
VALUES ('Факультет компьютерных наук', 1);
INSERT INTO
    faculty(name, university_id)
VALUES ('Экономический факультет', 1);
INSERT INTO
    faculty(name, university_id)
VALUES ('Исторический факультет', 1);
INSERT INTO
    faculty(name, university_id)
VALUES ('Факультет компьютерных наук', 2);
INSERT INTO
    faculty(name, university_id)
VALUES ('Экономический факультет', 2);
INSERT INTO
    faculty(name, university_id)
VALUES ('Исторический факультет', 2);

INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 1, 1, 1);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (22, 1, 2, 1);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 1, 3, 1);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 2, 1, 1);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 2, 2, 1);