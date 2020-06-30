package br.com.hbsis.linha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/linha")
public class LinhaRest {
    private static  final Logger LOGGER = LoggerFactory.getLogger(LinhaRest.class);

    private final LinhaService linhaService;

   @Autowired
   public LinhaRest(LinhaService linhaService){
       this.linhaService = linhaService;
   }

    @PostMapping
    public LinhaDTO save(@RequestBody LinhaDTO linhaDTO){
        LOGGER.info("Recebendo solicitação de persistência de linha...");
        LOGGER.debug("Payload: {}", linhaDTO);
        LinhaDTO save = this.linhaService. save(linhaDTO);
        return save;
    }
    
    @GetMapping("/{id}")
    public LinhaDTO find(@PathVariable("id")Long id ){
        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.linhaService.findById(id);
    }

    @PutMapping("/{id}")
    public LinhaDTO update (@PathVariable("id") Long id, @RequestBody LinhaDTO linhaDTO){
        LOGGER.info("Recebendo Update para linha de ID: {}", id);
        LOGGER.debug("Payload: {}", linhaDTO);

       return this.linhaService.update(linhaDTO, id);
    }
   @DeleteMapping("/{id}")
   public void delete(@PathVariable("id") Long id) {
       LOGGER.info("Recebendo Delete para linah de ID: {}", id);

       this.linhaService.delete(id);
   }

    @GetMapping("/exportar")
    public void exportar(HttpServletResponse response) throws Exception{
        LOGGER.info("Exportando categorias");
        
        this.linhaService.exportar(response);
    }

    @GetMapping("/importar")
    public void importar(@RequestParam("file") MultipartFile file) throws Exception {
        LOGGER.info("Importando categorias");

        this.linhaService.importar(file);
    }
}
