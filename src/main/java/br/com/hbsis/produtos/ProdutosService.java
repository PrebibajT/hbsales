package br.com.hbsis.produtos;

import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasDTO;
import br.com.hbsis.categoria.CategoriasService;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.linha.Linha;
import br.com.hbsis.linha.LinhaDTO;
import br.com.hbsis.linha.LinhaService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    private final FuncionarioService funcionarioService;

    public ProdutosService(IProdutosRepository iProdutosRepository, LinhaService linhaService, CategoriasService categoriasService, FornecedorService fornecedorService, FuncionarioService funcionarioService) {
        this.iProdutosRepository = iProdutosRepository;
        this.linhaService = linhaService;
        this.categoriasService = categoriasService;
        this.fornecedorService = fornecedorService;
        this.funcionarioService = funcionarioService;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) {

        this.validate(produtosDTO);

        LOGGER.info("Salvando produtos");
        LOGGER.debug("Produtos: {}", produtosDTO);

        Produtos produtos = new Produtos();

        produtos.setCodigoProduto(produtosDTO.getCodigoProduto().toUpperCase());
        StringBuilder codigoProdutoConcatenando = new StringBuilder();

        if (produtosDTO.getCodigoProduto().length() != 10) {
            int cata = 10 - produtosDTO.getCodigoProduto().length();

            for (int i = 0; i < cata; i++) {
                codigoProdutoConcatenando.append("0");
            }

            String codigoProdutoConcatenado = codigoProdutoConcatenando.toString() + produtosDTO.getCodigoProduto();
            codigoProdutoConcatenadoU = codigoProdutoConcatenado.toUpperCase();
            produtos.setCodigoProduto(codigoProdutoConcatenadoU);
        }

        Linha linha = this.linhaService.findByIdLinha(produtosDTO.getIdLinha());

        produtos.setLinhaCategoria(linha);
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
            throw new IllegalArgumentException("produtosDTO  não é pra ser nulo");
        }

        if (StringUtils.isEmpty(produtosDTO.getCodigoProduto())) {
            throw new IllegalArgumentException("O codigo do produto não pode ser nulo");
        }

        if (StringUtils.isEmpty(produtosDTO.getNomeProduto())) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo");
        }

        if (produtosDTO.getPreco() == null) {
            throw new IllegalArgumentException("O preco do produto não pode ser nulo");
        }

        if (produtosDTO.getIdLinha() == null) {
            throw new IllegalArgumentException("O idLinha do produto não pode ser nulo");
        }

        if (produtosDTO.getUnidadeCaixa() == null) {
            throw new IllegalArgumentException("O idLinha do produto não pode ser nulo");
        }

        if (produtosDTO.getPesoUnidade() == null) {
            throw new IllegalArgumentException("O peso da Unidade do produto não pode ser nulo");
        }

        if (StringUtils.isEmpty(produtosDTO.getValidade().toString())) {
            throw new IllegalArgumentException("A validade não pode ser nula");
        }

        if (StringUtils.isEmpty(produtosDTO.getPesoMedida())) {
            throw new IllegalArgumentException("O peso da medida do produto não pode ser nulo");


        } else if (produtosDTO.getPesoMedida().equals("mg")) {
            LOGGER.info("Ok é em mg");

        } else if (produtosDTO.getPesoMedida().equals("g")) {
            LOGGER.info("Ok é em g");

        } else if (produtosDTO.getPesoMedida().equals("Kg")) {
            LOGGER.info("Ok é em Kg");

        } else {
            throw new IllegalArgumentException("O peso da medida do produto só vai aceitar: mg / kg / g");
        }
    }


    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()) {
            Produtos produtosSuper = produtosOptional.get();

            LOGGER.info("Atualizando produtos.... id:[{}]", produtosSuper.getCodigoProduto());
            LOGGER.debug("Payload: {}", produtosDTO);
            LOGGER.debug("Produtos existente: {}", produtosSuper);


            Linha linha = this.linhaService.findByIdLinha(produtosDTO.getIdLinha());

            produtosSuper.setLinhaCategoria(linha);
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

    public Produtos findById (Long id){
        Optional<Produtos> idProduto = this.iProdutosRepository.findById(id);

        return idProduto.get();

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

            String doisPrimeiros = produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getCnpj().substring(0,2);
            String tresSegundos= produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getCnpj().substring(2,5);
            String tresTerceiros = produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getCnpj().substring(5,8);
            String penultimosQartos = produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getCnpj().substring(8,12);
            String ultimosDois = produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getCnpj().substring(12,14);
            String cnpjDisfarcado = doisPrimeiros + "." + tresSegundos + "." + tresTerceiros + "/" +
                    penultimosQartos + "-" + ultimosDois;

            miEscritor.append("\n" + produtos.getCodigoProduto() + ";" );
            miEscritor.append(produtos.getNomeProduto() + ";");
            miEscritor.append(produtos.getPreco() + ";");
            miEscritor.append(produtos.getUnidadeCaixa() + ";");
            miEscritor.append(produtos.getPesoUnidade() + produtos.getPesoMedida() + ";");
            miEscritor.append(produtos.getValidade() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCodigoLinha() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getNome() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getCodigoCategoria() + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getNomeCategoria() + ";");
            miEscritor.append(cnpjDisfarcado + ";");
            miEscritor.append(produtos.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getRazaoSocial());

            miEscritor.flush();
        }
    }

    public void importar(MultipartFile file) throws IOException {

        Produtos produtos = new Produtos();

        BufferedReader miLector = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = "";
        String splitBy = ";";

        line = miLector.readLine();
        while ((line = miLector.readLine()) != null) {
            String[] produto = line.split(splitBy);
      
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

            this.iProdutosRepository.save(produtos);
        }
    }

    public void importarOmega(MultipartFile file, Long idFornecedor) throws IOException {

        ProdutosDTO produtosDTO = new ProdutosDTO();
        LinhaDTO linhaDTO = new LinhaDTO();
        CategoriasDTO categoriasDTO = new CategoriasDTO();

        BufferedReader mioLettore = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = " ";
        String splitBy = ";";

        line = mioLettore.readLine();

        while ((line = mioLettore.readLine()) != null) {
            String[] produtoOMEGA = line.split(splitBy);

            Optional <Categorias> categoriasFornecedor = categoriasService.findByCodigoCategoria(produtoOMEGA[8]);
            Optional <Linha> linhaFornecedor = linhaService.findByCodigoLinha(produtoOMEGA[6]);
            Optional <Produtos> produtosFornecedor = this.iProdutosRepository.existsByCodigoProduto(produtoOMEGA[0]);

                if (!categoriasFornecedor.isPresent()) { 
                    LOGGER.info("criando nova categoria ");

                    categoriasDTO.setCodigoCategoria(produtoOMEGA[8]);
                    categoriasDTO.setNomeCategoria(produtoOMEGA[9]);
                    categoriasDTO.setIdFornecedor(idFornecedor);

                    categoriasService.save(categoriasDTO);

                } else  {
                    LOGGER.info("atualizando categoria já existente ");

                    Long idCat = categoriasService.findByCodigoCategorias(produtoOMEGA[8]).getId();

                    categoriasDTO.setCodigoCategoria(produtoOMEGA[8]);

                    LOGGER.info("criando nova linha ");

                    categoriasDTO.setNomeCategoria(produtoOMEGA[9]);
                    categoriasDTO.setIdFornecedor(idFornecedor);
                    categoriasDTO.setId(idCat);

                    categoriasService.update( categoriasDTO, idCat);

                }
                  if (!linhaFornecedor.isPresent()) { 
                    LOGGER.info("criando nova linha ");

                    Long idCat = categoriasService.findByCodigoCategorias(produtoOMEGA[8]).getId();

                    linhaDTO.setCodigoLinha(produtoOMEGA[6]);
                    linhaDTO.setNome(produtoOMEGA[7]);
                    linhaDTO.setIdCategoria(idCat);

                    linhaService.save(linhaDTO);


                } else {
                    LOGGER.info("atualizando linha já existente ");

                    Long idLin = linhaService.findByCodigoLinhas(produtoOMEGA[6]).getId();
                    Long idCat = categoriasService.findByCodigoCategorias(produtoOMEGA[8]).getId();

                    linhaDTO.setCodigoLinha(produtoOMEGA[6]);
                    linhaDTO.setNome(produtoOMEGA[7]);
                    linhaDTO.setIdCategoria(idCat);

                    linhaService.update(linhaDTO, idLin);
                }

                if (!produtosFornecedor.isPresent()){ //cria um novo

                    LOGGER.info("criando nova produtos ");

                    Long idLin = linhaService.findByCodigoLinhas(produtoOMEGA[6]).getId();

                    produtosDTO.setCodigoProduto(produtoOMEGA[0]);
                    produtosDTO.setNomeProduto(produtoOMEGA[1]);
                    produtosDTO.setPreco(Double.parseDouble(produtoOMEGA[2]));
                    produtosDTO.setUnidadeCaixa(Integer.parseInt(produtoOMEGA[3]));

                    String medida = produtoOMEGA[4].replaceAll("[0-9.]", "");
                    String peso = produtoOMEGA[4].replaceAll("[^\\d.]", "");
                    String data = produtoOMEGA[5]; //  09/02/2020

                    String dia = data.substring(0,2);
                    String mes = data.substring(3,5);
                    String ano = data.substring(6,10);
                  
                    String dataCompleta = ano + "-" + mes + "-" + dia;

                    LocalDate dataFinal = LocalDate.parse(dataCompleta);

                    produtosDTO.setPesoMedida(medida);
                    produtosDTO.setIdLinha(idLin);
                    produtosDTO.setPesoUnidade(Double.parseDouble(peso));
                    produtosDTO.setValidade(dataFinal);

                    save(produtosDTO);

                } else {

                  Long idPro = this.iProdutosRepository.findByCodigoProduto(produtoOMEGA[0]).getId();
                  Long idLin = linhaService.findByCodigoLinhas(produtoOMEGA[6]).getId();


                    produtosDTO.setCodigoProduto(produtoOMEGA[0]);
                    produtosDTO.setNomeProduto(produtoOMEGA[1]);
                    produtosDTO.setPreco(Double.parseDouble(produtoOMEGA[2]));
                    produtosDTO.setUnidadeCaixa(Integer.parseInt(produtoOMEGA[3]));

                    String medida = produtoOMEGA[4].replaceAll("[0-9.]", "");
                    String peso = produtoOMEGA[4].replaceAll("[^\\d.]", "");
                    String data = produtoOMEGA[5];//  09/02/2020
                    String dia = data.substring(0,2);
                    String mes = data.substring(3,5);
                    String ano = data.substring(6,10);
                    String dataCompleta = ano + "-" + mes + "-" + dia;

                    LocalDate dataFinal = LocalDate.parse(dataChata);

                    produtosDTO.setPesoMedida(medida);
                    produtosDTO.setPesoUnidade(Double.parseDouble(peso));
                    produtosDTO.setValidade(dataFinal);
                    produtosDTO.setIdLinha(idLin);

                    LOGGER.info("atualizando produtos já existente");

                    update(produtosDTO, idPro);
                }
        }
    }

    public void exportarFornecedorProduto(HttpServletResponse response, Long idFornecedor) throws IOException {

            response.setHeader("Content-Disposition", "attachment; filename=\"output.csv\"");
            response.setContentType("text/csv");

            List<Produtos> express = iProdutosRepository.findAll();
            PrintWriter myWriter = response.getWriter();

            myWriter.append("nome_produto" + ";" + "quantidade" + ";" + "fornecedor" + "-" + "CNPJ");

            Fornecedor fornecedor = fornecedorService.findByFornecedorId(idFornecedor);

        String doisPrimeiros = fornecedor.getCnpj().substring(0,2);
        String tresSegundos= fornecedor.getCnpj().substring(2,5);
        String tresTerceiros = fornecedor.getCnpj().substring(5,8);
        String penultimosQartos = fornecedor.getCnpj().substring(8,12);
        String ultimosDois = fornecedor.getCnpj().substring(12,14);
        String cnpjDisfarcado = doisPrimeiros + "." + tresSegundos + "." + tresTerceiros + "/" +
                penultimosQartos + "-" + ultimosDois;

            for (Produtos produtos : express) {
                myWriter.append("\n" + produtos.getNomeProduto() + ";");
                myWriter.append(produtos.getUnidadeCaixa() + ";");
                myWriter.append(fornecedor.getRazaoSocial() + "-");
                myWriter.append(cnpjDisfarcado);

                myWriter.flush();
            }
    }

    public void exportarFuncionarioProduto(HttpServletResponse response, Long idFornecedor, Long idFuncionario) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"output.csv\"");
        response.setContentType("text/csv");

        List<Produtos> express = iProdutosRepository.findAll();
        PrintWriter myWriter = response.getWriter();

        myWriter.append("nome_produto" + ";" + "quantidade" + ";" + "fornecedor" + "-" + "CNPJ");

        Fornecedor fornecedor = fornecedorService.findByFornecedorId(idFornecedor);
        Funcionario funcionario = funcionarioService.findByIdFuncionario(idFuncionario);

        String doisPrimeiros = fornecedor.getCnpj().substring(0,2);
        String tresSegundos= fornecedor.getCnpj().substring(2,5);
        String tresTerceiros = fornecedor.getCnpj().substring(5,8);
        String penultimosQartos = fornecedor.getCnpj().substring(8,12);
        String ultimosDois = fornecedor.getCnpj().substring(12,14);
        
        String cnpjDisfarcado = doisPrimeiros + "." + tresSegundos + "." + tresTerceiros + "/" +
                penultimosQartos + "-" + ultimosDois;

        for (Produtos produtos : express) {
            myWriter.append("\n" + funcionario.getNome() + ";");
            myWriter.append(produtos.getNomeProduto() + ";");
            myWriter.append(produtos.getUnidadeCaixa() + ";");
            myWriter.append(fornecedor.getRazaoSocial() + "-");
            myWriter.append(cnpjDisfarcado);

            myWriter.flush();
        }
    }
}
