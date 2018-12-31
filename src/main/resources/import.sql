INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');

INSERT INTO issue (id, user_id, subject, comment, create_date, modified_date) values (1, 'javajigi', 'test제목', '내용', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO issue (id, user_id, subject, comment, create_date, modified_date ) values (2, 'sanjigi', 'test제목2', '내용2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());