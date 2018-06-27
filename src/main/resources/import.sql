INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'asdfasdf', '산지기');

INSERT INTO issue (id, writer_id, title, content, status, deleted) values (1, 1, 'title', 'content', 'OPEN', false);

INSERT INTO milestone (id, start_date, due_date, subject, deleted) values (1, '2018-06-20', '2018-07-20', 'Test Subject', false);

INSERT INTO attachment (id, writer_id, issue_id, original_file_name, hashed_file_name, path) values (1, 1, 1, 'logback.xml', '100a221a-6654-472c-b81a-4b9da2de7ff8_logback.xml', '/Users/JaeP/Desktop/CodeSquad-lvl3/java-ims/src/main/resources/attachments/100a221a-6654-472c-b81a-4b9da2de7ff8_logback.xml')
