package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import javax.persistence.*;

@Entity
@Table(name = "seg_categorias")
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_categoria",   length = 10)
    private String codigoCategoria;

    @ManyToOne
    @JoinColumn (name = "id_fornecedor", referencedColumnName="id")
    private Fornecedor fornecedorCategoria;

    @Column(name = "nome_categoria", unique = true,  length = 50)
    private String nomeCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public Fornecedor getFornecedorCategoria() {
        return fornecedorCategoria;
    }

    public void setFornecedorCategoria(Fornecedor fornecedorCategoria) {
        this.fornecedorCategoria = fornecedorCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return "id{" + id+ '\'' +
                ", nome_Categoria='" + nomeCategoria +'\'' +
                ", fornecedor_Categoria='" + fornecedorCategoria + '\'' +
                ", codigoCategoria='"+ codigoCategoria + '\'' +
                '}';
    }
}