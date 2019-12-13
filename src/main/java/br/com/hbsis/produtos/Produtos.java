package br.com.hbsis.produtos;

import br.com.hbsis.linha.Linha;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "seg_produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//1

    @Column(name = "codigo_produto", length = 10)
    private String codigoProduto; //2

    @Column(name = "nome_produto", length = 200)
    private String nomeProduto; //3

    @Column(name = "preco")
    private Double preco; //4

    @ManyToOne
    @JoinColumn(name = "id_linha", referencedColumnName = "id")
    private Linha linhaCategoria;//5

    @Column(name = "unidade_caixa")
    private Integer unidadeCaixa;//6

    @Column(name = "peso_unidade")
    private Double pesoUnidade;//7

    @Column(name = "peso_medida")
    private String pesoMedida;//8

    @Column(name = "validade")
    private LocalDate validade; //9


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

    public Linha getLinhaCategoria() {
        return linhaCategoria;
    }

    public void setLinhaCategoria(Linha linhaCategoria) {
        this.linhaCategoria = linhaCategoria;
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

    @Override
    public String toString() {
        return "id{" + id + '\'' +
                ", nome_produto='" + nomeProduto + '\'' +
                ", preco='" + preco + '\'' +
                ", id_linha='" + linhaCategoria + '\'' +
                ", unidade_caixa='" + unidadeCaixa + '\'' +
                ", peso_unidade='" + pesoUnidade + '\'' +
                ", peso_medida='" + pesoMedida + '\'' +
                ", validade='" + validade + '\'' +
                '}';
    }
}
