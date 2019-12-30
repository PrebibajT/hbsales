package br.com.hbsis.produtos;


import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasDTO;
import br.com.hbsis.categoria.CategoriasService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linha.Linha;
import br.com.hbsis.linha.LinhaDTO;
import br.com.hbsis.linha.LinhaService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosService.class);

    private final IProdutosRepository iProdutosRepository;
    private final LinhaService linhaService;
    private final CategoriasService categoriasService;
    private final FornecedorService fornecedorService;


    public ProdutosService(IProdutosRepository iProdutosRepository, LinhaService linhaService, CategoriasService categoriasService, FornecedorService fornecedorService) {
        this.iProdutosRepository = iProdutosRepository;
        this.linhaService = linhaService;
        this.categoriasService = categoriasService;
        this.fornecedorService = fornecedorService;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) {

        this.validate(produtosDTO);

        LOGGER.info("Salvando produtos");
        LOGGER.debug("Produtos: {}", produtosDTO);

        Produtos produtos = new Produtos();

        produtos.setCodigoProduto(produtosDTO.getCodigoProduto().toUpperCase());
        StringBuilder produtationConcatenation = new StringBuilder();

        if (produtosDTO.getCodigoProduto().length() != 10) {
            int cata = 10 - produtosDTO.getCodigoProduto().length();

            for (int i = 0; i < cata; i++) {
                produtationConcatenation.append("0");
                System.out.println(produtationConcatenation);

            }

            String concatenationProdutation = produtationConcatenation.toString() + produtosDTO.getCodigoProduto();
            concatenationProdutation = concatenationProdutation.toUpperCase();
            produtos.setCodigoProduto(concatenationProdutation);
        }

        Optional<Linha> linha = this.linhaService.findByIdLinha(produtosDTO.getIdLinha());

        produtos.setLinhaCategoria(linha.get());
        produtos.setNomeProduto(produtosDTO.getNomeProduto());
        produtos.setPesoMedida(produtosDTO.getPesoMedida());
        produtos.setPesoUnidade(produtosDTO.getPesoUnidade());
        produtos.setPreco(produtosDTO.getPreco());
        produtos.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
        produtos.setValidade(produtosDTO.getValidade());
        produtos = this.iProdutosRepository.save(produtos);

        return ProdutosDTO.of(produtos);

    }

    private void validate(ProdutosDTO produtosDTO) {
        LOGGER.info("Validando produtos");

        if (produtosDTO == null) {
            throw new IllegalArgumentException("produtosDTO  não é pra ser nulo, animal");
        }

        if (StringUtils.isEmpty(produtosDTO.getCodigoProduto())) {
            throw new IllegalArgumentException("O codigo do produto não pode ser nulo, burrão");
        }

        if (StringUtils.isEmpty(produtosDTO.getNomeProduto())) {
            throw new IllegalArgumentException("O nome do produto ta nullo ");
        }

        if (produtosDTO.getPreco() == null) {
            throw new IllegalArgumentException("O preco do produto ta nullo");
        }

        if (produtosDTO.getIdLinha() == null) {
            throw new IllegalArgumentException("O idLinha do produto ta nulo, cara de coruja seca");
        }

        if (produtosDTO.getUnidadeCaixa() == null) {
            throw new IllegalArgumentException("O idLinha do produto ta nulo, vo te bater mlk");
        }

        if (produtosDTO.getPesoUnidade() == null) {
            throw new IllegalArgumentException("O peso da Unidade do produto ta nulo, tanso");
        }

        if (StringUtils.isEmpty(produtosDTO.getValidade().toString())) {
            throw new IllegalArgumentException("A validade não pode ser nula, mongol");
        }

        if (StringUtils.isEmpty(produtosDTO.getPesoMedida())) {
            throw new IllegalArgumentException("O peso da medida do produto ta nulo, seu burro");


        } else if (produtosDTO.getPesoMedida().equals("mg")) {


        } else if (produtosDTO.getPesoMedida().equals("g")) {


        } else if (produtosDTO.getPesoMedida().equals("Kg")) {


        } else {
            throw new IllegalArgumentException("O peso da medida do produto não vai aceitar isso, seu fudido");
        }
    }

    public Optional<Produtos> findByCodigoProduto(String codigoProduto) {
        Optional<Produtos> productsOptional = this.iProdutosRepository.findByCodigoProduto(codigoProduto);

            return productsOptional;

    }

    public Produtos findByCodigoProdutos(String codigoProduto){
        Optional <Produtos> produtosPeperoni = this.iProdutosRepository.findByCodigoProduto(codigoProduto);

        return produtosPeperoni.get();

    }


    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()) {
            Produtos produtosSuper = produtosOptional.get();

            LOGGER.info("Atualizando produtos.... id:[{}]", produtosSuper.getCodigoProduto());
            LOGGER.debug("Payload: {}", produtosDTO);
            LOGGER.debug("Produtos existente: {}", produtosSuper);


            Optional<Linha> linha = this.linhaService.findByIdLinha(produtosDTO.getIdLinha());

            produtosSuper.setLinhaCategoria(linha.get());
            produtosSuper.setNomeProduto(produtosDTO.getNomeProduto());
            produtosSuper.setPesoMedida(produtosDTO.getPesoMedida());
            produtosSuper.setPesoUnidade(produtosDTO.getPesoUnidade());
            produtosSuper.setPreco(produtosDTO.getPreco());
            produtosSuper.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
            produtosSuper.setValidade(produtosDTO.getValidade());
            produtosSuper.setCodigoProduto(produtosDTO.getCodigoProduto());

            produtosSuper = this.iProdutosRepository.save(produtosSuper);
            return ProdutosDTO.of(produtosSuper);

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID: [{}]", id);

        this.iProdutosRepository.deleteById(id);
    }


    public void exportar(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"output.csv\"");
        response.setContentType("text/csv");
        List<Produtos> express = iProdutosRepository.findAll();

        PrintWriter miEscritor = response.getWriter();

        miEscritor.append("Codigo produto" + ";" + "Nome" + ";" + "Preço" + ";" + "Unidades de caixa" + ";" + "Peso por unidade" + ";" + "Validade" +
                ";" + "Código linha" + ";" + "Nome linha" + ";" + "Código categoria" + ";" + "Nome categoria" + ";" + "CNPJ fornecedor"
                + ";" + "Razão social");

        for (Produtos produtos : express) {

            miEscritor.append("\n" + produtos.getCodigoProduto() + ";");
            miEscritor.append(produtos.getNomeProduto() + ";");
            miEscritor.append(produtos.getPreco() + ";");
            miEscritor.append(produtos.getUnidadeCaixa() + ";");
            miEscritor.append(produtos.getPesoUnidade() + produtos.getPesoMedida() + ";");
            miEscritor.append(produtos.getValidade() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCodigoLinha() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getNome() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getCodigoCategoria() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getNomeCategoria() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getCnpj() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getRazaoSocial());

            miEscritor.flush();

        }

    }

    public void importar(MultipartFile file) throws IOException {

        //    /-----------------------Produtos---------------------------\         /-------Linha-------\    \\
        //  Codigo produto || Nome || Preço || Unidades de caixa || Validade ||| Código linha || Nome linha  \\

        Produtos produtos = new Produtos();

        BufferedReader miLector = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = "";
        String splitBy = ";";

        line = miLector.readLine();
        while ((line = miLector.readLine()) != null) {
            String[] produto = line.split(splitBy);

            //não azedou
            produtos.setCodigoProduto(produto[0]);
            produtos.setNomeProduto(produto[1]);
            produtos.setPreco(Double.parseDouble(produto[2]));
            produtos.setUnidadeCaixa(Integer.parseInt(produto[3]));

            String medida = produto[4].replaceAll("[0-9.]", "");
            String peso = produto[4].replaceAll("[^\\d.]", "");
            String data = produto[5];//  09/02/2020
            String usaEsse = (produto[6]);

            Optional<Linha> linha = linhaService.findByCodigoLinha(usaEsse);

            produtos.setPesoMedida(medida);
            produtos.setPesoUnidade(Double.parseDouble(peso));
            produtos.setValidade(LocalDate.parse(data));
            produtos.setLinhaCategoria(linha.get());


            if (linha.isPresent()) {

                this.iProdutosRepository.save(produtos);
                LOGGER.info("Tudo ok mestre, por enquanto");

            }
            else {
                throw new IllegalArgumentException("Azedo em mestre e não foi pouco");

            }

        }

    }


    public void importarOmega(MultipartFile file, Long idFornecedor) throws IOException {

        ProdutosDTO produtosDTO = new ProdutosDTO();
        LinhaDTO linhaDTO = new LinhaDTO();
        CategoriasDTO categoriasDTO = new CategoriasDTO();

        BufferedReader mioLettore = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = "";
        String splitBy = ";";

        line = mioLettore.readLine();

        while ((line = mioLettore.readLine()) != null) {
            String[] produtoOMEGA = line.split(splitBy);

            Optional <Categorias> categoriasFornecedor = categoriasService.findByCodigoCategoria(produtoOMEGA[8]);
            Optional <Linha> linhaFornecedor = linhaService.findByCodigoLinha(produtoOMEGA[6]);
            Optional <Produtos> produtosFornecedor = this.iProdutosRepository.findByCodigoProduto(produtoOMEGA[0]);

                if (!categoriasFornecedor.isPresent()) { //cria um novo
                    LOGGER.info("criando nova categoria ");

                    categoriasDTO.setCodigoCategoria(produtoOMEGA[8]);
                    categoriasDTO.setNomeCategoria(produtoOMEGA[9]);
                    categoriasDTO.setIdFornecedor(idFornecedor);

                    categoriasService.save(categoriasDTO);

                } else  {//update
                    LOGGER.info("atualizando categoria já existente ");

                    Long idCat = categoriasService.findByCodigoCategorias(produtoOMEGA[8]).getId();

                    categoriasDTO.setCodigoCategoria(produtoOMEGA[8]);

                    LOGGER.info("criando nova linha ");

                    categoriasDTO.setNomeCategoria(produtoOMEGA[9]);
                    categoriasDTO.setIdFornecedor(idFornecedor);
                    categoriasDTO.setId(idCat);

                    categoriasService.update( categoriasDTO, idCat);

                }


                // ERRO AQ
                if (!linhaFornecedor.isPresent()) { //cria um novo
                    LOGGER.info("criando nova linha ");

                    Long idCat = categoriasService.findByCodigoCategorias(produtoOMEGA[8]).getId();

                    linhaDTO.setCodigoLinha(produtoOMEGA[6]);
                    linhaDTO.setNome(produtoOMEGA[7]);
                    linhaDTO.setIdCategoria(idCat);

                    linhaService.save(linhaDTO);


                } else {//update
                    LOGGER.info("atualizando linha já existente ");

                    Long idLin = linhaService.findByCodigoLinhas(produtoOMEGA[6]).getId();
                    Long idCat = categoriasService.findByCodigoCategorias(produtoOMEGA[8]).getId();

                    linhaDTO.setCodigoLinha(produtoOMEGA[6]);
                    linhaDTO.setNome(produtoOMEGA[7]);
                    linhaDTO.setIdCategoria(idCat);

                    linhaService.update(linhaDTO, idLin);

                }


//                if (!produtosFornecedor.isPresent()) { //cria um novo
//
//                    LOGGER.info("criando nova produtos ");
//                    produtosDTO.setCodigoProduto(produtoOMEGA[0]);
//                    produtosDTO.setNomeProduto(produtoOMEGA[1]);
//                    produtosDTO.setPreco(Double.parseDouble(produtoOMEGA[2]));
//                    produtosDTO.setUnidadeCaixa(Integer.parseInt(produtoOMEGA[3]));
//
//                    String medida = produtoOMEGA[4].replaceAll("[0-9.]", "");
//                    String peso = produtoOMEGA[4].replaceAll("[^\\d.]", "");
//                    String data = produtoOMEGA[5];//  09/02/2020
//
//                    produtosDTO.setPesoMedida(medida);
//                    produtosDTO.setPesoUnidade(Double.parseDouble(peso));
//                    produtosDTO.setValidade(LocalDate.parse(data));
//
//
//                    produtosDTO.setIdLinha(idLin);
//
//
//                    this.iProdutosRepository.save(produtosDTO);
//
//                } else {//update
//
//                    LOGGER.info("atualizando produtos já existente ");
//                    update(produtosDTO, produtosDTO.getId());
//
//                }


        }

    }
}