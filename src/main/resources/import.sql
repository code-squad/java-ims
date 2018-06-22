INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', 'test', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', 'test', '산지기');

INSERT INTO issue (id, subject, comment, writer_id) values (1, '이슈입니다', '코멘트입니다', 1);
INSERT INTO issue (id, subject, comment, writer_id) values (2, '이슈입니다2', '코멘트입니다', 1);
INSERT INTO issue (id, subject, comment, writer_id) values (3, '이슈입니다3', '코멘트입니다', 1);

INSERT INTO milestone (id, end_date, start_date, subject, writer_id) values (1, '2018-12-31 12:59:00', '2018-12-31 12:59:00', '마일스톤이에ㅛ', 1);
