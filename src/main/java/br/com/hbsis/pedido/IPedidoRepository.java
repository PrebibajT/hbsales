package br.com.hbsis.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
interface IPedidoRepository extends JpaRepository<Pedido , Long> {


    @Query(value = "SELECT * FROM seg_pedidos WHERE id = (select max(id) from seg_pedidos)", nativeQuery = true)
    Pedido pedidoWhitIdMax();

    Pedido findByUid(Long uid);

    Pedido findByPedidoFuncionario_Id(Long idFuncionario);

}
