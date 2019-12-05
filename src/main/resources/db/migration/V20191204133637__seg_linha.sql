create table seg_linha
(
   id     BIGINT IDENTITY (1, 1)  PRIMARY KEY                NOT NULL,
   codigo_linha     BIGINT                                   NOT NULL,
   nome       VARCHAR (255)                                  NOT NULL,
   id_categoria BIGINT FOREIGN KEY REFERENCES seg_categorias(codigo_categoria)

);

   create unique index ix_seg_linha_01 on seg_linha (codigo_linha asc);
   create unique index ix_seg_linha_02 on seg_linha (nome asc);
   create unique index ix_seg_linha_03 on seg_linha (id_categoria asc);