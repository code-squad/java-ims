INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'red', 'password', '레드');

INSERT INTO issue (id, subject, comment, writer_id, deleted) values(1, 'Nullpoint Exception', 'NullPoint Exception', 3, false);
INSERT INTO issue (id, subject, comment, writer_id, deleted) values(2, 'ajax', 'json parse error', 3, false);
INSERT INTO issue (id, subject, comment, writer_id, deleted) values(3, 'ajax', '405 error', 3, false);
INSERT INTO issue (id, subject, comment, writer_id, deleted) values(4, 'not found method', 'not found method', 3, false);

INSERT INTO MILESTONE (id, subject, start_date, end_date) values (1, 'Requirements Analysis', '2018-11-01 00:00:00', '2018-11-30 00:00:00');
INSERT INTO MILESTONE (id, subject, start_date, end_date) values (2, 'Programming', '2018-12-01 00:00:00', '2018-12-31 00:00:00');
INSERT INTO MILESTONE (id, subject, start_date, end_date) values (3, 'Software Test', '2019-01-01 00:00:00', '2019-01-31 00:00:00');
INSERT INTO MILESTONE (id, subject, start_date, end_date) values (4, 'Software maintenance', '2019-02-01 10:00:00', '2019-02-28 00:00:00');
INSERT INTO MILESTONE (id, subject, start_date, end_date) values (5, 'CodeSquad', '2018-09-10 10:00:00', '2019-01-31 18:00:00');

INSERT INTO LABEL (id, label) values (1, 'Bug');
INSERT INTO LABEL (id, label) values (2, 'Invalid');
INSERT INTO LABEL (id, label) values (3, 'Question');
INSERT INTO LABEL (id, label) values (4, 'Duplicate');

INSERT INTO ANSWER (id, answer, issue_id, writer_id) values (1, 'null point error fixed', 1, 3);
INSERT INTO ANSWER (id, answer, issue_id, writer_id) values (2, 'update issues ', 1, 3);
INSERT INTO ANSWER (id, answer, issue_id, writer_id) values (3, 'finish issue', 1, 3);
