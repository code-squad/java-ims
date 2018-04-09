INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');
INSERT INTO user (id, user_id, password, name) values (3, 'koo', 'test', '구상윤');
INSERT INTO user (id, user_id, password, name) values (4, 'pobi', 'test', '박재성교수님');

INSERT INTO milestone (id, deleted, start_date, end_date, subject) values (1, false, '2018-09-10T12:00:00', '2019-09-10T12:00:00', '-=-testMilestone1-=-');
INSERT INTO milestone (id, deleted, start_date, end_date, subject) values (2, false, '2019-09-10T12:00:00', '2020-09-10T12:00:00', '-=-testMilestone2-=-');
INSERT INTO milestone (id, deleted, start_date, end_date, subject) values (3, false, '2020-09-10T12:00:00', '2021-09-10T12:00:00', '-=-testMilestone3-=-');

INSERT INTO label (id, deleted, subject, writer_id) values (1, false, 'label1 - BackLog', 1);
INSERT INTO label (id, deleted, subject, writer_id) values (2, false, 'label2 - Planning', 2);
INSERT INTO label (id, deleted, subject, writer_id) values (3, false, 'label3 - Ready', 2);
INSERT INTO label (id, deleted, subject, writer_id) values (4, false, 'label4 - Working', 1);
INSERT INTO label (id, deleted, subject, writer_id) values (5, false, 'label5 - InReview', 1);

INSERT INTO issue (id, comment, deleted, label_id, subject, milestone_id, writer_id) values (1, 'test1 comment.', false, 1, '-=-test1-=-', null, 1);
INSERT INTO issue (id, comment, deleted, label_id, subject, milestone_id, writer_id) values (2, 'test2 comment.', false, 2, '-=-test2-=-', null, 2);
INSERT INTO issue (id, comment, deleted, label_id, subject, milestone_id, writer_id) values (3, 'test3 comment.', false, 3, '-=-test3-=-', null, 3);
