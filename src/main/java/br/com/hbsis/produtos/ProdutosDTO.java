package br.com.hbsis.produtos;

import java.time.LocalDate;

public class ProdutosDTO {

    private Long id;              //1
    private String codigoProduto; //2
    private String nomeProduto;   //3
    private Double preco;         //4
    private Long idLinha;         //5
    private Integer unidadeCaixa; //6
    private Double pesoUnidade;   //7
    private String pesoMedida;    //8
    private LocalDate validade;      //9

    public ProdutosDTO() {

    }

    public ProdutosDTO(Long id, String codigoProduto, String nomeProduto, Double preco, Long idLinha, Integer unidadeCaixa, Double pesoUnidade, String pesoMedida, LocalDate validade) {
        this.id = id;
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.idLinha = idLinha;
        this.unidadeCaixa = unidadeCaixa;
        this.pesoUnidade = pesoUnidade;
        this.pesoMedida = pesoMedida;
        this.validade = validade;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

    public Integer getUnidadeCaixa() {
        return unidadeCaixa;
    }

    public void setUnidadeCaixa(Integer unidadeCaixa) {
        this.unidadeCaixa = unidadeCaixa;
    }

    public Double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(Double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public String getPesoMedida() {
        return pesoMedida;
    }

    public void setPesoMedida(String pesoMedida) {
        this.pesoMedida = pesoMedida;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }


    public static ProdutosDTO of(Produtos produtos) {
        return new ProdutosDTO(
                produtos.getId(),
                produtos.getCodigoProduto(),
                produtos.getNomeProduto(),
                produtos.getPreco(),
                produtos.getLinhaCategoria().getId(),
                produtos.getUnidadeCaixa(),
                produtos.getPesoUnidade(),
                produtos.getPesoMedida(),
                produtos.getValidade()

        );
    }

    @Override
    public String toString() {
        return "id{" + id + '\'' +
                ", nome_produto='" + nomeProduto + '\'' +
                ", preco='" + preco + '\'' +
                ", id_linha='" + idLinha + '\'' +
                ", unidade_caixa='" + unidadeCaixa + '\'' +
                ", peso_unidade='" + pesoUnidade + '\'' +
                ", peso_medida='" + pesoMedida + '\'' +
                ", validade='" + validade + '\'' +
                '}';
    }

}
