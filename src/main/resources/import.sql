INSERT INTO user (id, user_id, password, name) VALUES (1, 'javajigi', 'testtest', '자바지기');
INSERT INTO user (id, user_id, password, name) VALUES (2, 'sanjigi', 'testtest', '산지기');
INSERT INTO user (id, user_id, password, name) VALUES (3, 'saram4030', 'testtest', '이끼룩');

INSERT INTO issue (id, subject, comment, create_date, modified_date, writer_id) VALUES (1, '이슈주제', '이슈코멘트', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
INSERT INTO issue (id, subject, comment, create_date, modified_date, writer_id) VALUES (2, '2번째 이슈주제', '2번째 이슈코멘트', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);

INSERT INTO milestone (id, subject, start_date, end_date) VALUES (2, '마일스톤주제', CURRENT_TIMESTAMP(), DATEADD('DAY', +7, CURRENT_TIMESTAMP()));