INSERT INTO user (id, user_id, password, name) values (1, 'brad903', 'password', 'Brad');
INSERT INTO user (id, user_id, password, name) values (2, 'leejh903', 'password', '이정현');

INSERT INTO issue (id, subject, comment, writer_id, is_deleted) values (1, '테스트 이슈1', '테스트 이슈 내용입니다1', 1, false);
INSERT INTO issue (id, subject, comment, writer_id, is_deleted) values (2, '테스트 이슈2', '테스트 이슈 내용입니다2', 1, false);
INSERT INTO issue (id, subject, comment, writer_id, is_deleted) values (3, '테스트 이슈3', '테스트 이슈 내용입니다3', 2, false);