INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (240, 25, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (245, 30, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (250, 50, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (255, 20, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (256, 150, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (247, 30, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (257, 40, 2, 1);
INSERT INTO audience(audience_number, capacity, faculty_id, university_id)
VALUES (266, 30, 2, 1);

INSERT INTO audience_equipment(audience_id, equipment_id)
VALUES (9, 1);
INSERT INTO audience_equipment(audience_id, equipment_id)
VALUES (10, 2);
INSERT INTO audience_equipment(audience_id, equipment_id)
VALUES (10, 3);
INSERT INTO audience_equipment(audience_id, equipment_id)
VALUES (13, 2);
INSERT INTO audience_equipment(audience_id, equipment_id)
VALUES (13, 3);

INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 1, 1, 2);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (22, 1, 2, 2);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 1, 3, 2);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 2, 1, 2);
INSERT INTO
    university_group(students_amount, course_number, group_number, faculty_id)
VALUES (20, 2, 2, 2);

INSERT INTO system_user(city, email, full_name, password, role, username, university_id, faculty_id)
VALUES ('Воронеж',
        'helloworld@gmail.com',
        'Андреев Андрей Андреевич',
        '$2a$12$lRznuTjuZMQq31AYTnUZJ.hwNaNRXKKy/lQkWoADrvJ2daYi8b0Z2',
        'lecturer',
        'andreev',
        1,
        2);

INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Экономика', 1.5, 'SEMINAR', 7, 6);
INSERT INTO request_equipment(request_id, equipment_id)
VALUES(5, 2);

INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Интересная экономика', 1.5, 'LECTURE', 7, 6);
INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Основы менеджмента', 1.5, 'LECTURE', 7, 7);
INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Бизнес теория', 1.5, 'LECTURE', 7, 8);

INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Экономика', 1.5, 'SEMINAR', 3, 2);
INSERT INTO impossible_time(day_of_week, request_id, time_from)
VALUES ('monday', 9, '09:45');
INSERT INTO impossible_time(day_of_week, request_id, time_from)
VALUES ('tuesday', 9, '08:00');
INSERT INTO request_equipment(request_id, equipment_id)
VALUES(9, 2);

INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Интересная экономика', 1.5, 'LECTURE', 3, 1);
INSERT INTO impossible_time(day_of_week, request_id, time_from)
VALUES ('monday', 10, '09:45');
INSERT INTO impossible_time(day_of_week, request_id, time_from)
VALUES ('tuesday', 10, '08:00');
INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Основы менеджмента', 1.5, 'LECTURE', 3, 2);
INSERT INTO request(subject_name, subject_hour_per_week, type_class, lecturer_id, group_id)
VALUES ('Бизнес теория', 1.5, 'LECTURE', 3, 3);
VALUES ('monday', 12, '09:45');
INSERT INTO impossible_time(day_of_week, request_id, time_from)
VALUES ('tuesday', 12, '08:00');

UPDATE system_user
set group_id = 1
where id = 5;
UPDATE university_group
set headman_id = 5
where id = 1