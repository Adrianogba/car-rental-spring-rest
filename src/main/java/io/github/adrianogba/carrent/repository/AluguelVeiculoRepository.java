package io.github.adrianogba.carrent.repository;

import io.github.adrianogba.carrent.models.AluguelVeiculo;

import io.github.adrianogba.carrent.models.Cliente;
import io.github.adrianogba.carrent.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AluguelVeiculoRepository extends JpaRepository<AluguelVeiculo, Long> {

    AluguelVeiculo findById(long id);


    List<AluguelVeiculo> findAllByVeiculo(Veiculo veiculo);

    List<AluguelVeiculo> findAllByCliente(Cliente cliente);



}
