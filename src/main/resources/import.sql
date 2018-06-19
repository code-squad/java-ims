INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'riverway', '123456', '리버웨이');

INSERT INTO issue (id, contents, title, writer_id) VALUES (1, 'helloWorld', 'test', 1);
INSERT INTO issue (id, contents, title, writer_id) VALUES (2, 'helloWorld2', 'test2', 3);
INSERT INTO issue (id, contents, title, writer_id) VALUES (3, 'helloWorld3', 'test3', 1);
INSERT INTO issue (id, contents, title, writer_id) VALUES (4, 'helloWorld4', 'test4', 1);

INSERT INTO milestone (id, start_date, end_date, subject) VALUES (1, '2018-06-07 14:02:00', '2018-06-09 15:02:00', 'testMilestone');
INSERT INTO milestone (id, start_date, end_date, subject) VALUES (2, '2018-06-08 12:03:00', '2018-06-24 18:55:00', 'testMilestone2');