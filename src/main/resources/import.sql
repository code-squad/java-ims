INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');

INSERT INTO issue (id, create_date, modified_date, comment, subject, writer_id, deleted, closed, milestone_id) values (1, current_timestamp, current_timestamp, '에러 해결좀요', '에러 겁나 뜨는데요?', 1, false, false, null);
INSERT INTO issue (id, create_date, modified_date, comment, subject, writer_id, deleted, closed, milestone_id) values (2, current_timestamp, current_timestamp, '해결 에러좀요', '해결 겁나 뜨는데요?', 2, false, false, null);

INSERT INTO milestone (id, create_date, modified_date, subject, start_date, end_date, open, closed) values (1, current_timestamp, current_timestamp, 'milestone1', current_timestamp, current_timestamp, 0, 0);
INSERT INTO milestone (id, create_date, modified_date, subject, start_date, end_date, open, closed) values (2, current_timestamp, current_timestamp, 'milestone2', current_timestamp, current_timestamp, 0, 0);

INSERT INTO label (id, create_date, modified_date, deleted, explanation, name) values (1, current_timestamp, current_timestamp, false, '개발를 하기 위해서 사전에 완료되어야 하는 task 목록을 작성하도록 한다. ', 'Development');
INSERT INTO label (id, create_date, modified_date, deleted, explanation, name) values (2, current_timestamp, current_timestamp, false, '버그와 관련된 이슈이다.', 'Bug');
INSERT INTO label (id, create_date, modified_date, deleted, explanation, name) values (3, current_timestamp, current_timestamp, false, '고객/사용자와의 업무협의 미팅이 있는 경우 meeting으로 이슈를 등록한다.', 'Meeting');

INSERT INTO comment (id, create_date, modified_date, contents, deleted, issue_id, writer_id) values (1, current_timestamp, current_timestamp, '에러는 스스로 해결하려무나', false, 1, 1);
INSERT INTO comment (id, create_date, modified_date, contents, deleted, issue_id, writer_id) values (2, current_timestamp, current_timestamp, '해결되면 나한테 알려주고', false, 1, 1);
INSERT INTO comment (id, create_date, modified_date, contents, deleted, issue_id, writer_id) values (3, current_timestamp, current_timestamp, '내가 도와줄까요?', false, 1, 2);

INSERT INTO file (id, create_date, modified_date, location, original_name, saved_name, comment_id, uploader_id) values (1, current_timestamp, current_timestamp, 'C:\Users\lollo\Desktop\uploadFile\0123456789sample.txt', 'sample.txt', '0123456789sample.txt', null, 1);

