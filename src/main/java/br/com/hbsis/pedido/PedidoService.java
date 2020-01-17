package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.itens.Item;
import br.com.hbsis.itens.ItensDTO;
import br.com.hbsis.itens.ItensService;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.periodoVendas.PeriodoVendasService;
import br.com.hbsis.produtos.Produtos;
import br.com.hbsis.produtos.ProdutosService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    private final IPedidoRepository iPedidoRepository;
    private final FornecedorService fornecedorService;
    private final ProdutosService produtosService;
    private final PeriodoVendasService periodoVendasService;
    private final FuncionarioService funcionarioService;


    public PedidoService(IPedidoRepository iPedidoRepository, FornecedorService fornecedorService, ProdutosService produtosService, PeriodoVendasService periodoVendasService, FuncionarioService funcionarioService) {
        this.iPedidoRepository = iPedidoRepository;
        this.fornecedorService = fornecedorService;

        this.produtosService = produtosService;
        this.periodoVendasService = periodoVendasService;
        this.funcionarioService = funcionarioService;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO){
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

        pedido.setItens(convercaoItens(pedidoDTO.getItens(),pedido));
        LOGGER.info(PedidoDTO.of(pedido).toString());

        pedido = this.iPedidoRepository.save(pedido);

        return PedidoDTO.of(pedido);
    }

    public Long arrumaUid(PedidoDTO pedidoDTO){

        Pedido pedido = new Pedido();

        if(pedidoDTO.getUid() == null && pedido.getUid() == null){
            Long acaso = pedidoDTO.getIdFornecedor();
            pedidoDTO.setuUid(acaso);


        }else if (pedidoDTO.getUid() == null){
            pedidoDTO.setuUid(pedido.getUid() + 1);
        }

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

    public Pedido findById (Long id){
        Optional<Pedido> idHumido = this.iPedidoRepository.findById(id);
        return idHumido.get();

    }

    public void reValidar (PedidoDTO pedidoDTO){

       List<ItensDTO> itensDTOyy = pedidoDTO.getItens();

       Produtos produtosyy = produtosService.findById(itensDTOyy.get(0).getIdProduto());

       PeriodoVendas periodoVendasyy = periodoVendasService.findAllByFornecedor(pedidoDTO.getIdFornecedor());

       Long idFornecedorPorFavor = produtosyy.getLinhaCategoria().getCategoriaLinha().getFornecedorCategoria().getId();

       if(!pedidoDTO.getIdFornecedor().equals(idFornecedorPorFavor)){
           throw new IllegalArgumentException("O id do fornecedor do produto e do pedido são diferentes, azedo");
        }

        if(!periodoVendasyy.getDataInicial().isAfter(pedidoDTO.getDataDeCriacao()) && !periodoVendasyy.getDataInicial().isBefore(pedidoDTO.getDataDeCriacao())){
            throw new IllegalArgumentException("Não tem periodo de venda vigente, olha no dicionário oq vigente é, e resolve");
        }

    }


    public PedidoDTO update(PedidoDTO pedidoDTO, Long id){
        Optional <Pedido> pedidoExistenteChato = this.iPedidoRepository.findById(id);

        if(pedidoExistenteChato.isPresent()){
            Pedido pedidoChatoPacas = pedidoExistenteChato.get();

            LOGGER.info("Atualizando produtos.... id:[{}]", pedidoChatoPacas.getId());
            LOGGER.debug("Payload: {}", pedidoDTO);
            LOGGER.debug("Produtos existente: {}", pedidoChatoPacas);

            Fornecedor fornecedorzz = this.fornecedorService.findByFornecedorId(pedidoDTO.getIdFornecedor());


            pedidoChatoPacas.setDataDeCriacao(pedidoDTO.getDataDeCriacao());
            pedidoChatoPacas.setItens(convercaoItens(pedidoDTO.getItens(),pedidoChatoPacas));
            pedidoChatoPacas.setStatus(pedidoDTO.getStatus());
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

}
