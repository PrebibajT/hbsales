create table seg_pedidos
(
     id            BIGINT  IDENTITY (1, 1)  PRIMARY KEY         NOT NULL,
     uid           BIGINT                             UNIQUE    NOT NULL,
     status        VARCHAR(50)                                  NOT NULL,
     id_fornecedor BIGINT    FOREIGN KEY REFERENCES seg_fornecedores(id),
     id_funcionario BIGINT    FOREIGN KEY REFERENCES seg_funcionario(id),
     data_criacao  DATE                                        NOT NULL
)

