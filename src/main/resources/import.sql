INSERT INTO user (id, user_id, password, name, create_date) values (1, 'brad903', 'password', 'Brad', CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date) values (2, 'leejh903', 'password', '이정현', CURRENT_TIMESTAMP());

INSERT INTO issue (id, subject, comment, writer_id, deleted, create_date) values (1, '테스트 이슈1', '테스트 이슈 내용입니다1', 1, false, CURRENT_TIMESTAMP());
INSERT INTO issue (id, subject, comment, writer_id, deleted, create_date) values (2, '테스트 이슈2', '테스트 이슈 내용입니다2', 1, false, CURRENT_TIMESTAMP());
INSERT INTO issue (id, subject, comment, writer_id, deleted, create_date) values (3, '테스트 이슈3', '테스트 이슈 내용입니다3', 2, false, CURRENT_TIMESTAMP());