package br.com.hbsis.funcionario;

import br.com.hbsis.usuario.UsuarioDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);

    private final IFuncionarioRepository iFuncionarioRepository;


    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository) {
        this.iFuncionarioRepository = iFuncionarioRepository;
    }


    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO){
        this.validate(funcionarioDTO);

        LOGGER.info("Salvando usuário");
        LOGGER.debug("Usuario: {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();

        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuid(funcionarioDTO.getUuid());

        funcionario = this.iFuncionarioRepository.save(funcionario);

        return FuncionarioDTO.of(funcionario);

    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Usuario");

        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("UsuarioDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getNome())) {
            throw new IllegalArgumentException("Senha não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getEmail())) {
            throw new IllegalArgumentException("Login não deve ser nulo/vazio");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getUuid())) {
            throw new IllegalArgumentException("Login não deve ser nulo/vazio");
        }
    }

}
