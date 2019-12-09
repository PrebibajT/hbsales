create table seg_linha
(
   id     BIGINT IDENTITY (1, 1)  PRIMARY KEY        NOT NULL,
   codigo_linha  VARCHAR (10)                UNIQUE  NOT NULL,
   nome          VARCHAR (50)                        NOT NULL,
   id_categoria BIGINT FOREIGN KEY REFERENCES seg_categorias(id)

);
