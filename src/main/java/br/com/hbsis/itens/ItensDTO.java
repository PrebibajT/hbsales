package br.com.hbsis.itens;

public class ItensDTO {
    Long id;
    Long idPedido;
    Long idProduto;
    Long quantidadeProduto;

    public ItensDTO(){

    }

    public ItensDTO(Long id, Long idPedido, Long idProduto, Long quantidadeProduto){
        this.id = id;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidadeProduto = quantidadeProduto;
    }


    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Long quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static ItensDTO of(Item item){
        return new ItensDTO(
                item.getId(),
                item.getIdProduto().getId(),
                item.getIdPedido().getId(),
                item.getQuantidade()

        );
    }



}

