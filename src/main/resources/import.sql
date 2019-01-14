INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'jar100', 'test', 'Peter');

INSERT INTO issue (id, writer_id, subject, comment, create_date, modified_date, deleted, closed) values (1, 1, 'test제목', '내용', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);
INSERT INTO issue (id, writer_id, subject, comment, create_date, modified_date, deleted, closed) values (2, 2, 'test제목2', '내용2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);

INSERT INTO milestone (id, create_date, modified_date, end_date, start_date, subject) values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '1차');
INSERT INTO milestone (id, create_date, modified_date, end_date, start_date, subject) values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2차');

INSERT INTO label (id, create_date, modified_date, subject, comment) values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '라벨', '치명적인 버그발생');