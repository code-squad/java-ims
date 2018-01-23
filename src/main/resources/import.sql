INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'testtest', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'testtest', '산지기');

INSERT INTO issue (title, contents, writer_id ) values ('11111', '11111', 1);
INSERT INTO issue (title, contents, writer_id) values ('22222', '22222', 2);
INSERT INTO issue (title, contents, writer_id) values ('33333', '33333', 1);

INSERT INTO milestone (title, writer_id, start_date, end_date) values ('마일스톤1', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO milestone (title, writer_id, start_date, end_date) values ('마일스톤2', 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO label (title) values ('라벨1')
INSERT INTO label (title) values ('라벨2')
INSERT INTO label (title) values ('라벨3')
