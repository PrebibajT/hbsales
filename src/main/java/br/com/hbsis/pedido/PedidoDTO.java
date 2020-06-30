package br.com.hbsis.pedido;

import br.com.hbsis.itens.ItensDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {

    Long id;
    Long uid;
    Long idFornecedor;
    Long idFuncionario;
    String status;
    List <ItensDTO> itens;
    LocalDate dataDeCriacao;

    public PedidoDTO(){

    }

    public PedidoDTO(Long id, Long uid, Long idFornecedor, Long idFuncionario,String status, List<ItensDTO> itens, LocalDate dataDeCriacao){
        this.id = id;
        this.uid = uid;
        this.idFornecedor = idFornecedor;
        this.idFuncionario = idFuncionario;
        this.status = status;
        this.itens = itens;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setuUid(Long uid) {
        this.uid = uid;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItensDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItensDTO> itens) {
        this.itens = itens;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }


    public static PedidoDTO of(Pedido pedido){

        List<ItensDTO> itens = new ArrayList<ItensDTO>();
        pedido.getItens().forEach(itens1 -> itens.add(ItensDTO.of(itens1)));

        return new PedidoDTO(
                pedido.getId(),
                pedido.getUid(),
                pedido.getPedidoFornecedor().getId(),
                pedido.getPedidoFuncionario().getId(),
                pedido.getStatus(),
                itens,
                pedido.getDataDeCriacao()
        );
    }


    @Override
    public String toString(){
        return "id{"+ id +'\'' +
                ", uId='" + uid +'\'' +
                ", status='" + status +'\'' +
                ", id_fornecedor='" + idFornecedor +'\'' +
                ", id_funcionario='" + idFuncionario +'\'' +
                ", id_itens='" + itens +'\'' +
                ", data_criacao='" + dataDeCriacao +'\'' +
                '}';

    }
}
