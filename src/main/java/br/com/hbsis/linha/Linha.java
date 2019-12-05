package br.com.hbsis.linha;

import br.com.hbsis.categoria.Categorias;

import javax.persistence.*;

@Entity
@Table(name = "seg_linha")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_linha", length = 100)
    private Long codigoLinha;

    @ManyToOne
    @JoinColumn (name = "id_categoria", referencedColumnName="id")
    private Categorias categoriaLinha;

    @Column(name = "nome", length = 36)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(Long codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public Categorias getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(Categorias categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "id{" + id +
                ", codigo_linha='" + codigoLinha + '\'' +
                ", categoria_linha='" + categoriaLinha + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}