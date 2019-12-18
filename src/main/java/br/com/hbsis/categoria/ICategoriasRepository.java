package br.com.hbsis.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ICategoriasRepository extends JpaRepository<Categorias, Long> {

    Optional <Categorias> findByCodigoCategoria (String codigoCategoria);

}
