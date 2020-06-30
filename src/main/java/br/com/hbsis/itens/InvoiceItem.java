package br.com.hbsis.itens;

import java.util.ArrayList;
import java.util.List;

public class InvoiceItem {
    private Long amount;
    private String itemName;

    public InvoiceItem() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public static List<InvoiceItem> of(List<Item> items) {

        List<InvoiceItem> listinha = new ArrayList<>();

        for (Item item : items) {
            InvoiceItem invoiceItemDTOset = new InvoiceItem();

            invoiceItemDTOset.setAmount(item.getQuantidade());
            invoiceItemDTOset.setItemName(item.getIdProduto().getNomeProduto());
            listinha.add(invoiceItemDTOset);
        }
        return listinha;

    }
}
