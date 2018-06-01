INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test1234', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');

INSERT INTO issue(id, writer_id, subject, comment, create_date, closed, mile_stone_id) values(1, 1, '1제목입니다', '1내용입니다', CURRENT_TIMESTAMP(), true, null);
INSERT INTO issue(id, writer_id, subject, comment, create_date, closed, mile_stone_id) values(2, 2, '2제목입니다', '2내용입니다', CURRENT_TIMESTAMP(), true, null);
INSERT INTO issue(id, writer_id, subject, comment, create_date, closed, mile_stone_id) values(3, 1, '3제목입니다', '3내용입니다', CURRENT_TIMESTAMP(), true, null);


INSERT INTO mile_stone(id,end_date, start_date, subject, writer_id, closed) values (1, '2018-06-30 14:03:00' , CURRENT_TIMESTAMP(), '첫스톤', 1, false);
INSERT INTO mile_stone(id,end_date, start_date, subject, writer_id, closed) values (2, '2018-06-30 14:03:00' , CURRENT_TIMESTAMP(), '둘스톤', 2, false);
INSERT INTO mile_stone(id,end_date, start_date, subject, writer_id, closed) values (3, '2018-06-30 14:03:00' , CURRENT_TIMESTAMP(), '셋스톤', 1, false);