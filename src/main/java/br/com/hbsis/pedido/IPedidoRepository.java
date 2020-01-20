package br.com.hbsis.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface IPedidoRepository extends JpaRepository<Pedido , Long> {

    Pedido findByUid(Long uid);


}
