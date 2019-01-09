INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'password', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'password', '산지기');

INSERT INTO milestone (id, subject, start_date, end_date) values (1, 'testMilestone1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO milestone (id, subject, start_date, end_date) values (2, 'testMilestone2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO milestone (id, subject, start_date, end_date) values (3, 'testMilestone3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO issue (id, writer_id, subject, comment, deleted, closed, milestone_id) values (1, 1, 'testSubject1', 'testComment1', false, false, 1);
INSERT INTO issue (id, writer_id, subject, comment, deleted, closed, milestone_id) values (2, 1, 'testSubject2', 'testComment2', false, false, 1);
INSERT INTO issue (id, writer_id, subject, comment, deleted, closed, milestone_id) values (3, 2, 'testSubject3', 'testComment3', false, false, 2);
INSERT INTO issue (id, writer_id, subject, comment, deleted, closed, milestone_id) values (4, 2, 'testSubject4', 'testComment4', false, true, 1);
