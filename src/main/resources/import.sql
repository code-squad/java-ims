INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'master', '1', '관리자');
INSERT INTO issue (id, subject, comment, writer_id, create_date, deleted) values (1, '안녕하세요1', '하이하이하이하이하이', 1, CURRENT_TIMESTAMP(), 0);
INSERT INTO issue (id, subject, comment, writer_id, create_date, deleted) values (2, '안녕하세요2', '너의 머리속은 마구니가 꼈어!', 1, CURRENT_TIMESTAMP(), 0);