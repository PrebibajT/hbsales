package br.com.hbsis.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
interface IProdutosRepository extends  JpaRepository<Produtos, Long>{

    Produtos findByCodigoProduto(String codigoProduto);

    Optional <Produtos> existsByCodigoProduto(String codigoProduto);
}
