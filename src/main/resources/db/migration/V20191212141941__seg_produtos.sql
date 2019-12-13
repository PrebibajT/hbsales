create table seg_produtos
(

  id              BIGINT        IDENTITY (1, 1)  PRIMARY KEY        NOT NULL,
  codigo_produto  VARCHAR(10)                               UNIQUE  NOT NULL,
  nome_produto    VARCHAR(200)                                      NOT NULL,
  preco           DECIMAL(8,2)                                      NOT NULL,
  id_linha        BIGINT                FOREIGN KEY REFERENCES seg_linha(id),
  unidade_caixa   BIGINT                                            NOT NULL,
  peso_unidade    DECIMAL(8,3)                                      NOT NULL,
  peso_medida     VARCHAR(10)                                       NOT NULL,
  validade        DATE                                              NOT NULL

);