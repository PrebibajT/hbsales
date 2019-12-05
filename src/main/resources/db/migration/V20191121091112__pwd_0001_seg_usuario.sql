create table seg_usuarios
(
    id    BIGINT IDENTITY (1, 1) NOT NULL,
    login VARCHAR(100)           NOT NULL,
    senha VARCHAR(255)           NOT NULL,
    uuid  VARCHAR(36)            NOT NULL
);

