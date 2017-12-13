INSERT INTO user (id, name, password) VALUES ('abcshc', '소희철', '1234');
INSERT INTO user (id, name, password) VALUES ('hong', '홍길동', '1234');
INSERT INTO user (id, name, password) VALUES ('kkk', 'kkk', '1234');
INSERT INTO user (id, name, password) VALUES ('chul', '철', '1234');

INSERT INTO milestone(id, subject, start_date, end_date, writer_id) values(1, 'firstMilestone', sysdate, sysdate+1, 'abcshc');
INSERT INTO milestone(id, subject, start_date, end_date, writer_id) values(2, 'hello', sysdate, sysdate+1, 'hong');
INSERT INTO milestone(id, subject, start_date, end_date, writer_id) values(3, 'good', sysdate, sysdate+1, 'kkk');

INSERT INTO label(id, name, writer_id) values(1, 'label1', 'abcshc');
INSERT INTO label(id, name, writer_id) values(2, 'hello', 'abcshc');
INSERT INTO label(id, name, writer_id) values(3, 'good', 'abcshc');

INSERT INTO issue (id, comment, reg_date, subject, writer_id, assignee_id, label_id, milestone_id) VALUES (1, '질문~', sysdate, '제목~', 'abcshc', 'abcshc', 1, 1);
INSERT INTO issue (id, comment, reg_date, subject, writer_id, assignee_id) VALUES (2, 'comment', sysdate, 'subject', 'hong', 'abcshc');
INSERT INTO issue (id, comment, reg_date, subject, writer_id) VALUES (3, 'comment!!', sysdate, 'subject!!!', 'kkk');
INSERT INTO issue (id, comment, reg_date, subject, writer_id) VALUES (4, '내용내용', sysdate, '철', 'chul');