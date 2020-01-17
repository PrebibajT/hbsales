package br.com.hbsis.itens;

import br.com.hbsis.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;

public class invoiceDTO {
    String cnpjFornecedor;
    String employeeUuid;
    Double totalValue;
    List<invoiceItemDTO> invoiceItemDTOS;

  public invoiceDTO(){

    }

    public invoiceDTO(String cnpjFornecedor, String employeeUuid, Double totalValue, List<invoiceItemDTO> invoiceItemDTOS) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.totalValue = totalValue;
        this.invoiceItemDTOS = invoiceItemDTOS;
    }


    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<invoiceItemDTO> getInvoiceItemDTOS() {
        return invoiceItemDTOS;
    }

    public void setInvoiceItemDTOS(List<invoiceItemDTO> invoiceItemDTOS) {
        this.invoiceItemDTOS = invoiceItemDTOS;
    }

    public static invoiceDTO of(Pedido pedido, List<Item> items, List<invoiceItemDTO> invoiceitemDTO) {

      double cont = 0;
        for(Item item :items){
          cont+=(item.getQuantidade()*item.getIdProduto().getPreco());
        }

        List<invoiceItemDTO> listinha = new ArrayList<>();
        for (Item item : items) {
            invoiceItemDTO invoiceItemDTO = new invoiceItemDTO();
            invoiceItemDTO.setAmount(item.getQuantidade());
            invoiceItemDTO.setItemName(item.getIdProduto().getNomeProduto());
            listinha.add(invoiceItemDTO);
        }

            return new invoiceDTO(
                    pedido.getPedidoFornecedor().getCnpj(),
                    pedido.getPedidoFuncionario().getUuid(),
                    cont,
                    listinha
                );
    }

}
