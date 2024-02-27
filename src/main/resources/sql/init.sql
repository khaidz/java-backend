CREATE TABLE IF NOT EXISTS khaibq.tbl_role
(
    id                 SERIAL PRIMARY KEY,
    code               VARCHAR(20) NOT NULL,
    name               VARCHAR(100),
    created_by         VARCHAR(50) NOT NULL,
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50),
    last_modified_date TIMESTAMP,
    is_deleted         INT       DEFAULT 0,
    CONSTRAINT UQ_role_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS khaibq.tbl_user
(
    id                 SERIAL PRIMARY KEY,
    username           VARCHAR(50)  NOT NULL,
    password           VARCHAR(200) NOT NULL,
    email              VARCHAR(100),
    dept_code          VARCHAR(50),
    created_by         VARCHAR(50)  NOT NULL,
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50),
    last_modified_date TIMESTAMP,
    is_deleted         INT       DEFAULT 0,
    CONSTRAINT UQ_user_username UNIQUE (username),
    CONSTRAINT UQ_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS khaibq.tbl_user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT FK_role_id FOREIGN KEY (role_id) REFERENCES khaibq.tbl_role (id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES khaibq.tbl_user (id)
);

CREATE TABLE IF NOT EXISTS khaibq.tbl_department
(
    id                 SERIAL PRIMARY KEY,
    code               VARCHAR(50)  NOT NULL,
    name               VARCHAR(200) NOT NULL,
    parent_code        VARCHAR(50),
    manager_username   VARCHAR(50),
    created_by         VARCHAR(50)  NOT NULL,
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50),
    last_modified_date TIMESTAMP,
    is_deleted         INT       DEFAULT 0,
    CONSTRAINT UQ_department_code UNIQUE (code)
);

create sequence seq_lov as integer;

CREATE TABLE IF NOT EXISTS khaibq.tbl_lov
(
    id   BIGINT PRIMARY KEY DEFAULT nextval('seq_lov'),
    type VARCHAR(50)  NOT NULL,
    code VARCHAR(50)  NOT NULL,
    name VARCHAR(200) NOT NULL,
    CONSTRAINT UQ_lov_type_code UNIQUE (type, code)
);

alter sequence seq_lov owned by khaibq.tbl_lov.id;


-------------------------------
INSERT INTO khaibq.tbl_role (code, name, created_by, created_date, last_modified_by, last_modified_date,
                             is_deleted)
VALUES ('ADMIN', 'ADMIN', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO khaibq.tbl_role (code, name, created_by, created_date, last_modified_by, last_modified_date,
                             is_deleted)
VALUES ('USER', 'USER', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO khaibq.tbl_user (username, password, email, created_by, created_date, last_modified_by,
                             last_modified_date, is_deleted)
VALUES ('admin', '$2a$10$bFyHic/vLIHYz6dVdA/OvuYc/O6HDT2w9HtR27V74GT0O.LUk.mwi', 'admin@khaibq.net', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO khaibq.tbl_user (username, password, email, created_by, created_date, last_modified_by,
                             last_modified_date, is_deleted)
VALUES ('user', '$2a$10$bFyHic/vLIHYz6dVdA/OvuYc/O6HDT2w9HtR27V74GT0O.LUk.mwi', 'user@khaibq.net', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO khaibq.tbl_user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO khaibq.tbl_user_role (user_id, role_id)
VALUES (2, 2);