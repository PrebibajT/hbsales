package br.com.hbsis.itens;

import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.produtos.Produtos;

import javax.persistence.*;

@Entity
@Table(name = "seg_itens")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Produtos idProduto;

    @Column(name = "quantidade", length = 50)
    private Long quantidade;


    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }

    public Produtos getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produtos idProduto) {
        this.idProduto = idProduto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "item{"+
                ", id='" + id +'\'' +
                ", id_pedido='" + idPedido +'\'' +
                ", id_produto='" + idProduto +'\'' +
                ", quantidade='" + quantidade +'\'' +
                '}';

    }


}
