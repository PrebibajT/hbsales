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



//    private void validaAPI(ItensDTO itensDTO) {
//        RestTemplate template = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "f5a00866-1b67-11ea-978f-2e728ce88125");
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        HttpEntity httpEntity = new HttpEntity(itensDTO, headers);
//        ResponseEntity<ItensDTO> response = template.exchange("http://10.2.54.25:9999/api/invoice", HttpMethod.POST, httpEntity, ItensDTO.class);
//
//    }

}