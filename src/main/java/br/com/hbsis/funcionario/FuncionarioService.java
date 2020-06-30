package br.com.hbsis.funcionario;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);

    private final IFuncionarioRepository iFuncionarioRepository;


    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository) {
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    private void validaAPI(FuncionarioDTO funcionarioDTO) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f5a00866-1b67-11ea-978f-2e728ce88125");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(funcionarioDTO, headers);
        ResponseEntity<EmployeeDTO> response = template.exchange("http://10.2.54.25:9999/api/employees", HttpMethod.POST, httpEntity, EmployeeDTO.class);
        funcionarioDTO.setUuid(Objects.requireNonNull(response.getBody()).getEmployeeUuid());
        funcionarioDTO.setNome(response.getBody().getEmployeeName());
    }


    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Usuario");

        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("FuncionarioDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getNome())) {
            throw new IllegalArgumentException("Senha não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getEmail())) {
            throw new IllegalArgumentException("Email não deve ser nulo/vazio");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getUuid())) {
            throw new IllegalArgumentException("Id não deve ser nulo/vazio");
        }
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO){
        this.validaAPI(funcionarioDTO);
        this.validate(funcionarioDTO);

        LOGGER.info("Salvando funcionario");
        LOGGER.debug("Funcionario: {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();

        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuid(funcionarioDTO.getUuid());

        funcionario = this.iFuncionarioRepository.save(funcionario);

        return FuncionarioDTO.of(funcionario);

    }

    public FuncionarioDTO findById(Long id) {
        Optional<Funcionario> funcionarioExistente = this.iFuncionarioRepository.findById(id);

        if (funcionarioExistente.isPresent()) {
            return FuncionarioDTO.of(funcionarioExistente.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public Funcionario findByIdFuncionario(Long id) {
        Optional<Funcionario> funcionarioExistente = this.iFuncionarioRepository.findById(id);

            return funcionarioExistente.get();
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para funcionario de ID: [{}]", id);

        this.iFuncionarioRepository.deleteById(id);
    }

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, Long id) {
        Optional<Funcionario> funcionarioExistenteOptional = this.iFuncionarioRepository.findById(id);

        if (funcionarioExistenteOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioExistenteOptional.get();

            LOGGER.info("Atualizando funcionario... id: [{}]", funcionarioExistente.getId());
            LOGGER.debug("Payload: {}", funcionarioDTO);
            LOGGER.debug("funcionario Existente: {}", funcionarioExistente);

            funcionarioExistente.setNome(funcionarioDTO.getNome());
            funcionarioExistente.setEmail(funcionarioDTO.getEmail());
            funcionarioExistente.setUuid(funcionarioDTO.getUuid());

            funcionarioExistente = this.iFuncionarioRepository.save(funcionarioExistente);

            return FuncionarioDTO.of(funcionarioExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }




}
