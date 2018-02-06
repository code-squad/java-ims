INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'boobby', '1234', '부비');

INSERT INTO issue (id, subject, comment, writer_id) values (1, 'test issue1', '테스트 1번 이슈입니다.', 1);
INSERT INTO issue (id, subject, comment, writer_id) values (2, 'test issue2', '테스트 2번 이슈입니다.', 2);
INSERT INTO issue (id, subject, comment, writer_id) values (3, 'test issue3', '테스트 3번 이슈입니다.', 1);
INSERT INTO issue (id, subject, comment, writer_id) values (4, 'test issue4', '테스트 4번 이슈입니다.', 2);
INSERT INTO issue (id, subject, comment, writer_id) values (5, 'test issue5', '테스트 5번 이슈입니다.', 1);

INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (1, 'Milestone 1', '2017-12-22', '2018-03-02', 1);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (2, 'Milestone 2', '2017-12-23', '2018-03-03', 2);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (3, 'Milestone 3', '2017-12-24', '2018-03-04', 1);