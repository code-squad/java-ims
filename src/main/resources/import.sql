INSERT INTO user (id, user_id, password, name, create_date) values (1, 'javajigi', 'test', '자바지기', current_timestamp());
INSERT INTO user (id, user_id, password, name, create_date) values (2, 'sanjigi', 'test', '산지기', current_timestamp());
INSERT INTO user (id, user_id, password, name, create_date) values (3, 'kkssry', 'kkssry', 'Skull', current_timestamp());

INSERT INTO issue (id, subject, comment, writer_id,create_date, deleted, closed) values (1, 'SUBJECT ~~~', 'Comment ~~~~~~', 1,current_timestamp(), 0, 0);
INSERT INTO issue (id, subject, comment, writer_id,create_date, deleted, closed) values (2, 'SUBJECT ~~~', 'Comment ~~~~~~', 2,current_timestamp(), 0, 0);
INSERT INTO issue (id, subject, comment, writer_id,create_date, deleted, closed) values (3, 'SUBJECT ~~~', 'Comment ~~~~~~', 3,current_timestamp(), 0, 0);

INSERT INTO milestone(id, subject, start_date, end_date,create_date,writer_id) values (1, '마일스톤!!', '2019-01-01','2019-12-31',current_timestamp,3);
INSERT INTO milestone(id, subject, start_date, end_date,create_date,writer_id) values (2, '마일스톤2!!', '2019-01-01','2020-12-31',current_timestamp,2);

INSERT INTO label(id,name,writer_id) values (1,'label1',3);
INSERT INTO label(id,name,writer_id) values (2,'label2',3);
INSERT INTO label(id,name,writer_id) values (3,'label3',3);

INSERT INTO label_manager(id,issue_id,label_id) values(1,1,1);
INSERT INTO label_manager(id,issue_id,label_id) values(2,1,2);
INSERT INTO label_manager(id,issue_id,label_id) values(3,1,3);