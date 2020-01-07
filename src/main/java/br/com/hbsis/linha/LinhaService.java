package br.com.hbsis.linha;

import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaService.class);

    private final ILinhaRepository iLinhaRepository;
    private final CategoriasService categoriasService;


    public LinhaService(ILinhaRepository ilinhaRepository, CategoriasService categoriasService) {
        this.iLinhaRepository = ilinhaRepository;
        this.categoriasService = categoriasService;

    }

    public LinhaDTO save(LinhaDTO linhaDTO) {

        this.validate(linhaDTO);

        LOGGER.info("Salvando linha");
        LOGGER.debug("linha: {}", linhaDTO);

        Linha linha = new Linha();

        linha.setCodigoLinha(linhaDTO.getCodigoLinha().toUpperCase());
        StringBuilder concatenationVariation = new StringBuilder();

        if (linha.getCodigoLinha().length() != 10) {
            int resto = 10 - linhaDTO.getCodigoLinha().length();


            for (int i = 0; i < resto; i++) {
                concatenationVariation.append("0");


            }

            String variationConcdatenation = concatenationVariation.toString() + linhaDTO.getCodigoLinha();
            variationConcdatenation = variationConcdatenation.toUpperCase();

            linha.setCodigoLinha(variationConcdatenation);
            System.out.println(linhaDTO.getCodigoLinha());


        }else if(linhaDTO.getCodigoLinha().length() == 10) {

            Categorias categorias = categoriasService.findById(linhaDTO.getIdCategoria());

            linha.setNome(linhaDTO.getNome());
            linha.setCategoriaLinha(categorias);


            linha = this.iLinhaRepository.save(linha);

            return LinhaDTO.of(linha);

        }else{

            throw new IllegalArgumentException("Fudeu bahia");
        }


        Categorias categorias = categoriasService.findById(linhaDTO.getIdCategoria());


        linha.setNome(linhaDTO.getNome());
        linha.setCategoriaLinha(categorias);


        linha = this.iLinhaRepository.save(linha);

        return LinhaDTO.of(linha);


    }

    private void validate(LinhaDTO linhaDTO) {
        LOGGER.info("Validando Linha");

        if (linhaDTO == null) {
            throw new IllegalArgumentException("LinhaDTO  não deve ser nulo");
        }

        if (StringUtils.isEmpty(linhaDTO.getCodigoLinha())) {
            throw new IllegalArgumentException("O código da linha não pode ser nulo");
        }


        if (linhaDTO.getIdCategoria() == null) {
            throw new IllegalArgumentException("A linha da categoria não pode ser nulo");
        }

        if (StringUtils.isEmpty(linhaDTO.getNome())) {
            throw new IllegalArgumentException("O nome da linha não pode ser nulo");
        }

    }


    public LinhaDTO findById(Long id) {
        Optional<Linha> linhaSuper = this.iLinhaRepository.findById(id);

        if (linhaSuper.isPresent()) {
            return LinhaDTO.of(linhaSuper.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public Optional <Linha> findByCodigoLinha(String codigoLinha) {
        Optional<Linha> linhaOptional = this.iLinhaRepository.findByCodigoLinha(codigoLinha);

            return linhaOptional;

    }


    public Linha findByIdLinha(Long idLinha) {
        Optional<Linha> linhaOptionale = this.iLinhaRepository.findById(idLinha);

            return linhaOptionale.get();


    }

    public Linha findByCodigoLinhas (String codigoLinha){
        Optional<Linha> linhaPeperoni = this.iLinhaRepository.findByCodigoLinha(codigoLinha);

        return linhaPeperoni.get();

    }



    public LinhaDTO update(LinhaDTO linhaDTO, Long id) {
        Optional<Linha> linhaSuperOptional = this.iLinhaRepository.findById(id);

        if (linhaSuperOptional.isPresent()) {
            Linha linhaSuper = linhaSuperOptional.get();

            LOGGER.info("Atualizando linha.... id:[{}]", linhaSuper.getCodigoLinha());
            LOGGER.debug("Payload: {}", linhaDTO);
            LOGGER.debug("linha existente: {}", linhaSuper);


            Categorias categorias = categoriasService.findById(linhaDTO.getIdCategoria());

            linhaSuper.setCategoriaLinha(categorias);
            linhaSuper.setNome(linhaDTO.getNome());
            linhaSuper.setCodigoLinha(linhaDTO.getCodigoLinha());

            linhaSuper = this.iLinhaRepository.save(linhaSuper);

            return LinhaDTO.of(linhaSuper);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public void delete(Long id) {
        LOGGER.info("Executando delete para linha de ID: [{}]", id);

        this.iLinhaRepository.deleteById(id);
    }


    public void exportar(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"output.csv\"");
        response.setContentType("text/csv");

        List<Linha> express = iLinhaRepository.findAll();
        PrintWriter myWriter = response.getWriter();

        myWriter.append("Codigo linha" + ";" + "Nome" + ";" + "Codigo categoria" + ";" + "Nome categoria");

        for (Linha linha : express) {
            myWriter.append("\n" + linha.getCodigoLinha() + ";");
            myWriter.append(linha.getNome() + ";");
            myWriter.append(linha.getCategoriaLinha().getCodigoCategoria() + ";");
            myWriter.append(linha.getCategoriaLinha().getNomeCategoria());

            myWriter.flush();
        }
    }

    public void importar(MultipartFile file) throws IOException {

        Linha linha = new Linha();

        BufferedReader meuLeitor = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = "";
        String splitBy = ";";

        line = meuLeitor.readLine();
        while ((line = meuLeitor.readLine()) != null) {
            String[] linhas = line.split(splitBy);

            linha.setCodigoLinha(linhas[0]);
            linha.setNome(linhas[1]);

            String esseVai = linhas[2];

           Optional <Categorias> categoriasOptional = categoriasService.findByCodigoCategoria(esseVai);
            if (categoriasOptional.isPresent()) {

                            linha.setCategoriaLinha(categoriasOptional.get());
                            linha.getCategoriaLinha().setNomeCategoria(linhas[3]);
                            this.iLinhaRepository.save(linha);
                            LOGGER.info("Tudo ok mestre");

            }
                        else {
                         throw new IllegalArgumentException("Azedo mestre");
                        }

        }

    }

}
