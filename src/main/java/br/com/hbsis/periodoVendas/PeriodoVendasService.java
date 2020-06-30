package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;

import br.com.hbsis.linha.Linha;
import br.com.hbsis.pedido.Pedido;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoVendasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendasService.class);

    private final IPeriodoVendasRepository iPeriodoVendasRepository;
    private final FornecedorService fornecedorService;

    public PeriodoVendasService(IPeriodoVendasRepository iPeriodoVendasRepository, FornecedorService fornecedorService){
        this.iPeriodoVendasRepository = iPeriodoVendasRepository;
        this.fornecedorService = fornecedorService;

    }


    public PeriodoVendasDTO save(PeriodoVendasDTO periodoVendasDTO){

        this.validate(periodoVendasDTO);

        LOGGER.info("Salvando periodo de vendas");
        LOGGER.debug("Periodo de vendas: {}", periodoVendasDTO);

        PeriodoVendas periodoVendas = new PeriodoVendas();
        Fornecedor fornecedorPeriodo = fornecedorService.findByFornecedorId(periodoVendasDTO.getIdFornecedor());
        PeriodoVendas periodoVendasFornecedor = this.iPeriodoVendasRepository.findAllFornecedorById(fornecedorPeriodo.getId());

        if (periodoVendasFornecedor == null){

            periodoVendas.setFornecedorPeriodo(fornecedorPeriodo);
            periodoVendas.setDataInicial(periodoVendasDTO.getDataInicial());
            periodoVendas.setDataFinal(periodoVendasDTO.getDataFinal());
            periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
            periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

            periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);
            return PeriodoVendasDTO.of(periodoVendas);

        }
                if (periodoVendasDTO.getIdFornecedor().equals(periodoVendasFornecedor.getFornecedorPeriodo().getId())) {

                     if(periodoVendasDTO.getDataInicial().isBefore(PeriodoVendasAtual.getDataInicial()) && periodoVendasDTO.getDataFinal().isBefore(PeriodoVendasAtual.getDataInicial()) || 
                     periodoVendasDTO.getDataInicial().isAfter(PeriodoVendasAtual.getDataFinal()) && periodoVendasDTO.getDataFinal().isAfter(PeriodoVendasAtual.getDataFinal())){
                         
                        periodoVendas.setFornecedorPeriodo(periodoVendasFornecedor);
                        periodoVendas.setDataInicial(periodoVendasDTO.getDataInicial());
                        periodoVendas.setDataFinal(periodoVendasDTO.getDataFinal());
                        periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
                        periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

                        periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

                        return PeriodoVendasDTO.of(periodoVendas);
                         
                     }                            
                        
                }else {

                    periodoVendas.setFornecedorPeriodo(periodoVendasFornecedor);
                    periodoVendas.setDataInicial(periodoVendasDTO.getDataInicial());
                    periodoVendas.setDataFinal(periodoVendasDTO.getDataFinal());
                    periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
                    periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

                    periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);
                    return PeriodoVendasDTO.of(periodoVendas);
                }
    }

    private void validate(PeriodoVendasDTO periodoVendasDTO) {
        LOGGER.info("Validando produtos");

        if (periodoVendasDTO == null) {
            throw new IllegalArgumentException("O periodo de vendas não pode ser nulo");
        }
        if (periodoVendasDTO.getIdFornecedor() == null) {
            throw new IllegalArgumentException("O fornecedor do periodo não pode ser nulo");
        }

        if (periodoVendasDTO.getDataInicial() == null || periodoVendasDTO.getDataInicial().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("O DataInicial da categoria não pode ser nulo, nem antes de hoje");
        }

        if (periodoVendasDTO.getDataFinal()== null ||  periodoVendasDTO.getDataFinal() == periodoVendasDTO.getDataInicial()|| periodoVendasDTO.getDataFinal().isBefore(periodoVendasDTO.getDataInicial())) {
            throw new IllegalArgumentException("A dataFinal não pode ser nulo, nem igual a data inicial, muito menos antes da data inicial");
        }

        if (periodoVendasDTO.getDataRetirada() == null) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo");
        }

        if (StringUtils.isEmpty(periodoVendasDTO.getDescricao())) {
            throw new IllegalArgumentException("A descrição do período não pode ser nulo");
        }
    }

    
    public PeriodoVendasDTO update (PeriodoVendasDTO periodoVendasDTO, Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.iPeriodoVendasRepository.findById(id);

         if (periodoVendasOptional.isPresent()) {
           PeriodoVendas periodoVendasAtual = periodoVendasOptional.get();

           if (periodoVendasAtual.getDataFinal().isAfter(LocalDate.now())) {
               throw new IllegalArgumentException("Já passou a data em que você pode alterar algo");

           }else {

               LOGGER.info("Atualizando produtos.... id:[{}]", periodoVendasAtual.getId());
               LOGGER.debug("Payload: {}", periodoVendasDTO);
               LOGGER.debug("Produtos existente: {}", periodoVendasAtual);

               Fornecedor fornecedor = this.fornecedorService.findByFornecedorId(periodoVendasDTO.getIdFornecedor());

            if (periodoVendasDTO.getIdFornecedor().equals(periodoVendasAtual.getFornecedorPeriodo().getId())) {
                    
                if(periodoVendasDTO.getDataInicial().isBefore(PeriodoVendasAtual.getDataInicial()) && periodoVendasDTO.getDataFinal().isBefore(PeriodoVendasAtual.getDataInicial()) || 
                     periodoVendasDTO.getDataInicial().isAfter(PeriodoVendasAtual.getDataFinal()) && periodoVendasDTO.getDataFinal().isAfter(PeriodoVendasAtual.getDataFinal())){
                
                
                //faz uma white list ao invés de uma black list
                        periodoVendasAtual.setFornecedorPeriodo(fornecedor);
                        periodoVendasAtual.setDataInicial(periodoVendasDTO.getDataInicial());
                        periodoVendasAtual.setDataFinal(periodoVendasDTO.getDataFinal());
                        periodoVendasAtual.setDataRetirada(periodoVendasDTO.getDataRetirada());
                        periodoVendasAtual.setDescricao(periodoVendasDTO.getDescricao());

                        periodoVendasAtual = this.iPeriodoVendasRepository.save(periodoVendasAtual);

                        return PeriodoVendasDTO.of(periodoVendasAtual);              

                   }else{
                     throw new IllegalArgumentException("Não é permitido nenhum tipo de conflito entre periodos de vendas iniciados");
                  }
                                                                                                               
            }else{
                    periodoVendasAtual.setFornecedorPeriodo(fornecedor);
                        periodoVendasAtual.setDataInicial(periodoVendasDTO.getDataInicial());
                        periodoVendasAtual.setDataFinal(periodoVendasDTO.getDataFinal());
                        periodoVendasAtual.setDataRetirada(periodoVendasDTO.getDataRetirada());
                        periodoVendasAtual.setDescricao(periodoVendasDTO.getDescricao());

                        periodoVendasAtual = this.iPeriodoVendasRepository.save(periodoVendasAtual);

                        return PeriodoVendasDTO.of(periodoVendasAtual);              

                   
         }
          throw new IllegalArgumentException(String.format("ID %s não existe", id));
     }
        
             
    public PeriodoVendas findById (Long id){
        Optional<PeriodoVendas> idPeriodo = this.iPeriodoVendasRepository.findById(id);

        return idPeriodo.get();
    }

    public PeriodoVendas findAllByFornecedor (Long idFornecedor){
       PeriodoVendas idFornecedor = this.iPeriodoVendasRepository.findAllFornecedorById(idFornecedor);

        return idFornecedor;

    }
    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID: [{}]", id);

        this.iPeriodoVendasRepository.deleteById(id);
    }
}
