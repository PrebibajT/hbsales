create table seg_itens
(
     id           BIGINT  IDENTITY (1, 1)  PRIMARY KEY    NOT NULL,
     id_pedido    BIGINT  FOREIGN KEY REFERENCES  seg_pedidos(id),
     id_produto   BIGINT  FOREIGN KEY REFERENCES  seg_produtos(id),
     quantidade   BIGINT                                  NOT NULL

)