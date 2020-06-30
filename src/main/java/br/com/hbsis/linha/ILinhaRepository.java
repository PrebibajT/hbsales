package br.com.hbsis.linha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ILinhaRepository  extends JpaRepository<Linha, Long> {

    Optional <Linha> findByCodigoLinha(String codigoLinha);

    
    
    
    
    
    
}
