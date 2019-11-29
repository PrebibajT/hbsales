package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seg_categorias")
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_Categoria",   length = 255)
    private Long codigoCategoria;

    @ManyToOne
    @JoinColumn (name = "id_fornecedor", referencedColumnName="id")
    private Fornecedor fornecedorCategoria;

    @Column(name = "nome_categoria", unique = true,  length = 255)
    private String nomeCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Long codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public Fornecedor getFornecedorCategoria() {
        return fornecedorCategoria;
    }

    public void setFornecedorCategoria(Fornecedor fornecedorCategoria) {
        this.fornecedorCategoria = fornecedorCategoria;
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
