create table seg_fornecedores
(
    id    BIGINT IDENTITY (1, 1) PRIMARY KEY NOT NULL,
    razao_social VARCHAR(155)                NOT NULL,
    cnpj VARCHAR(16)                           UNIQUE,
    telefone  VARCHAR(16)                      UNIQUE,
    email VARCHAR(120)                        NOT NULL,
    nome_fantasia VARCHAR(255)                NOT NULL,
    endereco varchar(255)                     NOT NULL
);

create unique index ix_seg_fornecedores_01 on seg_fornecedores (nome_fantasia asc);
create unique index ix_seg_fornecedores_02 on seg_fornecedores (cnpj asc);
create unique index ix_seg_fornecedores_03 on seg_fornecedores (telefone asc);
create unique index ix_seg_fornecedores_04 on seg_fornecedores (email asc);
create unique index ix_seg_fornecedores_05 on seg_fornecedores (razao_social asc);
create unique index ix_seg_fornecedores_06 on seg_fornecedores (endereco asc);

