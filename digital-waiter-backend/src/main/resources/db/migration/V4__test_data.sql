SET search_path TO dw;

INSERT INTO organizations (name, api_key_encrypted, pos_org_id) VALUES
('Sparks', '5wN0w63PAp9bPhHIEcf5kHPbwAQeK1qeZTWeN7EVjBcOAkEyyMwCbX/aS3efErbjBCNSzbDAu+g9v2y5', '9f047de1-7f9f-44a0-804d-dea489b8a997');

INSERT INTO terminal_groups (name, pos_external_menu_id, pos_terminal_group_id, organization_id) VALUES
('Sparks Новосибирск, ул. Ильича, 8', '51309', '1a84bf9e-5e3b-f6a9-0196-37c8668f0066', 1);

INSERT INTO restaurant_tables (name, pos_table_id, terminal_group_id) VALUES
('Стол 1', 'f06a7245-8f5c-45d5-88b1-de9691e622b5', 1);

INSERT INTO users (login, password_hash, role, restaurant_table_id) VALUES
('guest', '$2a$10$D51m4B4GWf//8gjCtIrIlOcVGr9g/gfbyc2tKX1GDg6ctdp2zOQ2u', 'GUEST', 1);