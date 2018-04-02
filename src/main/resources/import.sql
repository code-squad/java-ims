INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'password', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'password', '산지기');

INSERT INTO issue (id, subject, comment, writer_id) values (1, '테스트용 이슈 첫번째', '뭔 내용용용용용 아무 내용이나 적자', 1);
INSERT INTO issue (id, subject, comment, writer_id) values (2, '테스트용 이슈 두번째', '아무 내용이나 적자', 2);
INSERT INTO issue (id, subject, comment, writer_id) values (3, '테스트용 이슈 세번째', '무슨 내용을 적을까', 1);

INSERT INTO milestone (id, subject, start_date, end_date) values (1, 'Milestone 1', '2018-04-01 00:00:00.0', '2018-04-07 00:00:00.0');
INSERT INTO milestone (id, subject, start_date, end_date) values (2, 'Milestone 2', '2018-04-05 00:00:00.0', '2018-04-06 00:00:00.0');
INSERT INTO milestone (id, subject, start_date, end_date) values (3, 'Milestone 3', '2018-04-07 00:00:00.0', '2018-04-015 00:00:00.0');
