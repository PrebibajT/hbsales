package br.com.hbsis.itens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItensService {

 private static final Logger LOGGER = LoggerFactory.getLogger(ItensService.class);

    private final IItemRepository iItemRepository;

    public ItensService(IItemRepository iItemRepository) {
        this.iItemRepository = iItemRepository;

    }

    public Item findByIdPedido(Long idPedido){
        Optional<Item> Itemxx = this.iItemRepository.findById(idPedido);

        if(Itemxx.isPresent()){
            return Itemxx.get();
        }

        throw new IllegalArgumentException(String.format("id  %s não existe", idPedido));

    }

    public Item findByIdProduto (Long idProduto){

        return this.iItemRepository.findByIdProduto(idProduto);

    }





}