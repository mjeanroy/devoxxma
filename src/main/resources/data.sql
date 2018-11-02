--
-- Insert some data
--

INSERT INTO users(id, creation_date, login, password) VALUES ('05f596eb-b2e3-49f5-a9aa-eaa5fa732ff7', NOW(), 'mickael', 'azerty123');
INSERT INTO users(id, creation_date, login, password) VALUES ('0f0b9b8f-847c-4bd6-b207-68acc5ca475a', NOW(), 'houssem', 'qwerty123');

INSERT INTO tweets(id, creation_date, message, user_id) VALUES('be60771b-2f0f-4901-8ebc-1b3808d31b3d', NOW(), 'Hello DevoxxMA 2018!', '05f596eb-b2e3-49f5-a9aa-eaa5fa732ff7');
INSERT INTO tweets(id, creation_date, message, user_id) VALUES('2d185ca1-72c5-4166-9c1e-97fda888d8f7', NOW(), 'How are you today?', '0f0b9b8f-847c-4bd6-b207-68acc5ca475a');
