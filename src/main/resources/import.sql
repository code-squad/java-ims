INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');

INSERT INTO milestone (id, subject, start_date, end_date) VALUES (2, 'subject', CURRENT_TIMESTAMP , CURRENT_TIMESTAMP )

INSERT INTO issue (id, milestone_id, writer_id, subject, comment, deleted) VALUES (3, 2, 1, '질문이있어요2', '질문내용이에요2', FALSE)