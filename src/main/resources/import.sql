INSERT INTO attachment (id, origin_file_name, path, target_file_name, deleted) values (0, 'dummy.png', '/Users/lee_ki_hyun/Desktop/CodeSquad/Steps/img/avatar', 'dummy', 0);

INSERT INTO user (id, user_id, password, name, avatar_id) values (1, 'javajigi', 'password', '자바지기', 0);
INSERT INTO user (id, user_id, password, name, avatar_id) values (2, 'sanjigi', 'password', '산지기', 0);
INSERT INTO user (id, user_id, password, name, avatar_id) values (3, 'doby', 'password', 'lkhlkh23', 0);

INSERT INTO issue (id, subject, comment, writer_id, deleted) values (1, 'test subject-1', 'test comment', 1, 0);
INSERT INTO issue (id, subject, comment, writer_id, deleted) values (2, 'test subject-2', 'test comment', 1, 0);
INSERT INTO issue (id, subject, comment, writer_id, deleted) values (3, 'test subject-3', 'test comment', 1, 0);
INSERT INTO issue (id, subject, comment, writer_id, deleted) values (4, 'test subject-4', 'test comment', 3, 0);

INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (1, 'milestone-1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (2, 'milestone-2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (3, 'milestone-3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (4, 'milestone-4', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (5, 'milestone-5', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 3);


INSERT INTO label (id, subject, writer_id) values (1, 'label - 1', 1);
INSERT INTO label (id, subject, writer_id) values (2, 'label - 2', 1);
INSERT INTO label (id, subject, writer_id) values (3, 'label - 3', 1);


INSERT INTO answer (id, comment, writer_id, issue_id, deleted) values (1, 'answer - 1', 1, 1, 0);
INSERT INTO answer (id, comment, writer_id, issue_id, deleted) values (2, 'answer - 2', 2, 1, 0);
INSERT INTO answer (id, comment, writer_id, issue_id, deleted) values (3, 'answer - 3', 1, 1, 0);



