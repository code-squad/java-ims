INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
insert into user (id, user_id, password, name) values (3, 'jimmy', '12345', '재연킴');
INSERT INTO issue (id, writer_id, subject, comment, deleted) values (1, 1, 'db에 있는 subject', 'db에 있는 comment', false );
INSERT INTO issue (id, writer_id, subject, comment, deleted) values (2, 2, 'db에 있는 test subject', 'db에 있는 test comment2', false );
INSERT INTO issue (id, writer_id, subject, comment, deleted) values (3, 3, 'db에 있는 subject', 'db에 있는 test comment3', false );

insert into mile_stone (id, end_date, start_date, subject) values (1, '2018-06-21 12:00:00', '2018-06-28 12:00:00', 'milestone import sql');
insert into mile_stone (id, end_date, start_date, subject) values (2, '2018-05-21 12:00:00', '2018-07-28 12:00:00', 'milestone import sql2');
insert into mile_stone (id, end_date, start_date, subject) values (3, '2018-04-21 12:00:00', '2018-08-28 12:00:00', 'milestone import sql3');

insert into answer (id, writer_id, issue_id , comment, deleted) values (1, 1, 1, 'answer in db', false );
insert into answer (id, writer_id, issue_id , comment, deleted) values (2, 3, 1, 'answer writed by jimmy', false );
insert into answer (id, writer_id, issue_id , comment, deleted) values (3, 3, 3, 'answer writed by jimmy2', false );