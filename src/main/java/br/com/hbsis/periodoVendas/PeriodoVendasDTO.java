package br.com.hbsis.periodoVendas;

import java.time.LocalDate;

public class PeriodoVendasDTO {
    private Long id;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private LocalDate dataRetirada;
    private Long idFornecedor;
    private String descricao;

    public PeriodoVendasDTO(){


    }

    public PeriodoVendasDTO(Long id, LocalDate dataInicial, LocalDate dataFinal, LocalDate dataRetirada, Long idFornecedor, String descricao){
        this.id = id;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.dataRetirada = dataRetirada;
        this.idFornecedor = idFornecedor;
        this.descricao = descricao;

    }

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

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static PeriodoVendasDTO of (PeriodoVendas periodoVendas){
        return new PeriodoVendasDTO(
                periodoVendas.getId(),
                periodoVendas.getDataInicial(),
                periodoVendas.getDataFinal(),
                periodoVendas.getDataRetirada(),
                periodoVendas.getFornecedorPeriodo().getId(),
                periodoVendas.getDescricao()
        );
    }

    @Override
    public  String toString(){
        return "id{" + id + '\'' +
                ", data_inicial='" + dataInicial + '\'' +
                ", data_final='" + dataFinal + '\'' +
                ", id_fornecedor='" + idFornecedor + '\'' +
                ", data_retirada='" + dataRetirada + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }



}
