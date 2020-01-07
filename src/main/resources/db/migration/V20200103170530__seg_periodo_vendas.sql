create table seg_periodo_vendas
(

  id                BIGINT    IDENTITY (1, 1)    PRIMARY KEY  NOT NULL,
  descricao         VARCHAR(50)                               NOT NULL,
  id_fornecedor     BIGINT     FOREIGN KEY REFERENCES seg_fornecedores(id),
  data_inicial      DATE                                      NOT NULL,
  data_final        DATE                                      NOT NULL,
  data_retirada     DATE                                      NOT NULL

  );
