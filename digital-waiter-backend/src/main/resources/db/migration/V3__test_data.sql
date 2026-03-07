SET search_path TO dw;

INSERT INTO users (login, password_hash, role) VALUES
('main_admin', '$2a$10$3vPwWhn2h3ietzclaIx8JuJGp0UPQmyPhPpA7u1Wn3HwNonVVqCg2', 'ADMIN')