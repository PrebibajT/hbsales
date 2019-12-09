create table seg_categorias
(
   id     BIGINT IDENTITY (1, 1)  PRIMARY KEY            NOT NULL,
   codigo_categoria    VARCHAR (14)               UNIQUE NOT NULL,
   nome_categoria       VARCHAR (255)                    NOT NULL,
   id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedores(id)

);





