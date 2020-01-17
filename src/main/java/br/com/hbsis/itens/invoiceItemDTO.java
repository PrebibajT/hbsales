package br.com.hbsis.itens;

import java.util.ArrayList;
import java.util.List;

public class invoiceItemDTO {
    Long amount;
    String itemName;

    public invoiceItemDTO() {
    }

    public invoiceItemDTO(Long amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
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

    public static List<invoiceItemDTO> of(List<Item> items) {
        List<invoiceItemDTO> listinha = new ArrayList<>();
        for (Item item : items) {
            invoiceItemDTO invoiceItemDTO = new invoiceItemDTO();
            invoiceItemDTO.setAmount(item.getQuantidade());
            invoiceItemDTO.setItemName(item.getIdProduto().getNomeProduto());
            listinha.add(invoiceItemDTO);
        }
        return listinha;

    }


}
