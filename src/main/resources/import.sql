INSERT INTO user (id, name, password) VALUES ('abcshc', '소희철', '1234');
INSERT INTO user (id, name, password) VALUES ('hong', '홍길동', '1234');
INSERT INTO user (id, name, password) VALUES ('kkk', 'kkk', '1234');
INSERT INTO user (id, name, password) VALUES ('chul', '철', '1234');

INSERT INTO issue (comment, reg_date, subject, writer) VALUES ('질문~', sysdate, '제목~', 'abcshc');
INSERT INTO issue (comment, reg_date, subject, writer) VALUES ('comment', sysdate, 'subject', 'hong');
INSERT INTO issue (comment, reg_date, subject, writer) VALUES ('comment!!', sysdate, 'subject!!!', 'kkk');
INSERT INTO issue (comment, reg_date, subject, writer) VALUES ('내용내용', sysdate, '철', 'chul');