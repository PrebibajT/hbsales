package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.FornecedorService;
import com.opencsv.CSVReader;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.Id;
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
        categorias.setNomeCategoria(categoriasDTO.getNomeCategoria());
        categorias.setFornecedorCategoria(fornecedorService.findByFornecedorId(categoriasDTO.getIdFornecedor()));

        categorias = this.iCategoriasRepository.save(categorias);

        return CategoriasDTO.of(categorias);
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

    public CategoriasDTO findByCodigoCategoria(Long codigoCategoria) {
        Optional<Categorias> categoriasOptional = this.iCategoriasRepository.findById(codigoCategoria);

        if (categoriasOptional.isPresent()) {
            return CategoriasDTO.of(categoriasOptional.get());
        }

        throw new IllegalArgumentException(String.format("codigo da categoria  %s não existe", codigoCategoria));

    }

    public CategoriasDTO update(CategoriasDTO categoriasDTO, Long id) {
        Optional<Categorias> categoriasExistenteOptional = this.iCategoriasRepository.findById(id);

        if (categoriasExistenteOptional.isPresent()) {
            Categorias categoriasExistente = categoriasExistenteOptional.get();


            LOGGER.info("Atualizando produtos.... id:[{}]", categoriasExistente.getCodigoCategoria());
            LOGGER.debug("Payload: {}", categoriasDTO);
            LOGGER.debug("Produtos existente: {}", categoriasExistente);

            categoriasExistente.setNomeCategoria(categoriasDTO.getNomeCategoria());
            categoriasExistente.setFornecedorCategoria(fornecedorService.findByFornecedorId(categoriasDTO.getIdFornecedor()));

            categoriasExistente.setCodigoCategoria(categoriasDTO.getCodigoCategoria());

            categoriasExistente = this.iCategoriasRepository.save(categoriasExistente);

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


        myWriter.append("ID" + ";" + "Nome" + ";" + "Codigo" + ";" + "Fornecedor");

        for (Categorias categorias : express) {

            myWriter.append("\n" + categorias.getId().toString() + ";");
            myWriter.append(categorias.getNomeCategoria() + ";");
            myWriter.append(categorias.getCodigoCategoria().toString() + ";");
            myWriter.append(categorias.getFornecedorCategoria().getId().toString());

            myWriter.flush();
        }
    }

    public void importar(MultipartFile file) throws IOException {

        CategoriasDTO categoriasDTO = new CategoriasDTO();

        BufferedReader myReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = "";
        String splitBy = ";";

        line = myReader.readLine();
        while ((line = myReader.readLine()) != null){
            String[] Categoria = line.split(splitBy);

            categoriasDTO.setId(Long.parseLong(Categoria[0]));
            categoriasDTO.setNomeCategoria(Categoria[1]);
            categoriasDTO.setCodigoCategoria(Long.parseLong(Categoria[2]));
            categoriasDTO.setIdFornecedor(Long.parseLong(Categoria[3]));

            save(categoriasDTO);
        }
    }
}