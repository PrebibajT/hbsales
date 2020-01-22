package br.com.hbsis.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
