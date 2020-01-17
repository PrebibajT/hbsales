package br.com.hbsis.pedido;


import br.com.hbsis.periodoVendas.PeriodoVendasDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.print.PeekGraphics;

@RestController
@RequestMapping("/pedido")
public class PedidoRest {


    private static  final Logger LOGGER = LoggerFactory.getLogger(PedidoRest.class);

    private final PedidoService pedidoService;

    @Autowired
    public PedidoRest(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }


    @PostMapping
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO){
        LOGGER.info("Recebendo solicitação de persistência de pedido");
        LOGGER.debug("Payload: {}",pedidoDTO);

        return this.pedidoService.save(pedidoDTO);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable("id") Long id, @RequestBody PedidoDTO pedidoDTO) {
        LOGGER.info("Recebendo Update para pedido de ID: {}", id);
        LOGGER.debug("Payload: {}", pedidoDTO);

        return this.pedidoService.update(pedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para categorias de ID: {}", id);

        this.pedidoService.delete(id);

    }

}
