create table seg_fornecedores
(
    id    BIGINT IDENTITY (1, 1) PRIMARY KEY NOT NULL,
    razao_social VARCHAR(100)                NOT NULL,
    cnpj VARCHAR(18)                  NOT NULL UNIQUE,
    telefone  VARCHAR(12)                    NOT NULL,
    email VARCHAR(50)                        NOT NULL,
    nome_fantasia VARCHAR(100)               NOT NULL,
    endereco varchar(100)                    NOT NULL
);

