package br.com.hbsis.itens;

import org.springframework.data.jpa.repository.JpaRepository;

interface IItemRepository extends JpaRepository <Item, Long> {

    Item findByIdProduto(Long idProduto);
}
