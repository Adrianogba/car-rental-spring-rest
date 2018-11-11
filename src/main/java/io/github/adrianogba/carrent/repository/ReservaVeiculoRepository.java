package io.github.adrianogba.carrent.repository;

import io.github.adrianogba.carrent.models.Cliente;
import io.github.adrianogba.carrent.models.ReservaVeiculo;
import io.github.adrianogba.carrent.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaVeiculoRepository extends JpaRepository<ReservaVeiculo, Long> {

    ReservaVeiculo findById(long id);


    List<ReservaVeiculo> findAllByVeiculo(Veiculo veiculo);


    List<ReservaVeiculo> findAllByCliente(Cliente cliente);

}
