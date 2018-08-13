INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', '123123', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'testtest', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'learner', 'password', '러너네임');

INSERT INTO MILESTONE (subject, end_date) values ('MILESTONE 01', '2018-08-01 17:55:00');

INSERT INTO ISSUE (comment, deleted, subject, milestone_id, writer_id) values ('불일치 이슈', false, '불일치 이슈 제목', 1, 3);

INSERT INTO LABEL (TITLE) VALUES ('DB');
INSERT INTO LABEL (TITLE) VALUES ('NOTICE');

