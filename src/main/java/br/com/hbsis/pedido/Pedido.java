package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.itens.Item;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    private Funcionario pedidoFuncionario;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor pedidoFornecedor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPedido")
    private List<Item> itens;

    @Column(name = "data_criacao", length = 50)
    private LocalDate dataDeCriacao;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Funcionario getPedidoFuncionario() {
        return pedidoFuncionario;
    }

    public void setPedidoFuncionario(Funcionario pedidoFuncionario) {
        this.pedidoFuncionario = pedidoFuncionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Fornecedor getPedidoFornecedor() {
        return pedidoFornecedor;
    }

    public void setPedidoFornecedor(Fornecedor pedidoFornecedor) {
        this.pedidoFornecedor = pedidoFornecedor;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    @Override
    public String toString(){
        return "id{"+ id +'\'' +
                ", uid='" + uid +'\'' +
                ", status='" + status +'\'' +
                ", id_fornecedor='" + pedidoFornecedor +'\'' +
                ", id_funcionario='" + pedidoFuncionario +'\'' +
                ", id_itens='" + itens +'\'' +
                ", data_criacao='" + dataDeCriacao +'\'' +
                '}';

    }
}
