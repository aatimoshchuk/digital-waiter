CREATE TABLE organization (
    id SERIAL PRIMARY KEY,
    api_key VARCHAR NOT NULL,
    pos_org_id VARCHAR NOT NULL
);

CREATE TABLE terminal_group (
    id SERIAL PRIMARY KEY,
    pos_terminal_group_id VARCHAR NOT NULL,
    organization_id INT NOT NULL REFERENCES organization(id)
);

CREATE TABLE restaurant_table (
    id SERIAL PRIMARY KEY,
    pos_table_id VARCHAR NOT NULL,
    terminal_group_id INT NOT NULL REFERENCES terminal_group(id)
);

CREATE TABLE table_auth (
    id INT PRIMARY KEY REFERENCES restaurant_table(id),
    login VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

INSERT INTO organization (api_key, pos_org_id) VALUES ('a586a080884c4bc19f7cab4d7a13c083',
                                                       '9f047de1-7f9f-44a0-804d-dea489b8a997');

INSERT INTO terminal_group (pos_terminal_group_id, organization_id) VALUES ('1a84bf9e-5e3b-f6a9-0196-37c8668f0066', 1);

INSERT INTO restaurant_table (pos_table_id, terminal_group_id) VALUES ('f06a7245-8f5c-45d5-88b1-de9691e622b5', 1);

INSERT INTO table_auth (id, login, password) VALUES (1, 'dw_1', '$2a$10$xfrwtGEb8nx02Gov0fEWe.c.WpUmlvVvAggKLB6P.bCfIZkf7OMcC');