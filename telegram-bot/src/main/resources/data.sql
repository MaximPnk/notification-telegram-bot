DROP TABLE IF EXISTS chat_session;

CREATE TABLE chat_session (
    chat_id bigint primary key,
    method_id int NOT NULL
);