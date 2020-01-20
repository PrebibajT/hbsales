package br.com.hbsis.itens;

import br.com.hbsis.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDTO {
    private String cnpjFornecedor;
    private String employeeUuid;
    private Double totalValue;
    private List<InvoiceItem> invoiceItemDTOSet = new ArrayList<>();

    public InvoiceDTO() {
    }

    public InvoiceDTO(String cnpjFornecedor, String employeeUuid, Double totalValue, List<InvoiceItem> invoiceItemDTOSet) {
        this.cnpjFornecedor = cnpjFornecedor.replaceAll("/", "")
                .replaceAll("\\.", "")
                .replaceAll("-", "");
        this.employeeUuid = employeeUuid;
        this.totalValue = totalValue;
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor
                .replaceAll("/", "")
                .replaceAll("\\.", "");
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

    public List<InvoiceItem> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(List<InvoiceItem> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public static InvoiceDTO of(Pedido pedido, List<Item> items) {

        double cont = 0;
        for (Item item : items) {
            cont += (item.getQuantidade() * item.getIdProduto().getPreco());
        }

        return new InvoiceDTO(
                pedido.getPedidoFornecedor().getCnpj(),
                pedido.getPedidoFuncionario().getUuid(),
                cont,
                InvoiceItem.of(items)
        );
    }


}
