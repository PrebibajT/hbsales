package br.com.hbsis.categoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Categorias")
public class CategoriasRest {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CategoriasRest.class);

    private final CategoriasService categoriasService;

    @Autowired
    public CategoriasRest(CategoriasService categoriasService){
        this.categoriasService = categoriasService;
    }

    @PostMapping
    public CategoriasDTO save(@RequestBody CategoriasDTO categoriasDTO){
        LOGGER.info("Recebendo solicitação de persistência de categorias");
        LOGGER.debug("Payload: {}",categoriasDTO);

        return this.categoriasService.save(categoriasDTO);
    }

    @PutMapping("/{id}")
    public CategoriasDTO update(@PathVariable("id") Long codigoCategoria, @RequestBody CategoriasDTO categoriasDTO) {
        LOGGER.info("Recebendo Update para produtos de ID: {}", codigoCategoria);
        LOGGER.debug("Payload: {}", categoriasDTO);

        return this.categoriasService.update(categoriasDTO, codigoCategoria);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para categorias de ID: {}", id);

        this.categoriasService.delete(id);
    }

    @GetMapping("/exportar")
    public void exportar() throws Exception{

        LOGGER.info("Exportando categorias");

         this.categoriasService.exportar();

    }
}