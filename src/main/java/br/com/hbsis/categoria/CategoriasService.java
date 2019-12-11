package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorDTO;
import br.com.hbsis.Fornecedor.FornecedorService;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.br.CNPJ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriasService.class);

    private final ICategoriasRepository iCategoriasRepository;
    private final FornecedorService fornecedorService;

    public CategoriasService(ICategoriasRepository icategoriasRepository, FornecedorService fornecedorService) {
        this.iCategoriasRepository = icategoriasRepository;
        this.fornecedorService = fornecedorService;
    }

    public CategoriasDTO save(CategoriasDTO categoriasDTO) {

        this.validate(categoriasDTO);

        LOGGER.info("Salvando produtos");
        LOGGER.debug("Produtos: {}", categoriasDTO);

        Categorias categorias = new Categorias();

        categorias.setCodigoCategoria(categoriasDTO.getCodigoCategoria());

        if(categoriasDTO.getCodigoCategoria().length() < 3 ){
            String minhaVariavelConcatenada = "0" + "0" + categoriasDTO.getCodigoCategoria();
            categoriasDTO.setCodigoCategoria(minhaVariavelConcatenada);
        }
        if(categoriasDTO.getCodigoCategoria().length() < 2 ){
            String minhaVariavelConcatenada = "0" + categoriasDTO.getCodigoCategoria();
            categoriasDTO.setCodigoCategoria(minhaVariavelConcatenada);

        }

        categorias.setNomeCategoria(categoriasDTO.getNomeCategoria());

        Fornecedor byFornecedorId = fornecedorService.findByFornecedorId(categoriasDTO.getIdFornecedor());
        categorias.setFornecedorCategoria(byFornecedorId);

        categorias.setCodigoCategoria(this.reValidate(byFornecedorId, categoriasDTO));

        categorias = this.iCategoriasRepository.save(categorias);

        return CategoriasDTO.of(categorias);
    }

    public Categorias findByCodigoCategoria (String codigoCategoria){
            Optional<Categorias> codigoQTem = this.iCategoriasRepository.findByCodigoCategoria(codigoCategoria);

        if (codigoQTem.isPresent()){
            return codigoQTem.get();

        }
        throw new IllegalArgumentException("O código da categoria não é para ser null, seu mongol");
    }

    private void validate(CategoriasDTO categoriasDTO) {
        LOGGER.info("Validando produtos");

        if (categoriasDTO == null) {
            throw new IllegalArgumentException("CategoriasDTO  não deve ser nulo");
        }
        if (categoriasDTO.getIdFornecedor() == null) {
            throw new IllegalArgumentException("O fornecedor da categoria não pode ser nulo");
        }

        if (categoriasDTO.getCodigoCategoria() == null) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo");
        }

        if (StringUtils.isEmpty(categoriasDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("O nome da categoria não pode ser nulo");
        }
    }


    public Optional<Categorias> findByCategoriaId(Long id) {
        Optional<Categorias> categoriasOptional = this.iCategoriasRepository.findById(id);

        if (categoriasOptional.isPresent()) {
            return categoriasOptional;
        }

        throw new IllegalArgumentException(String.format("id  %s não existe", id));
    }


    public String reValidate (Fornecedor fornecedor, CategoriasDTO categoriasDTO){

     String subCNPJ= fornecedor.getCnpj().substring(10,14);

      String codigoCompleto = "CAT" + subCNPJ + categoriasDTO.getCodigoCategoria();

        return codigoCompleto;

    }


    public CategoriasDTO update(CategoriasDTO categoriasDTO, Long id) {
        Optional<Categorias> categoriasExistenteOptional = this.iCategoriasRepository.findById(id);

        if (categoriasExistenteOptional.isPresent()) {
            Categorias categoriasExistente = categoriasExistenteOptional.get();

            LOGGER.info("Atualizando produtos.... id:[{}]", categoriasExistente.getCodigoCategoria());
            LOGGER.debug("Payload: {}", categoriasDTO);
            LOGGER.debug("Produtos existente: {}", categoriasExistente);

            categoriasExistente.setNomeCategoria(categoriasDTO.getNomeCategoria());
            categoriasExistente.setCodigoCategoria(categoriasDTO.getCodigoCategoria());

            categoriasExistente.setFornecedorCategoria(fornecedorService.findByFornecedorId(categoriasDTO.getIdFornecedor()));

            categoriasExistente = this.iCategoriasRepository.save(categoriasExistente);
            categoriasExistente.setCodigoCategoria(this.reValidate(categoriasExistente.getFornecedorCategoria(), categoriasDTO));

            return CategoriasDTO.of(categoriasExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID: [{}]", id);

        this.iCategoriasRepository.deleteById(id);
    }


    public void exportar(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"output.csv\"");
        response.setContentType("text/csv");
        List<Categorias> express = iCategoriasRepository.findAll();

        PrintWriter myWriter = response.getWriter();


        myWriter.append("Codigo" + ";" + "Nome" +";" + "Razão social" + ";" + "CNPJ");

        for (Categorias categorias : express) {

            myWriter.append("\n" +categorias.getCodigoCategoria()+ ";");
            myWriter.append(categorias.getNomeCategoria() + ";");
            myWriter.append(categorias.getFornecedorCategoria().getRazaoSocial()+ ";");

            String doisPrimeiros = categorias.getFornecedorCategoria().getCnpj().substring(0,2);
            String tresSegundos= categorias.getFornecedorCategoria().getCnpj().substring(2,5);
            String tresTerceiros = categorias.getFornecedorCategoria().getCnpj().substring(5,8);
            String penultimosQartos = categorias.getFornecedorCategoria().getCnpj().substring(8,12);
            String ultimosDois = categorias.getFornecedorCategoria().getCnpj().substring(12,14);
            String cnpjDisfarcado = doisPrimeiros + "." + tresSegundos + "." + tresTerceiros + "/" +
                    penultimosQartos + "-" + ultimosDois;

            myWriter.append(cnpjDisfarcado);


            myWriter.flush();
        }
    }


    public void importar(MultipartFile file) throws IOException {

        Categorias categorias = new Categorias();

        BufferedReader myReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = "";
        String splitBy = ";";

        line = myReader.readLine();
        while ((line = myReader.readLine()) != null){
            String[] categoria = line.split(splitBy);

           categorias.setCodigoCategoria(categoria[0]);
           categorias.setNomeCategoria(categoria[1]);

            String cnpjMasked = categoria[3];
            String desmascaradoCnpj = cnpjMasked.substring(0,2) + cnpjMasked.substring(3,6) +cnpjMasked.substring(7,10) +
                                      cnpjMasked.substring(11,15) +cnpjMasked.substring(16,18) ;

            Optional<Fornecedor> fornecedor = this.fornecedorService.findByCnpj(desmascaradoCnpj);
            categorias.setFornecedorCategoria(fornecedor.get());

            this.iCategoriasRepository.save(categorias);

        }
    }
}