INSERT INTO user (id, user_id, password, name) values (1, 'kuro', 'eogks369', 'kuro');
INSERT INTO user (id, user_id, password, name) values (2, 'dsoop', 'eogks369', 'daesoop');

INSERT INTO issue (id, subject, comment, deleted, writer_id) values (1, 'this is first issue', 'this is soop first comment test', false, 1);
INSERT INTO issue (id, subject, comment, deleted, writer_id) values (2, 'this is second issue', 'this is soop second comment test', false, 1);

INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (1, 'first milestone', '2019-01-10T14:02', '222222-02-22T14:02', 1);
INSERT INTO milestone (id, subject, start_date, end_date, writer_id) values (2, 'second milestone', '2019-01-10T14:02', '222222-02-22T14:02', 1);

INSERT INTO label (id, name) values (1, 'bug');
INSERT INTO label (id, name) values (2, 'what?');
