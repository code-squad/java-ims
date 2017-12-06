INSERT INTO user (id, name, password) VALUES ('abcshc', '소희철', '1234');
INSERT INTO user (id, name, password) VALUES ('hong', '홍길동', '1234');
INSERT INTO user (id, name, password) VALUES ('kkk', 'kkk', '1234');
INSERT INTO user (id, name, password) VALUES ('chul', '철', '1234');

INSERT INTO issue (id, comment, reg_date, subject, writer) VALUES (1, '질문~', sysdate, '제목~', 'abcshc');
INSERT INTO issue (id, comment, reg_date, subject, writer) VALUES (2, 'comment', sysdate, 'subject', 'hong');
INSERT INTO issue (id, comment, reg_date, subject, writer) VALUES (3, 'comment!!', sysdate, 'subject!!!', 'kkk');
INSERT INTO issue (id, comment, reg_date, subject, writer) VALUES (4, '내용내용', sysdate, '철', 'chul');