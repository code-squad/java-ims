INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'password', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'password', '산지기');

INSERT INTO issue (id, writer_id, subject, comment, deleted) values (1, 1, 'testSubject1', 'testComment1', false);
INSERT INTO issue (id, writer_id, subject, comment, deleted) values (2, 1, 'testSubject2', 'testComment2', false);
INSERT INTO issue (id, writer_id, subject, comment, deleted) values (3, 2, 'testSubject3', 'testComment3', false);