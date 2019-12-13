package br.com.hbsis.produtos;

import br.com.hbsis.linha.ILinhaRepository;
import br.com.hbsis.linha.Linha;
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
    private  final ILinhaRepository iLinhaRepository;

    public ProdutosService(IProdutosRepository iProdutosRepository, LinhaService linhaService, ILinhaRepository iLinhaRepository) {
        this.iProdutosRepository = iProdutosRepository;
        this.linhaService = linhaService;
        this.iLinhaRepository = iLinhaRepository;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) {

        this.validate(produtosDTO);

        LOGGER.info("Salvando produtos");
        LOGGER.debug("Produtos: {}", produtosDTO);

        Produtos produtos = new Produtos();

        produtos.setCodigoProduto(produtosDTO.getCodigoProduto().toUpperCase());
        StringBuilder produtationConcatenation = new StringBuilder();

        if(produtosDTO.getCodigoProduto().length() != 10){
            int cata = 10 - produtosDTO.getCodigoProduto().length();

            for (int i = 0; i < cata; i++) {
                produtationConcatenation.append("0");
                System.out.println(produtationConcatenation);

            }

            String concatenationProdutation = produtationConcatenation.toString() + produtosDTO.getCodigoProduto();

            concatenationProdutation = concatenationProdutation.toUpperCase();

            produtos.setCodigoProduto(concatenationProdutation);


        }

     Optional<Linha> linha = this.linhaService.findByCodigoLinha(produtosDTO.getIdLinha());

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


        }else if (produtosDTO.getPesoMedida().equals("mg")) {


        }else  if (produtosDTO.getPesoMedida().equals("g")) {


        }else if (produtosDTO.getPesoMedida().equals("Kg")){


        }else {
            throw new IllegalArgumentException("O peso da medida do produto não vai aceitar isso, seu fudido");
        }
    }

    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id){
        Optional <Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if(produtosOptional.isPresent()){
         Produtos produtosSuper = produtosOptional.get();

            LOGGER.info("Atualizando produtos.... id:[{}]", produtosSuper.getCodigoProduto());
            LOGGER.debug("Payload: {}", produtosDTO);
            LOGGER.debug("Produtos existente: {}", produtosSuper);


            Optional<Linha> linha = this.linhaService.findByCodigoLinha(produtosDTO.getIdLinha());
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




}
