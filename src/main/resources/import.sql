INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (1, 'javajigi', 'test', '자바지기', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (2, 'sanjigi', 'test', '산지기', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (3, 'third', 'test', '셋째', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (4, 'fourth', 'test', '넷째', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (5, 'fifth', 'test', '다섯째', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (6, 'sixth', 'test', '여섯째', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO user (id, user_id, password, name, create_date, modified_date) values (7, 'seventh', 'test', '일곱째', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO milestone (id, subject, start_date, end_date) values (1, 'Milestone1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO milestone (id, subject, start_date, end_date) values (2, 'Milestone2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO milestone (id, subject, start_date, end_date) values (3, 'Milestone3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (1, 1, 1, '자바지기 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, true);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (2, 2, 2, '산지기 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (3, 3, 2, '셋째 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (4, 4, 3, '넷째 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, true);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (5, 5, 1, '다섯째 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (6, 6, 3, '여섯째 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, true);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (7, 7, 1, '일곱째 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);
INSERT INTO issue (id, writer_id, milestone_id, subject, comment, create_date, modified_date, deleted, closed) values (8, 1, 2, '자바지기 두 번째 이슈', '내용123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, false);

