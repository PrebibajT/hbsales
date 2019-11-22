create table seg_fornecedores
(
    id    BIGINT IDENTITY (1, 1) NOT NULL,
    Razao_Social VARCHAR(155)           NOT NULL,
    CNPJ VARCHAR(16)                     NOT NULL,
    Telefone  VARCHAR(16)               NOT NULL,
    Email VARCHAR(120)                  NOT NULL,
    NomeFantasia VARCHAR(255)           NOT NULL,
    Endereco varchar(255)               NOT NULL
);

create unique index ix_seg_fornecedores_01 on seg_fornecedores (NomeFantasia asc);
create unique index ix_seg_fornecedores_02 on seg_fornecedores (CNPJ asc);
create unique index ix_seg_fornecedores_03 on seg_fornecedores (Telefone asc);
create unique index ix_seg_fornecedores_04 on seg_fornecedores (Email asc);
create unique index ix_seg_fornecedores_05 on seg_fornecedores (Razao_Social asc);
create unique index ix_seg_fornecedores_06 on seg_fornecedores (Endereco asc);

