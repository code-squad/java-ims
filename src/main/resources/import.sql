INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');

INSERT INTO issue (id, create_date, modified_date, comment, subject, writer_id, deleted, closed, milestone_id) values (1, current_timestamp, current_timestamp, '에러 해결좀요', '에러 겁나 뜨는데요?', 1, false, false, null);
INSERT INTO issue (id, create_date, modified_date, comment, subject, writer_id, deleted, closed, milestone_id) values (2, current_timestamp, current_timestamp, '해결 에러좀요', '해결 겁나 뜨는데요?', 2, false, false, null);

INSERT INTO milestone (id, create_date, modified_date, subject, start_date, end_date, open, closed) values (1, current_timestamp, current_timestamp, 'milestone1', current_timestamp, current_timestamp, 0, 0);
INSERT INTO milestone (id, create_date, modified_date, subject, start_date, end_date, open, closed) values (2, current_timestamp, current_timestamp, 'milestone2', current_timestamp, current_timestamp, 0, 0);