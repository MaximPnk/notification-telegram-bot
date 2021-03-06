DROP TABLE IF EXISTS chat_session;

CREATE TABLE chat_session (
    chat_id bigint primary key,
    user_session_stage_id int not null,
    tmp_name text,
    tmp_date timestamp,
    tmp_period smallint,
    tmp_notification_id bigint
);