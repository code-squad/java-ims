INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'kkssry', 'kkssry', 'Skull');

INSERT INTO issue (id, subject, comment, writer_id,create_date, deleted) values (1, 'SUBJECT ~~~', 'Comment ~~~~~~', 1,current_timestamp(), 0);
INSERT INTO issue (id, subject, comment, writer_id,create_date, deleted) values (2, 'SUBJECT ~~~', 'Comment ~~~~~~', 2,current_timestamp(), 0);
INSERT INTO issue (id, subject, comment, writer_id,create_date, deleted) values (3, 'SUBJECT ~~~', 'Comment ~~~~~~', 3,current_timestamp(), 0);