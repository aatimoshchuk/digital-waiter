CREATE TABLE organization (
    id SERIAL PRIMARY KEY,
    api_key VARCHAR NOT NULL,
    pos_org_id VARCHAR NOT NULL
);

CREATE TABLE terminal_group (
    id SERIAL PRIMARY KEY,
    pos_external_menu_id VARCHAR NOT NULL,
    pos_terminal_group_id VARCHAR NOT NULL,
    organization_id INT NOT NULL REFERENCES organization(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE restaurant_table (
    id SERIAL PRIMARY KEY,
    pos_table_id VARCHAR NOT NULL,
    terminal_group_id INT NOT NULL REFERENCES terminal_group(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE table_auth (
    id INT PRIMARY KEY REFERENCES restaurant_table(id) ON UPDATE CASCADE ON DELETE CASCADE,
    login VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE session (
    id INT PRIMARY KEY,
    table_auth_id INT NOT NULL REFERENCES table_auth(id) ON UPDATE CASCADE ON DELETE CASCADE,
    status VARCHAR NOT NULL
);

CREATE TABLE draft_order_item (
    draft_order_item_id INT PRIMARY KEY,
    session_id INT NOT NULL REFERENCES session(id) ON UPDATE CASCADE ON DELETE CASCADE,
    pos_dish_id VARCHAR NOT NULL,
    amount INT NOT NULL,
    pos_size_id VARCHAR
);

INSERT INTO organization (api_key, pos_org_id) VALUES ('a586a080884c4bc19f7cab4d7a13c083',
                                                       '9f047de1-7f9f-44a0-804d-dea489b8a997');

INSERT INTO terminal_group (pos_external_menu_id, pos_terminal_group_id, organization_id) VALUES('51309',
                                                                                                 '1a84bf9e-5e3b-f6a9-0196-37c8668f0066', 1);

INSERT INTO restaurant_table (pos_table_id, terminal_group_id) VALUES ('f06a7245-8f5c-45d5-88b1-de9691e622b5', 1);

INSERT INTO table_auth (id, login, password) VALUES (1, 'dw_1', '$2a$10$xfrwtGEb8nx02Gov0fEWe.c.WpUmlvVvAggKLB6P.bCfIZkf7OMcC');