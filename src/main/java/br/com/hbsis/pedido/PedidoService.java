package br.com.hbsis.pedido;

import br.com.hbsis.RequestLoggingInterceptor;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.itens.Item;
import br.com.hbsis.itens.ItensDTO;
import br.com.hbsis.itens.InvoiceDTO;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.periodoVendas.PeriodoVendasService;
import br.com.hbsis.produtos.Produtos;
import br.com.hbsis.produtos.ProdutosService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    private final IPedidoRepository iPedidoRepository;
    private final FornecedorService fornecedorService;
    private final ProdutosService produtosService;
    private final PeriodoVendasService periodoVendasService;
    private final FuncionarioService funcionarioService;
    private final JavaMailSender javaMailSender;


    public PedidoService(IPedidoRepository iPedidoRepository, FornecedorService fornecedorService, ProdutosService produtosService, PeriodoVendasService periodoVendasService, FuncionarioService funcionarioService, JavaMailSender javaMailSender) {
        this.iPedidoRepository = iPedidoRepository;
        this.fornecedorService = fornecedorService;
        this.produtosService = produtosService;
        this.periodoVendasService = periodoVendasService;
        this.funcionarioService = funcionarioService;
        this.javaMailSender = javaMailSender;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        this.arrumaUid(pedidoDTO);
        this.validate(pedidoDTO);
        this.reValidar(pedidoDTO);

        LOGGER.info("Salvando pedidos");
        LOGGER.debug("Pedidos: {}", pedidoDTO);

        Pedido pedido = new Pedido();

        Fornecedor fornecedorxx = fornecedorService.findByFornecedorId(pedidoDTO.getIdFornecedor());
        Funcionario funcionarioxx = funcionarioService.findByIdFuncionario(pedidoDTO.getIdFuncionario());

        pedido.setDataDeCriacao(pedidoDTO.getDataDeCriacao());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setPedidoFornecedor(fornecedorxx);
        pedido.setPedidoFuncionario(funcionarioxx);
        pedido.setUid(arrumaUid(pedidoDTO));
        pedido.setItens(convercaoItens(pedidoDTO.getItens(), pedido));

        pedido = this.iPedidoRepository.save(pedido);

        List<Item> a = pedido.getItens();

        this.validaAPI(InvoiceDTO.of(pedido, a));
        this.simpleMailMessage(pedido.getPedidoFuncionario());

        return PedidoDTO.of(pedido);
    }


    public Long arrumaUid(PedidoDTO pedidoDTO) {

        if (true) {
            return ((long) new Random().nextInt(100000));
        }

        /**
         * Possui unicidade de código dentro do banco
         * - A Aplicação deveria gerar esse código antes de mandar pro banco
         * - Necessário fazer uma consulta dentro do banco pra pegar o último valor inserido
         * - Incrementa e atribui ao próximo pedido
         */

//        Pedido pedido = new Pedido();
//
//        Pedido pedidoxx = iPedidoRepository.findByUid(pedido.getUid());
//
//
//        if(pedidoxx.getUid() == null){
//           String um = "1";
//          pedidoxx.setUid(Long.parseLong(um));
//
//
//        }else if (pedidoxx.getUid() >= 1){
//            pedidoDTO.setuUid(pedidoxx.getUid() + 1);
//        }
//
        return pedidoDTO.getUid();

    }

    private List<Item> convercaoItens(List<ItensDTO> itensDTOS, Pedido pedido) {
        List<Item> itens = new ArrayList<>();
        for (ItensDTO itensDTO : itensDTOS) {
            Item itens1 = new Item();
            itens1.setIdPedido(pedido);
            itens1.setQuantidade(itensDTO.getQuantidadeProduto());
            itens1.setIdProduto(produtosService.findById(itensDTO.getIdProduto()));
            itens.add(itens1);
        }
        return itens;
    }


    private void validaAPI(InvoiceDTO invoiceDTO) {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(new RequestLoggingInterceptor()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f5a00866-1b67-11ea-978f-2e728ce88125");
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<InvoiceDTO> httpEntity = new HttpEntity<>(invoiceDTO, headers);
        ResponseEntity<InvoiceDTO> response = template.exchange("http://10.2.54.25:9999/api/invoice", HttpMethod.POST, httpEntity, InvoiceDTO.class);
    }


    private void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando produtos");

        if (pedidoDTO == null) {
            throw new IllegalArgumentException("CategoriasDTO  não deve ser nulo");
        }
        if (pedidoDTO.getIdFornecedor() == null) {
            throw new IllegalArgumentException("O fornecedor da categoria não pode ser nulo");
        }

        if (pedidoDTO.getIdFuncionario() == null) {
            throw new IllegalArgumentException("O funcionario da categoria não pode ser nulo");
        }

        if (pedidoDTO.getItens() == null) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getStatus())) {
            throw new IllegalArgumentException("O nome da categoria não pode ser nulo");
        }
        if (pedidoDTO.getDataDeCriacao() == null) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo");
        }

    }


    public Pedido findById(Long id) {
        Optional<Pedido> idHumido = this.iPedidoRepository.findById(id);
        return idHumido.get();

    }


    public void reValidar(PedidoDTO pedidoDTO) {

        for (ItensDTO item : pedidoDTO.getItens()) {

            Produtos produtosyy = produtosService.findById(item.getIdProduto());// pega o produto do pedido

            PeriodoVendas periodoVendasyy = periodoVendasService.findAllByFornecedor(pedidoDTO.getIdFornecedor());

            Long idFornecedorPorFavor = produtosyy.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getId();

            if (!pedidoDTO.getIdFornecedor().equals(idFornecedorPorFavor)) {
                throw new IllegalArgumentException("O id do fornecedor do produto e do pedido são diferentes, azedo");
            }

            if (!periodoVendasyy.getDataInicial().isAfter(pedidoDTO.getDataDeCriacao()) && !periodoVendasyy.getDataInicial().isBefore(pedidoDTO.getDataDeCriacao())) {
                throw new IllegalArgumentException("Não tem periodo de venda vigente, olha no dicionário oq vigente é, e resolve");
            }
        }

    }


    public PedidoDTO statusCancela(Long id) {
        Optional<Pedido> pedidoExistenteChato = this.iPedidoRepository.findById(id);

        if (pedidoExistenteChato.isPresent()) {
            Pedido pedidoChatoPacasete = pedidoExistenteChato.get();
            PeriodoVendas periodoVendasyy = periodoVendasService.findAllByFornecedor(pedidoChatoPacasete.getPedidoFornecedor().getId());

            if (pedidoChatoPacasete.getStatus().contains("Ativo")) {
                if (!periodoVendasyy.getDataInicial().isAfter(LocalDate.now()) && !periodoVendasyy.getDataInicial().isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Não tem periodo de venda vigente, olha no dicionário oq vigente é, e resolve");
                }
                pedidoChatoPacasete.setStatus("Cancelado");// setta o pedido como cancelado

            } else if (pedidoChatoPacasete.getStatus().contains("Cancelado") || pedidoChatoPacasete.getStatus().contains("Recebido")) {
                throw new IllegalArgumentException("não da pra cancelar pedido q n ta ativo parça");
            }
            pedidoChatoPacasete = this.iPedidoRepository.save(pedidoChatoPacasete);
            return PedidoDTO.of(pedidoChatoPacasete);
        }
        throw new IllegalArgumentException("não tem oq updatar");
    }


    public PedidoDTO statusRetira(Long id) {
        Optional<Pedido> pedidoExistenteChato = this.iPedidoRepository.findById(id);

        if (pedidoExistenteChato.isPresent()) {
            Pedido pedidoChatoPacasete = pedidoExistenteChato.get();
            PeriodoVendas periodoVendasyy = periodoVendasService.findAllByFornecedor(pedidoChatoPacasete.getPedidoFornecedor().getId());//pega o periodo de vendas do pedido

            if (pedidoChatoPacasete.getStatus().contains("Ativo")) {
                if (!periodoVendasyy.getDataInicial().isAfter(LocalDate.now()) && !periodoVendasyy.getDataInicial().isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Não tem periodo de venda vigente, olha no dicionário oq vigente é, e resolve");
                }

                pedidoChatoPacasete.setStatus("Retirado");// setta o pedido como retirado

            } else if (pedidoChatoPacasete.getStatus().contains("Cancelado") || pedidoChatoPacasete.getStatus().contains("Recebido")) {
                throw new IllegalArgumentException("não da pra cancelar pedido q n ta ativo parça");
            }
            pedidoChatoPacasete = this.iPedidoRepository.save(pedidoChatoPacasete);
            return PedidoDTO.of(pedidoChatoPacasete);
        }
        throw new IllegalArgumentException("não tem oq updatar");
    }

    public PedidoDTO attLadrao(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoExistenteChato = this.iPedidoRepository.findById(id);


        if (pedidoExistenteChato.isPresent()) {
            Pedido pedidoChatoPacasete = pedidoExistenteChato.get();
            PeriodoVendas periodoVendasyy = periodoVendasService.findAllByFornecedor(pedidoChatoPacasete.getPedidoFornecedor().getId());//pega o periodo de vendas do pedido

            if (pedidoChatoPacasete.getStatus().contains("Ativo")) {
                if (!periodoVendasyy.getDataInicial().isAfter(LocalDate.now()) && !periodoVendasyy.getDataInicial().isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Não tem periodo de venda vigente, olha no dicionário oq vigente é, e resolve");
                }
                Fornecedor fornecedorzz = this.fornecedorService.findByFornecedorId(pedidoDTO.getIdFornecedor());

                pedidoChatoPacasete.setStatus(pedidoDTO.getStatus());
                pedidoChatoPacasete.setDataDeCriacao(pedidoDTO.getDataDeCriacao());
                pedidoChatoPacasete.setItens(convercaoItens(pedidoDTO.getItens(), pedidoChatoPacasete));
                pedidoChatoPacasete.setPedidoFornecedor(fornecedorzz);

            } else if (pedidoChatoPacasete.getStatus().contains("Cancelado") || pedidoChatoPacasete.getStatus().contains("Recebido")) {
                throw new IllegalArgumentException("não da pra cancelar pedido q n ta ativo parça");
            }
            pedidoChatoPacasete = this.iPedidoRepository.save(pedidoChatoPacasete);
            return PedidoDTO.of(pedidoChatoPacasete);
        }
        throw new IllegalArgumentException("não tem oq updatar");
    }


    @Bean
    public void simpleMailMessage(Funcionario funcionario) {

        SimpleMailMessage sendSimpleMessage = new SimpleMailMessage();

        sendSimpleMessage.setSubject("Compra no site dos cria");
        sendSimpleMessage.setText("seu pedido foi aprovado e está em separação");
        sendSimpleMessage.setTo(funcionario.getEmail());
        sendSimpleMessage.setFrom("amrheintiago@gmail.com");

        try {
            javaMailSender.send(sendSimpleMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoExistenteChatao = this.iPedidoRepository.findById(id);

        if (pedidoExistenteChatao.isPresent()) {
            Pedido pedidoChatoPacas = pedidoExistenteChatao.get();

            LOGGER.info("Atualizando produtos.... id:[{}]", pedidoChatoPacas.getId());
            LOGGER.debug("Payload: {}", pedidoDTO);
            LOGGER.debug("Produtos existente: {}", pedidoChatoPacas);

            Fornecedor fornecedorzz = this.fornecedorService.findByFornecedorId(pedidoDTO.getIdFornecedor());

            pedidoChatoPacas.setStatus(pedidoDTO.getStatus());
            pedidoChatoPacas.setDataDeCriacao(pedidoDTO.getDataDeCriacao());
            pedidoChatoPacas.setItens(convercaoItens(pedidoDTO.getItens(), pedidoChatoPacas));
            pedidoChatoPacas.setPedidoFornecedor(fornecedorzz);

            pedidoChatoPacas = this.iPedidoRepository.save(pedidoChatoPacas);
            return PedidoDTO.of(pedidoChatoPacas);
        }
        throw new IllegalArgumentException("não tem oq updatar");
    }


    public void delete(Long id) {
        LOGGER.info("Executando delete para linha de ID: [{}]", id);

        this.iPedidoRepository.deleteById(id);
    }


    public void pegaLadrao(Long idFuncionario) {

        List<Pedido> express = iPedidoRepository.findAll();
        for (Pedido pedido : express) {

            if (pedido.getPedidoFuncionario().getId().equals(idFuncionario)) {
                if (pedido.getStatus().contains("Ativo") || pedido.getStatus().contains("Cancelado"))
                    System.out.println(pedido.toString());

            } else {
                throw new IllegalArgumentException("O idFuncionario q vc busca n se encontra, tente novamente cadastrando ele");

            }
        }
    }
}