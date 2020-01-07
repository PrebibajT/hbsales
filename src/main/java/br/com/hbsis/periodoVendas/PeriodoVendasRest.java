package br.com.hbsis.periodoVendas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodoVendas")
public class PeriodoVendasRest {

    private static  final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendasRest.class);

    private final PeriodoVendasService periodoVendasService;

    @Autowired
    public PeriodoVendasRest(PeriodoVendasService periodoVendasService){
        this.periodoVendasService = periodoVendasService;
    }

    @PostMapping
    public PeriodoVendasDTO save(@RequestBody PeriodoVendasDTO periodoVendasDTO){
        LOGGER.info("Recebendo solicitação de persistência de categorias");
        LOGGER.debug("Payload: {}",periodoVendasDTO);

        return this.periodoVendasService.save(periodoVendasDTO);
    }

    @PutMapping("/{id}")
    public PeriodoVendasDTO update(@PathVariable("id") Long id, @RequestBody PeriodoVendasDTO periodoVendasDTO) {
        LOGGER.info("Recebendo Update para produtos de ID: {}", id);
        LOGGER.debug("Payload: {}", periodoVendasDTO);

        return this.periodoVendasService.update(periodoVendasDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para categorias de ID: {}", id);

        this.periodoVendasService.delete(id);
    }


}
