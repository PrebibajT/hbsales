package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        Fornecedor fornecedorxx = fornecedorService.findByFornecedorId(periodoVendasDTO.getIdFornecedor());

        PeriodoVendas periodoVendasxx = this.iPeriodoVendasRepository.findAllFornecedorById(fornecedorxx.getId());


        if (periodoVendasxx == null){

            periodoVendas.setFornecedorPeriodo(fornecedorxx);
            periodoVendas.setDataInicial(periodoVendasDTO.getDataInicial());
            periodoVendas.setDataFinal(periodoVendasDTO.getDataFinal());
            periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
            periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

            periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

            return PeriodoVendasDTO.of(periodoVendas);

        }
                if (periodoVendasDTO.getIdFornecedor().equals(periodoVendasxx.getFornecedorPeriodo().getId())) {

                    if (periodoVendasDTO.getDataInicial().isAfter(periodoVendasxx.getDataInicial()) && periodoVendasDTO.getDataInicial().isBefore(periodoVendasxx.getDataFinal())){
                        throw  new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, maxime");

                    }
                        if(periodoVendasDTO.getDataFinal().isBefore(periodoVendasxx.getDataFinal()) && periodoVendasDTO.getDataFinal().isAfter(periodoVendasxx.getDataInicial())) {
                            throw new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, 4");

                        }
                            if(periodoVendasDTO.getDataInicial().equals(periodoVendasxx.getDataInicial())){
                                  throw  new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, 3");

                            }
                                if(periodoVendasDTO.getDataFinal().equals(periodoVendasxx.getDataFinal())){
                                    throw new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, 5");

                                }
                                else{

                        periodoVendas.setFornecedorPeriodo(fornecedorxx);
                        periodoVendas.setDataInicial(periodoVendasDTO.getDataInicial());
                        periodoVendas.setDataFinal(periodoVendasDTO.getDataFinal());
                        periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
                        periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

                        periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

                        return PeriodoVendasDTO.of(periodoVendas);

                    }

                }else {


                    periodoVendas.setFornecedorPeriodo(fornecedorxx);
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
            throw new IllegalArgumentException("O periodo de vendas não pode ser nulo seu mongol");
        }
        if (periodoVendasDTO.getIdFornecedor() == null) {
            throw new IllegalArgumentException("O fornecedor do periodo não pode ser nulo, burrão");
        }

        if (periodoVendasDTO.getDataInicial() == null || periodoVendasDTO.getDataInicial().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo, nem antes de hoje");
        }

        if (periodoVendasDTO.getDataFinal()== null ||  periodoVendasDTO.getDataFinal() == periodoVendasDTO.getDataInicial()|| periodoVendasDTO.getDataFinal().isBefore(periodoVendasDTO.getDataInicial())) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo, nem igual a data inicial, muito menos antes da data inicial");
        }

        if (periodoVendasDTO.getDataRetirada() == null) {
            throw new IllegalArgumentException("O código da categoria não pode ser nulo");
        }

        if (StringUtils.isEmpty(periodoVendasDTO.getDescricao())) {
            throw new IllegalArgumentException("A descrição do período não pode ser nulo seu animal");
        }
    }

    public PeriodoVendasDTO update (PeriodoVendasDTO periodoVendasDTO, Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.iPeriodoVendasRepository.findById(id);

        if (periodoVendasOptional.isPresent()) {
            PeriodoVendas periodoVendasWow = periodoVendasOptional.get();

            if (periodoVendasWow.getDataFinal().isAfter(LocalDate.now())) {

                throw new IllegalArgumentException("Já passou a data em que você pode alterar algo");

            } else {

                LOGGER.info("Atualizando produtos.... id:[{}]", periodoVendasWow.getId());
                LOGGER.debug("Payload: {}", periodoVendasDTO);
                LOGGER.debug("Produtos existente: {}", periodoVendasWow);

                Fornecedor fornecedor = this.fornecedorService.findByFornecedorId(periodoVendasDTO.getIdFornecedor());

                if (periodoVendasDTO.getIdFornecedor().equals(periodoVendasWow.getFornecedorPeriodo().getId())) {

                    if (periodoVendasDTO.getDataInicial().isAfter(periodoVendasWow.getDataInicial()) && periodoVendasDTO.getDataInicial().isBefore(periodoVendasWow.getDataFinal())) {
                        throw new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, maxime");

                    }
                    if (periodoVendasDTO.getDataFinal().isBefore(periodoVendasWow.getDataFinal()) && periodoVendasDTO.getDataFinal().isAfter(periodoVendasWow.getDataInicial())) {
                        throw new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, 4");

                    }
                    if (periodoVendasDTO.getDataInicial().equals(periodoVendasWow.getDataInicial())) {
                        throw new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, 3");

                    }
                    if (periodoVendasDTO.getDataFinal().equals(periodoVendasWow.getDataFinal())) {
                        throw new IllegalArgumentException("Ferro bahia a data ta errada meu ousado, 5");

                    } else {

                        periodoVendasWow.setFornecedorPeriodo(fornecedor);
                        periodoVendasWow.setDataInicial(periodoVendasDTO.getDataInicial());
                        periodoVendasWow.setDataFinal(periodoVendasDTO.getDataFinal());
                        periodoVendasWow.setDataRetirada(periodoVendasDTO.getDataRetirada());
                        periodoVendasWow.setDescricao(periodoVendasDTO.getDescricao());

                        periodoVendasWow = this.iPeriodoVendasRepository.save(periodoVendasWow);

                        return PeriodoVendasDTO.of(periodoVendasWow);

                    }

                }
            }
        }







                throw new IllegalArgumentException(String.format("ID %s não existe", id));
            }






    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID: [{}]", id);

        this.iPeriodoVendasRepository.deleteById(id);
    }


}
