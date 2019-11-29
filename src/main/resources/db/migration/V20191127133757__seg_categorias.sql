create table seg_categorias
(
   id     BIGINT IDENTITY (1, 1)  PRIMARY KEY            NOT NULL,
   codigo_categoria     BIGINT                           NOT NULL,
   nome_categoria       VARCHAR (255)                    NOT NULL,
   id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedores(id)

);

create unique index ix_seg_categorias_01 on seg_categorias (nome_categoria asc);
create unique index ix_seg_categorias_02 on seg_categorias (id_fornecedor asc);
create unique index ix_seg_categorias_03 on seg_categorias (codigo_categoria asc);


