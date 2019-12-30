package br.com.hbsis.produtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/produtos")
public class ProdutosRest {

    private static  final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);

    private final ProdutosService produtosService;

    @Autowired
    public ProdutosRest(ProdutosService produtosService){
        this.produtosService = produtosService;
    }

    @PostMapping
    public ProdutosDTO save(@RequestBody ProdutosDTO produtosDTO){
        LOGGER.info("Recebendo solicitação de persistência de produtinhos");
        LOGGER.debug("Payload: {}",produtosDTO);

        return this.produtosService.save(produtosDTO);
    }

    @PutMapping("/{id}")
    public ProdutosDTO update(@PathVariable("id") Long codigoProduto, @RequestBody ProdutosDTO produtosDTO) {
        LOGGER.info("Recebendo Update para produtos de ID: {}", codigoProduto);
        LOGGER.debug("Payload: {}", produtosDTO);

        return this.produtosService.update(produtosDTO, codigoProduto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para produtos de ID: {}", id);

        this.produtosService.delete(id);
    }

    @GetMapping("/exportar")
    public void exportar(HttpServletResponse response) throws Exception{

        LOGGER.info("Exportando produtos");

        this.produtosService.exportar(response);

    }

    @PostMapping("/importar")
    public void importar(@RequestParam("file") MultipartFile file) throws Exception {
        LOGGER.info("Importando produtos");

        this.produtosService.importar(file);

}
    @PostMapping("/importarOmega/{idFornecedor}")
    public void  importarOmega(@PathVariable("idFornecedor") Long idFornecedor, @RequestParam("file") MultipartFile file ) throws Exception {
        LOGGER.info("Importando produtos  O M E G A ");

         this.produtosService.importarOmega(file, idFornecedor);
    }

}