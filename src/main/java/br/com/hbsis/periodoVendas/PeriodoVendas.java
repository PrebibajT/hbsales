package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.linha.Linha;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_periodo_vendas")
public class PeriodoVendas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inicial", nullable = false, length = 10)
    private LocalDate dataInicial;

    @Column(name = "data_final", nullable = false, length = 10)
    private LocalDate dataFinal;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedorPeriodo;

    @Column(name = "data_retirada", nullable = false, length = 10)
    private LocalDate dataRetirada;

    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Fornecedor getFornecedorPeriodo() {
        return fornecedorPeriodo;
    }

    public void setFornecedorPeriodo(Fornecedor fornecedorPeriodo) {
        this.fornecedorPeriodo = fornecedorPeriodo;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public  String toString(){
        return "id{" + id + '\'' +
                ", data_inicial='" + dataInicial + '\'' +
                ", data_final='" + dataFinal + '\'' +
                ", id_fornecedor='" + fornecedorPeriodo + '\'' +
                ", data_retirada='" + dataRetirada + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

}
