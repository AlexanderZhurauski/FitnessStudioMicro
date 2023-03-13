INSERT INTO app.user_role (role)
VALUES ('ADMIN'), ('USER');

INSERT INTO app.user_status (status)
VALUES ('ACTIVATED'), ('DEACTIVATED'), ('WAITING_ACTIVATION');

INSERT INTO app.user
VALUES ('128fbf51-9bdc-4a8e-ab59-2e73cad49551',
        '2023-02-22 17:08:10.346499+03',
        '2023-02-22 17:08:10.346499+03',
        'gandalfdude@gmail.com',
        '$2a$10$jvv8N260N5tBmhErPaYtsubolyD1G0cUJHocgZFQaXldYZX6D/Ix2',
        'admin',
        1,
        1);