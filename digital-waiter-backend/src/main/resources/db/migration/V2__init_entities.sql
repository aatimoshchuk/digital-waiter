SET search_path TO dw;

CREATE TYPE ROLE_TYPE AS ENUM (
    'ADMIN',
    'GUEST'
);

CREATE TABLE organizations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    api_key_encrypted VARCHAR NOT NULL,
    pos_org_id VARCHAR NOT NULL
);

CREATE TABLE terminal_groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    pos_external_menu_id VARCHAR NOT NULL,
    pos_terminal_group_id VARCHAR NOT NULL,
    organization_id INT NOT NULL REFERENCES organizations(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE restaurant_tables (
    id SERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    pos_table_id VARCHAR NOT NULL,
    terminal_group_id INT NOT NULL REFERENCES terminal_groups(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR NOT NULL UNIQUE,
    password_hash VARCHAR NOT NULL,
    role ROLE_TYPE NOT NULL,
    restaurant_table_id INT REFERENCES restaurant_tables(id) ON UPDATE CASCADE ON DELETE CASCADE
);